package com.gank.ui.search;

import com.gank.ui.base.MvpPresenter;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public interface SearchMVPPrensenter<V extends SearchView> extends MvpPresenter<V>{
    void loadData(String content,String type,String page);
    void insertHistory(String content);
    void loadSearchHistory();
    void deleteAll();
    boolean getTheme();
}
