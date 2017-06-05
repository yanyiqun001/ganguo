package com.gank;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.gank.data.DataManager;
import com.gank.di.component.ApplicationComponent;
import com.gank.di.component.DaggerApplicationComponent;
import com.gank.di.module.ApplicationModule;
import com.gank.di.module.NetWorkModule;
import com.gank.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class GankApp extends Application {
    private static Context context;
    private ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        applicationComponent = DaggerApplicationComponent.builder().
                applicationModule(new ApplicationModule(this)).
                netWorkModule(new NetWorkModule(this)).
                build();
        applicationComponent.inject(this);


        new LogUtils.Builder()
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("CMJ")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose

        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(context))
                        .build());

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
     //   LeakCanary.install(this);
         Constants.isNight = dataManager.getTheme();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    public  static Context getAppContext(){
        return context;
    }

}
