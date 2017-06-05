package com.gank.di.component;

import android.app.Application;
import android.content.Context;

import com.gank.GankApp;
import com.gank.data.DataManager;
import com.gank.di.module.ApplicationModule;
import com.gank.di.module.NetWorkModule;
import com.gank.di.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetWorkModule.class})
public interface ApplicationComponent {
    void inject(GankApp gankApp);

    Application getApplication();

    @ApplicationContext
    Context context();

    DataManager getDataManager();


}
