package com.gank.data;

import com.gank.data.database.DbHelper;
import com.gank.data.database.entity.Image;
import com.gank.data.network.ApiHelper;
import com.gank.data.network.response.Result;
import com.gank.data.network.response.ThemeResponse;
import com.gank.data.preference.SharePreferenecesHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@Singleton
public class AppDataManager implements DataManager {
    ApiHelper apiHelper;
    DbHelper  dbHelper;
    SharePreferenecesHelper sharePreferenecesHelper;
    @Inject
    public AppDataManager(ApiHelper apiHelper, DbHelper dbHelper, SharePreferenecesHelper sharePreferenecesHelper) {
        this.dbHelper=dbHelper;
        this.apiHelper=apiHelper;
        this.sharePreferenecesHelper=sharePreferenecesHelper;
    }

    @Override
    public Observable<ThemeResponse> getThemeDataCall(String path) {
        return apiHelper.getThemeDataCall(path);
    }

    @Override
    public Observable<ThemeResponse> getSearchDataCall(String content, String type, String page) {
        return apiHelper.getSearchDataCall(content,type,page);
    }


    @Override
    public Boolean getIsCollnection(String id) {
        return dbHelper.getIsCollnection(id);
    }

    @Override
    public void addConnection(Result result) {
        dbHelper.addConnection(result);
    }

    @Override
    public void addImage(Image img) {
        dbHelper.addImage(img);
    }

    @Override
    public void cancelCollection(String id) {
        dbHelper.cancelCollection(id);
    }



    @Override
    public Observable<List<Result>> queryForList(int offset) {
        return dbHelper.queryForList(offset);
    }

    @Override
    public void addSearchHistory(String content) {
        dbHelper.addSearchHistory(content);
    }

    @Override
    public Observable<List<String>> querySearchHistory() {
        return dbHelper.querySearchHistory();
    }

    @Override
    public void deleteSearchHistory() {
        dbHelper.deleteSearchHistory();
    }

    @Override
    public void setOrder(String orderJsonStirng) {
        sharePreferenecesHelper.setOrder(orderJsonStirng);
    }

    @Override
    public String getOrderString() {
        return sharePreferenecesHelper.getOrderString();
    }

    @Override
    public void setTheme(boolean isNight) {
        sharePreferenecesHelper.setTheme(isNight);
    }

    @Override
    public boolean getTheme() {
        return sharePreferenecesHelper.getTheme();
    }
}
