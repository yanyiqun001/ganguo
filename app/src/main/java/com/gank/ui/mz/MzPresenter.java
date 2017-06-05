package com.gank.ui.mz;

import com.gank.data.DataManager;
import com.gank.data.network.response.ThemeResponse;
import com.gank.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class MzPresenter<V extends MzVIew> extends BasePresenter<V> implements MzMVPPresenter<V>{
    @Inject
    public MzPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }


    @Override
    public void loadData(String path) {
        getCompositeDisposable().add(
        getDataManager().getThemeDataCall(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ThemeResponse>() {
                    @Override
                    public void accept(ThemeResponse themeResponse) throws Exception {
                        getMvpview().showView(themeResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpview().showError();
                    }
                }));

    }
}
