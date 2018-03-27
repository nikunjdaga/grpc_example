package com.nikunj.gamezopinterview.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;


import com.nikunj.gamezopinterview.di.ActivityContext;
import com.nikunj.gamezopinterview.di.PerActivity;
import com.nikunj.gamezopinterview.utils.rxjava.AppSchedulerProvider;
import com.nikunj.gamezopinterview.utils.rxjava.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by nikunj on 3/27/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    /*@Provides
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            AboutPresenter<MainMvpView> presenter) {
        return presenter;
    }*/

}
