package com.gank.data.network;

import com.gank.data.network.response.ThemeResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Singleton
public class AppApiHelper implements  ApiHelper{
    ApiService mApiSerVice;

    @Inject
    public AppApiHelper(Retrofit retrofit) {
        this.mApiSerVice=retrofit.create(ApiService.class);
    }

    @Override
    public Observable<ThemeResponse> getThemeDataCall(String path) {
        return mApiSerVice.getThemeDataCall(path);
    }

    @Override
    public Observable<ThemeResponse> getSearchDataCall(String content, String type, String page) {
        return mApiSerVice.getSearchDataCall(content,type,page);
    }


}
