package com.nikunj.gamezopinterview.data.model;

/**
 * Created by nikunj on 3/27/18.
 */

public class GridViewItem {
    private String gameName;
    private String gameImageUrl;

    public GridViewItem(String gameName, String gameImageUrl){
        this.gameName = gameName;
        this.gameImageUrl = gameImageUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameImageUrl() {
        return gameImageUrl;
    }

    public void setGameImageUrl(String gameImageUrl) {
        this.gameImageUrl = gameImageUrl;
    }
}


