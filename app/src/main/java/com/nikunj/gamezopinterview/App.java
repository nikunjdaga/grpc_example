package com.nikunj.gamezopinterview;


import android.support.multidex.MultiDexApplication;

import com.nikunj.gamezopinterview.di.component.ApplicationComponent;
import com.nikunj.gamezopinterview.di.component.DaggerApplicationComponent;
import com.nikunj.gamezopinterview.di.module.ApplicationModule;

/**
 * Created by nikunj on 3/27/18.
 */

public class App extends MultiDexApplication {
    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);


    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
