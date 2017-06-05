package com.gank.ui.main.index;

import com.gank.data.DataManager;
import com.gank.data.network.response.Result;
import com.gank.data.network.response.ThemeResponse;
import com.gank.ui.base.BasePresenter;
import com.gank.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class IndexPresenter<V extends IndexView> extends BasePresenter<V> implements IndexMvpPresenter<V> {
    @Inject
    public IndexPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
        LogUtils.v("dagger",DataManager);
        LogUtils.v("dagger",CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @Override
    public void loadData(String path) {
        LogUtils.v(getMvpview());
        final List<Result> list=new ArrayList<>();
        getCompositeDisposable().add(getDataManager()
                .getThemeDataCall(path)
                //将返回值转换为多个Observable<Result>
                .concatMap(new Function<ThemeResponse, ObservableSource<Result>>() {
                    @Override
                    public ObservableSource<Result> apply(ThemeResponse themeResponse) throws Exception {
                        if (!themeResponse.isError()) {
                            return Observable.fromIterable(themeResponse.getResults());
                        }
                        return null;
                    }
                })
                //过滤不需要的项目
                .filter(new Predicate<Result>() {
                    @Override
                    public boolean test(Result result) throws Exception {
                        return filter(result.getType());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        list.add(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpview().showError();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpview().showList(list);
                    }
                }));
    }
    private boolean filter(String requestType) {

        try {
             String type= URLDecoder.decode(requestType,"utf-8");
            if("Android".equals(type)||"iOS".equals(type)||"前端".equals(type)
                    ||"拓展资源".equals(type)) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
