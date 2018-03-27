package com.nikunj.gamezopinterview.ui.base;

/**
 * Created by nikunj on 3/27/18.
 */


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();
}