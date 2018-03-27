package com.nikunj.gamezopinterview.di.module;

import android.app.Application;
import android.content.Context;

import com.nikunj.gamezopinterview.di.ApplicationContext;
import com.nikunj.gamezopinterview.di.PreferenceInfo;
import com.nikunj.gamezopinterview.data.prefs.SharedPreferenceKeys;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikunj on 3/27/18.
 */

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return SharedPreferenceKeys.PREF_NAME;
    }

}