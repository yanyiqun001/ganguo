package com.gank.data.network;

import com.gank.data.network.response.ThemeResponse;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public interface ApiHelper {
    Observable<ThemeResponse> getThemeDataCall(String path);

    Observable<ThemeResponse> getSearchDataCall(String content,String type,String page);
}
