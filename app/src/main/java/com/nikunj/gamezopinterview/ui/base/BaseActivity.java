package com.nikunj.gamezopinterview.ui.base;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nikunj.gamezopinterview.App;
import com.nikunj.gamezopinterview.di.component.ActivityComponent;
import com.nikunj.gamezopinterview.di.component.DaggerActivityComponent;
import com.nikunj.gamezopinterview.di.module.ActivityModule;
import com.nikunj.gamezopinterview.utils.NetworkUtils;

/**
 * Created by nikunj on 3/27/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(getApplicationContext());

    }

    protected abstract void setUp();
}
