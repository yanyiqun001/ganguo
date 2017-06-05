package com.gank.ui.main.index;

import com.gank.ui.base.MvpPresenter;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public interface IndexMvpPresenter<V extends IndexView> extends MvpPresenter<V> {
    void loadData(String path);
}
