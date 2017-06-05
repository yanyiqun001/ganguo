package com.gank.di.module;

import com.gank.Constants;
import com.gank.GankApp;
import com.gank.data.network.util.CacheInterceptor;
import com.gank.util.FileUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import static com.gank.Constants.baseUrl;


/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Module
public class NetWorkModule {
     private GankApp mApplication;

    public NetWorkModule(GankApp Application) {
        this.mApplication=Application;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(){
       // Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Gson gson = new Gson();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(FileUtils.getHttpCacheDir(mApplication), Constants.Config.HTTP_CACHE_SIZE))
                .connectTimeout(Constants.Config.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.Config.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new CacheInterceptor())

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();



        return retrofit;
    }
}
