package com.nikunj.gamezopinterview.ui.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.nikunj.gamezopinterview.GamesRequest;
import com.nikunj.gamezopinterview.GamesResponse;
import com.nikunj.gamezopinterview.InfoServiceGrpc;
import com.nikunj.gamezopinterview.data.model.GridViewItem;
import com.nikunj.gamezopinterview.data.response.GetGameResponse;
import com.nikunj.gamezopinterview.utils.DownloadConstants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by nikunj on 3/30/18.
 */
class GrpcTask extends AsyncTask<String, Void, GamesResponse> {
    private final WeakReference<Activity> activityReference;
    private ManagedChannel channel;
    GetGameResponse response = null;

    GrpcTask(Activity activity) {
        this.activityReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected GamesResponse doInBackground(String... params) {
        String host = DownloadConstants.HOST_ADDRESS;
        String portStr = DownloadConstants.PORT;
        int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
        try {
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
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
        ArrayList<GridViewItem> getImagesURLfromResult = new ArrayList<GridViewItem>();
        HashMap<Integer, String> makeMap = new HashMap<>();
        for (int i = 0; i < result.getGamesCount(); i++) {
            getImagesURLfromResult.add(new GridViewItem(result.getGames(i).getName(), result.getGames(i).getCover()));
            makeMap.put(i, result.getGames(i).getZipUrl());

        }
        response.getUnzippedHashMap(makeMap);
        response.afterUnzip(getImagesURLfromResult);

    }
}