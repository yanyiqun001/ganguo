package com.gank.ui.collection;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public interface CollectionView extends MvpView {
    void showCollections(List<Result> collections);
}
