package com.gank.data.network;

import com.gank.Constants;
import com.gank.data.network.response.ThemeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public interface ApiService {
    @GET("data/{path}")
    Observable<ThemeResponse> getThemeDataCall(@Path("path") String path);

    @GET("search/query/{content}/category/{type}/count/"+ Constants.PAGECOUNT+"/page/{page}")
    Observable<ThemeResponse> getSearchDataCall(@Path("content") String content,@Path("type") String type,@Path("page") String page);
}
