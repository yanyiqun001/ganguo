package com.gank.ui.base;

import com.gank.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private final DataManager mDataManager;

    private final CompositeDisposable mCompositeDisposable;

    private V mvpview;
    public BasePresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        this.mDataManager=DataManager;
        this.mCompositeDisposable=CompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public V getMvpview() {
        return mvpview;
    }

    @Override
    public void attachView(V view) {
        this.mvpview=view;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
    }
}
