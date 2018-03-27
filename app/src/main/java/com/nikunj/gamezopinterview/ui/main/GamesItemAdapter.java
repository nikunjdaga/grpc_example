package com.nikunj.gamezopinterview.ui.main;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.data.model.GridViewItem;


/**
 * Created by nikunj on 3/27/18.
 */

public class GamesItemAdapter extends RecyclerView.Adapter<GamesItemAdapter.GamesItemViewHolder> {

    private List<GridViewItem> gameGridItemList;
    private Context context;

    public GamesItemAdapter(Context context, List<GridViewItem> gameGridItemList) {
        this.gameGridItemList = gameGridItemList;
        this.context = context;
    }

    @Override
    public GamesItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.game_grid_item, parent, false);
        return new GamesItemViewHolder(gameLayoutView);
    }

    @Override
    public void onBindViewHolder(GamesItemViewHolder holder, int position) {
        holder.gameName.setText(gameGridItemList.get(position).getGameName());

        Glide.with(context)
                .load(gameGridItemList.get(position).getGameImageUrl())
                .into(holder.gameImage);

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
