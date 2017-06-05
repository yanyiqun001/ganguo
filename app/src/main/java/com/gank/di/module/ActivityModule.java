package com.gank.di.module;

import com.gank.di.scope.PerActivity;
import com.gank.ui.collection.CollectionMVPPresenter;
import com.gank.ui.collection.CollectionPresenter;
import com.gank.ui.collection.CollectionView;
import com.gank.ui.detail.DetailMVPPresenter;
import com.gank.ui.detail.DetailPresenter;
import com.gank.ui.detail.DetailView;
import com.gank.ui.main.index.IndexMvpPresenter;
import com.gank.ui.main.index.IndexPresenter;
import com.gank.ui.main.index.IndexView;
import com.gank.ui.main.order.OrderMvpPresenter;
import com.gank.ui.main.order.OrderPresenter;
import com.gank.ui.main.order.OrderView;
import com.gank.ui.main.theme.CommonMvpPresenter;
import com.gank.ui.main.theme.CommonPresenter;
import com.gank.ui.main.theme.CommonView;
import com.gank.ui.mz.MzMVPPresenter;
import com.gank.ui.mz.MzPresenter;
import com.gank.ui.mz.MzVIew;
import com.gank.ui.search.SearchMVPPrensenter;
import com.gank.ui.search.SearchPresenter;
import com.gank.ui.search.SearchView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Module
public class ActivityModule  {
    @Provides
    CommonMvpPresenter<CommonView> provideCommonPresenter(CommonPresenter<CommonView> presenter) {
        return presenter;
    }

    @Provides
    IndexMvpPresenter<IndexView> provideIndexPresenter(IndexPresenter<IndexView> presenter) {
        return presenter;
    }
    @Provides
    @PerActivity
    DetailMVPPresenter<DetailView> provideDetailPresenter(DetailPresenter<DetailView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    OrderMvpPresenter<OrderView> provideOrderPresenter(OrderPresenter<OrderView> presenter) {
        return presenter;
    }

    @Provides
    CollectionMVPPresenter<CollectionView> provideCollectionPresenter(CollectionPresenter<CollectionView> presenter) {
        return presenter;
    }

    @Provides
    SearchMVPPrensenter<SearchView> provideSearchPresenter(SearchPresenter<SearchView> presenter) {
        return presenter;
    }

    @Provides
    MzMVPPresenter<MzVIew> provideMzMVPPresenter(MzPresenter<MzVIew> presenter) {
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


}
