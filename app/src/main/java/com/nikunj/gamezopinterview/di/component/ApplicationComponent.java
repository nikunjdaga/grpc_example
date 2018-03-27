package com.nikunj.gamezopinterview.di.component;


import android.app.Application;
import android.content.Context;

import com.nikunj.gamezopinterview.App;
import com.nikunj.gamezopinterview.di.ApplicationContext;
import com.nikunj.gamezopinterview.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikunj on 3/27/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();
}