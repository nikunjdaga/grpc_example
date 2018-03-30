package com.nikunj.gamezopinterview.ui.main;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.data.response.GetGameResponse;
import com.nikunj.gamezopinterview.data.model.GridViewItem;

import java.util.ArrayList;
import java.util.HashMap;


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

        gamesGrid.setHasFixedSize(true);
        gamesGrid.setLayoutManager(lgridLayout);

        grpcTask = new GrpcTask(this);
        grpcTask.execute();
        grpcTask.response = this;

        gamesItemAdapter = new GamesItemAdapter(this, gamesList, hashMap);
        gamesGrid.setAdapter(gamesItemAdapter);
    }

/*    @OnItemClick(R.id.games_grid_list)
    private void onGridItemClick(){
        progress = new ProgressDialog(getBaseContext());
        progress.setMessage("Loading game...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }*/

    @Override
    public void afterUnzip(ArrayList<GridViewItem> gridViewItemList) {
        gamesList.addAll(gridViewItemList);
        gamesItemAdapter = new GamesItemAdapter(this, gamesList,hashMap);
        gamesGrid.setAdapter(gamesItemAdapter);
        gamesItemAdapter.notifyDataSetChanged();
        gamesGrid.invalidate();
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
