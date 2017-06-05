package com.gank.ui.main.theme;

import com.gank.ui.base.MvpPresenter;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public interface CommonMvpPresenter<V extends CommonView> extends MvpPresenter<V> {

    void loadData(String path);
}
