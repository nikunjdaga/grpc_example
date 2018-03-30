package com.nikunj.gamezopinterview.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.data.model.GridViewItem;
import com.nikunj.gamezopinterview.ui.webview.WebviewActivity;
import com.nikunj.gamezopinterview.utils.DownloadZipTask;
import com.nikunj.gamezopinterview.utils.UnzipTask;


/**
 * Created by nikunj on 3/27/18.
 */

public class GamesItemAdapter extends RecyclerView.Adapter<GamesItemAdapter.GamesItemViewHolder> {

    private List<GridViewItem> gameGridItemList;
    private Context context;
    private HashMap<Integer, String> hashMap;

    public GamesItemAdapter(Context context, List<GridViewItem> gameGridItemList,
                            HashMap<Integer, String> hashMap) {
        this.gameGridItemList = gameGridItemList;
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    public GamesItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.game_grid_item, parent, false);
        return new GamesItemViewHolder(gameLayoutView);
    }

    @Override
    public void onBindViewHolder(GamesItemViewHolder holder, final int position) {
        holder.gameName.setText(gameGridItemList.get(position).getGameName());

        Glide.with(context)
                .load(gameGridItemList.get(position).getGameImageUrl())
                .into(holder.gameImage);

        ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Loading game...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        String zipUrl = hashMap.get(position);
        DownloadZipTask downloadZipTask = new DownloadZipTask(
                "/gamezopint/" + position + ".zip",
                context, new DownloadZipTask.onPostDownload() {
            @Override
            public void downloadFinish(File fDownload) {
                // check unzip file now
                UnzipTask unzip = new UnzipTask(context, fDownload);
                String ss = unzip.unzip(position);
                String path_to_file = "gamezopinterview/unzips/" + ss;
                Intent startAct = new Intent(context, WebviewActivity.class);
                startAct.putExtra("path", path_to_file);
                context.startActivity(startAct);
            }
        });
        downloadZipTask.execute(zipUrl);

    }

    @Override
    public int getItemCount() {
        return gameGridItemList.size();
    }

    static class GamesItemViewHolder extends RecyclerView.ViewHolder {

        TextView gameName;
        ImageView gameImage;
        ConstraintLayout gameGridCard;

        GamesItemViewHolder(View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.game_name);
            gameImage = itemView.findViewById(R.id.game_image);
            gameGridCard = itemView.findViewById(R.id.game_grid_layout);
        }
    }
}
