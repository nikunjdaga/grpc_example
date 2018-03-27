package com.nikunj.gamezopinterview.ui.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.data.response.GetGameResponse;
import com.nikunj.gamezopinterview.data.model.GridViewItem;
import com.nikunj.gamezopinterview.ui.webview.WebviewActivity;
import com.nikunj.gamezopinterview.utils.DownloadConstants;
import com.nikunj.gamezopinterview.utils.DownloadZipTask;
import com.nikunj.gamezopinterview.utils.UnzipTask;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import app.gamezoptest.gamezoptest.GamesRequest;
import app.gamezoptest.gamezoptest.GamesResponse;
import app.gamezoptest.gamezoptest.InfoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MainActivity extends AppCompatActivity implements GetGameResponse{
    private ArrayList<GridViewItem> gamesList = new ArrayList<GridViewItem>();
    private RecyclerView gamesGrid;
    protected GridLayoutManager lgridLayout;
    private GamesItemAdapter gamesItemAdapter;
    private Integer GRID_COLUMNS = 2;
    GrpcTask grpcTask = null;
    public static ProgressDialog progress = null;
    private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gamesGrid = (RecyclerView) findViewById(R.id.games_grid_list);
        lgridLayout = new GridLayoutManager(this, GRID_COLUMNS);

        grpcTask = new GrpcTask(this);
        grpcTask.execute();
        grpcTask.response = this;

        gamesGrid.setHasFixedSize(true);
        gamesGrid.setLayoutManager(lgridLayout);

        gamesItemAdapter = new GamesItemAdapter(this, gamesList);
        gamesGrid.setAdapter(gamesItemAdapter);
    }

    @Override
    public void afterUnzip(ArrayList<GridViewItem> gridViewItemList) {
        gamesList.addAll(gridViewItemList);
        gamesItemAdapter.notifyDataSetChanged();
        gamesGrid.invalidate();
        gamesGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(getBaseContext());
                progress.setMessage("Loading game...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                final int i = gamesGrid.getChildLayoutPosition(v);
                String zipUrl = hashMap.get(i);
                DownloadZipTask downloadZipTask = new DownloadZipTask(
                        "/gamezopinterview/" + i + ".zip",
                        getBaseContext(), new DownloadZipTask.onPostDownload() {
                    @Override
                    public void downloadFinish(File fDownload) {
                        // check unzip file now
                        UnzipTask unzip = new UnzipTask(MainActivity.this, fDownload);
                        String ss = unzip.unzip(i);
                        String path_to_file = "gamezopinterview/unzips/" + ss;
                        Intent startAct = new Intent(MainActivity.this, WebviewActivity.class);
                        startAct.putExtra("path", path_to_file);
                        startActivity(startAct);
                    }
                });
                downloadZipTask.execute(zipUrl);
            }
        });
    }

    @Override
    public void getUnzippedHashMap(HashMap<Integer, String> hashMap) {
        this.hashMap.putAll(hashMap);
    }


    private class GrpcTask extends AsyncTask<String, Void, GamesResponse> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private GetGameResponse response = null;

        private GrpcTask(Activity activity) {
            this.activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        protected GamesResponse doInBackground(String... params) {
            String hostAddress = DownloadConstants.HOST_ADDRESS;
            String portString = DownloadConstants.PORT;
            int port = TextUtils.isEmpty(portString) ? 0 : Integer.valueOf(portString);
            try {
                channel = ManagedChannelBuilder.forAddress(hostAddress, port).usePlaintext(true).build();
                InfoServiceGrpc.InfoServiceBlockingStub stub = InfoServiceGrpc.newBlockingStub(channel);
                GamesRequest request = GamesRequest.newBuilder().build();
                return stub.getGames(request);

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return null;
            }
        }

        @Override
        protected void onPostExecute(GamesResponse result) {
            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ArrayList<GridViewItem> getgamesList = new ArrayList<GridViewItem>();
            HashMap<Integer, String> makeMap = new HashMap<>();
            for (int i = 0; i < result.getGamesCount(); i++) {
                getgamesList.add(new GridViewItem(result.getGames(i).getName(), result.getGames(i).getCover()));
                makeMap.put(i, result.getGames(i).getZipUrl());

            }
            response.getUnzippedHashMap(makeMap);
            response.afterUnzip(getgamesList);
            gamesItemAdapter = new GamesItemAdapter(getApplicationContext(), gamesList);
            gamesItemAdapter.notifyDataSetChanged();
        }
    }
}
