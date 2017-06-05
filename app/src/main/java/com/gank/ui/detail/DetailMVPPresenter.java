package com.gank.ui.detail;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpPresenter;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public interface DetailMVPPresenter<V extends DetailView> extends MvpPresenter<V> {
    void queryIsLike(String id);
    void add(Result result);
    void Cancel(String id);
}
