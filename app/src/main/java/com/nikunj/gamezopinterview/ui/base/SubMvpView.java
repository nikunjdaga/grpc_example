package com.nikunj.gamezopinterview.ui.base;

/**
 * Created by nikunj on 3/27/18.
 */

public interface SubMvpView extends MvpView {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}