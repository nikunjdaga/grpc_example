package com.nikunj.gamezopinterview.di.component;


import com.nikunj.gamezopinterview.di.PerActivity;
import com.nikunj.gamezopinterview.di.module.ActivityModule;
import com.nikunj.gamezopinterview.ui.main.MainActivity;
import com.nikunj.gamezopinterview.ui.webview.WebviewActivity;

import dagger.Component;

/**
 * Created by nikunj on 3/27/18.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(WebviewActivity activity);
}
