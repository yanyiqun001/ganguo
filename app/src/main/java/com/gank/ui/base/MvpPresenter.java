package com.gank.ui.base;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public interface MvpPresenter<V extends MvpView> {
    void attachView(V view);
    void onDetach();
}
