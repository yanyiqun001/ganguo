package com.gank.ui.collection;

import com.gank.data.DataManager;
import com.gank.data.network.response.Result;
import com.gank.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public class CollectionPresenter<V extends CollectionView> extends BasePresenter<V> implements CollectionMVPPresenter<V> {
    @Inject
    public CollectionPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @Override
    public void getCollectionData(int page) {
        getCompositeDisposable().add(getDataManager()
                .queryForList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> collections) throws Exception {
                        getMvpview().showCollections(collections);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpview().showError();
                    }
                })
        );

    }
}
