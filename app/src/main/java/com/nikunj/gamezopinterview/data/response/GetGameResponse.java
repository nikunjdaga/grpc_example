package com.nikunj.gamezopinterview.data.response;

import com.nikunj.gamezopinterview.data.model.GridViewItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nikunj on 3/27/18.
 */

public interface GetGameResponse {
    void afterUnzip(ArrayList<GridViewItem> gridViewItemList);
    void getUnzippedHashMap(HashMap<Integer, String> hashMap);
}
