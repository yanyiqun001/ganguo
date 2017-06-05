package com.gank.ui.search;

import com.gank.data.DataManager;
import com.gank.data.network.response.ThemeResponse;
import com.gank.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class SearchPresenter<V extends SearchView> extends BasePresenter<V> implements SearchMVPPrensenter<V> {
    @Inject
    public SearchPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @Override
    public void loadData(String content, String type, String page) {
        getCompositeDisposable().add(getDataManager()
                .getSearchDataCall(content,type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ThemeResponse>() {
                    @Override
                    public void accept(ThemeResponse themeResponse) throws Exception {
                        getMvpview().showList(themeResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpview().showError();
                    }
                })
        );
    }

    @Override
    public void insertHistory(String content) {
        getDataManager().addSearchHistory(content);
    }

    @Override
    public void loadSearchHistory() {
        getCompositeDisposable().add(getDataManager()
                .querySearchHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> searchHistories) throws Exception {
                        getMvpview().showHistory(searchHistories);
                    }
                })
        );
    }

    @Override
    public void deleteAll() {
        getDataManager().deleteSearchHistory();
    }

    @Override
    public boolean getTheme() {
        return getDataManager().getTheme();
    }


}
