package com.gank.ui.detail;

import com.gank.data.DataManager;
import com.gank.data.database.entity.Image;
import com.gank.data.network.response.Result;
import com.gank.ui.base.BasePresenter;
import com.gank.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class DetailPresenter<V extends DetailView> extends BasePresenter<V> implements DetailMVPPresenter<V> {

    @Inject
    public DetailPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
        LogUtils.v("dagger",CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }


    @Override
    public void queryIsLike(String id) {
        if (getDataManager().getIsCollnection(id)) {
            getMvpview().showLike();
        } else {
            getMvpview().showUnLike();
        }
    }

    @Override
    public void add(Result result) {

        getDataManager().addConnection(result);
        //本地存储图片
        if (result.getImages() != null) {
            for (String imgUrl : result.getImages()) {
                getDataManager().addImage(new Image(null, imgUrl, result.getId()));
            }
        }
        getMvpview().showLike();

    }


    @Override
    public void Cancel(String id) {
        getDataManager().cancelCollection(id);
        getMvpview().showUnLike();
    }
}
