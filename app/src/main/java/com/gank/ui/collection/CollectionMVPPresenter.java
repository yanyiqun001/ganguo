package com.gank.ui.collection;

import com.gank.ui.base.MvpPresenter;
import com.gank.ui.base.MvpView;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public interface CollectionMVPPresenter<V extends MvpView> extends MvpPresenter<V>{
    void getCollectionData(int page);
}
