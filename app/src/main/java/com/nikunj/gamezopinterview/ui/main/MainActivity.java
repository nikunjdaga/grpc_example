package com.nikunj.gamezopinterview.ui.main;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.data.response.GetGameResponse;
import com.nikunj.gamezopinterview.data.model.GridViewItem;
import com.nikunj.gamezopinterview.ui.webview.WebviewActivity;
import com.nikunj.gamezopinterview.utils.DownloadZipTask;
import com.nikunj.gamezopinterview.utils.UnzipTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements GetGameResponse{
    private ArrayList<GridViewItem> gamesList = new ArrayList<GridViewItem>();
    private RecyclerView gamesGrid;
    protected GridLayoutManager lgridLayout;
    private GamesItemAdapter gamesItemAdapter;
    private Integer GRID_COLUMNS = 2;
    GrpcTask grpcTask = null;
    private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Gamezop Interview");
        ButterKnife.bind(this);

        gamesGrid = (RecyclerView) findViewById(R.id.games_grid_list);
        lgridLayout = new GridLayoutManager(this, GRID_COLUMNS);

        gamesGrid.setHasFixedSize(true);
        gamesGrid.setLayoutManager(lgridLayout);

        grpcTask = new GrpcTask(this);
        grpcTask.execute();
        grpcTask.response = this;

        gamesItemAdapter = new GamesItemAdapter(this, gamesList, hashMap);
        gamesGrid.setAdapter(gamesItemAdapter);


        gamesGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void afterUnzip(ArrayList<GridViewItem> gridViewItemList) {
        gamesList.addAll(gridViewItemList);
        gamesItemAdapter = new GamesItemAdapter(this, gamesList,hashMap);
        gamesGrid.setAdapter(gamesItemAdapter);
        gamesItemAdapter.notifyDataSetChanged();
        gamesGrid.invalidate();

        /*gamesGrid.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public void getUnzippedHashMap(HashMap<Integer, String> hashMap) {
        this.hashMap.putAll(hashMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
