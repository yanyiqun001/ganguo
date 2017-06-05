package com.gank.di.component;

import com.gank.di.module.ActivityModule;
import com.gank.di.scope.PerActivity;
import com.gank.ui.MainActivity;
import com.gank.ui.base.BaseActivity;
import com.gank.ui.collection.CollectionFragment;
import com.gank.ui.detail.DetailActivity;
import com.gank.ui.main.MainFragment;
import com.gank.ui.main.index.IndexFragment;
import com.gank.ui.main.order.OrderActivity;
import com.gank.ui.main.theme.CommonFragment;
import com.gank.ui.mz.MzFragment;
import com.gank.ui.search.SearchActivity;
import com.gank.ui.search.SearchEmptyFragment;
import com.gank.ui.search.SearchResultFragment;
import com.gank.ui.setting.SettingFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(CommonFragment commonFragment);

    void inject(IndexFragment indexFragment);

    void inject(DetailActivity detailActivity);

    void inject(OrderActivity orderActivity);

    void inject(MainFragment mainFragment);

    void inject(CollectionFragment collectionFragment);

    void inject(MzFragment mzFragment);

    void inject(SearchResultFragment resultFragment);

    void inject(SearchEmptyFragment searchEmptyFragment);

    void inject(SearchActivity searchActivity);

    void inject(SettingFragment settingFragment);

    void inject(BaseActivity baseActivity);

}
