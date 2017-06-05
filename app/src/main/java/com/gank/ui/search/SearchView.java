package com.gank.ui.search;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public interface SearchView extends MvpView{
    void showList(List<Result> results);
    void showHistory(List<String> results);
}
