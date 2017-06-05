package com.gank.ui.mz;

import com.gank.ui.base.MvpPresenter;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public interface MzMVPPresenter<V extends MzVIew> extends MvpPresenter<V> {
    void loadData(String path);
}
