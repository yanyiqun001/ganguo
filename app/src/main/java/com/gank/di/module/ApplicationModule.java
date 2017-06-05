package com.gank.di.module;

import android.app.Application;
import android.content.Context;

import com.gank.Constants;
import com.gank.GankApp;
import com.gank.data.AppDataManager;
import com.gank.data.DataManager;
import com.gank.data.database.AppDbHelper;
import com.gank.data.database.DbHelper;
import com.gank.data.network.ApiHelper;
import com.gank.data.network.AppApiHelper;
import com.gank.data.preference.AppSharePreferences;
import com.gank.data.preference.SharePreferenecesHelper;
import com.gank.di.qualifier.ApplicationContext;
import com.gank.di.qualifier.DbName;
import com.gank.di.qualifier.PreferenceName;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Module
public class ApplicationModule {
    private final GankApp mApplication;
    public ApplicationModule(GankApp application) {
        this.mApplication=application;
    }

    @ApplicationContext
    @Provides
    Context provideContext(){
        return mApplication;
    }


    @Singleton
    @Provides
    Application provideApplication(){return mApplication;}

    @Singleton
    @Provides
    DataManager provideDataManger(AppDataManager appDataManager){return appDataManager;}

    @Singleton
    @Provides
    ApiHelper privideAqiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }

    @Singleton
    @Provides
    DbHelper privideDbHelper(AppDbHelper appDbHelper){
        return appDbHelper;
    }

    @Singleton
    @Provides
    SharePreferenecesHelper prividePreferenecesHelper(AppSharePreferences appSharePreferences){
        return appSharePreferences;
    }


    @DbName
    @Provides
    String privadeDbName(){
        return Constants.DB_NAME;
    }


    @PreferenceName
    @Provides
    String privadePreferenceName(){
        return Constants.PREFERENCE_NAME;
    }


}
