package com.gank.ui.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gank.R;
import com.gank.data.DataManager;
import com.gank.ui.base.LazyFragment;
import com.gank.ui.main.index.IndexFragment;
import com.gank.ui.main.order.Order;
import com.gank.ui.main.order.OrderActivity;
import com.gank.ui.main.theme.CommonFragment;
import com.gank.ui.search.SearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gank.Constants.OPENSTATUS;
import static com.gank.ui.main.order.OrderActivity.ORDERCHANGE;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class MainFragment extends LazyFragment {
    public static final String TAG = MainFragment.class.getSimpleName();

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.icon_add)
    ImageView iconAdd;
    @Inject
    DataManager dataManager;
    @Bind(R.id.bar_layout)
    AppBarLayout barLayout;
    @Bind(R.id.floatButton)
    FloatingActionButton floatButton;
    @Bind(R.id.addlayout)
    RelativeLayout addlayout;
    private List<String> tabNames;
    private List<Fragment> fragmentList;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    private FragmentStatePagerAdapter pagerAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initfbc();
        tabNames = new ArrayList<>();
        fragmentList = new ArrayList<>();
        //固定栏目
        fragmentList.add(IndexFragment.newInstance());
        tabNames.add(getResources().getString(R.string.title1));

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //初始化栏目数据
        String orderString = dataManager.getOrderString();
        if ("".equals(orderString)) {
            viewpager.setOffscreenPageLimit(4);
            tabNames.add(getResources().getString(R.string.title2));
            tabNames.add(getResources().getString(R.string.title3));
            tabNames.add(getResources().getString(R.string.title4));

            fragmentList.add(CommonFragment.newInstance("Android"));
            fragmentList.add(CommonFragment.newInstance("iOS"));
            fragmentList.add(CommonFragment.newInstance("前端"));
        } else {
            Gson gson = new Gson();
            List<Order> orders = gson.fromJson(orderString,
                    new TypeToken<List<Order>>() {
                    }.getType());
            for (Order order : orders) {
                if (order.getStatus() == OPENSTATUS) {
                    tabNames.add(order.getTheme());
                    fragmentList.add(CommonFragment.newInstance(order.getTheme()));
                }
            }
            viewpager.setOffscreenPageLimit(fragmentList.size());
        }
        pagerAdapter = new ViewpagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    private void initfbc() {
        if(dataManager.getTheme()){
            floatButton.setImageResource(R.drawable.ic_search_brone_24dp);
        }else{
            floatButton.setImageResource(R.drawable.ic_search_white_24dp);
        }
    }

    @Override
    public void loadData() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        getActivityComponent().inject(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.icon_add, R.id.floatButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_add:
                startActivityForResult(new Intent(getActivity(), OrderActivity.class), 0);
                break;
            case R.id.floatButton:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), floatButton, floatButton.getTransitionName());
                    startActivity(new Intent(getActivity(), SearchActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
                break;
        }
    }

    @Override
    protected void refreshUI() {
        TypedValue tablayoutcolor = new TypedValue();
        TypedValue addlayoutcolor = new TypedValue();
        TypedValue fbcolor = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.tablayoutbgcolor, tablayoutcolor, true);
        getActivity().getTheme().resolveAttribute(R.attr.addlayout, addlayoutcolor, true);
        getActivity().getTheme().resolveAttribute(R.attr.fbcolor, fbcolor, true);
        tablayout.setBackgroundColor(getResources().getColor(tablayoutcolor.resourceId));
        addlayout.setBackgroundColor(getResources().getColor(addlayoutcolor.resourceId));
        floatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(fbcolor.resourceId)));
        initfbc();
    }



    private class ViewpagerAdapter extends FragmentStatePagerAdapter {

        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ORDERCHANGE) {
            viewpager.setCurrentItem(0);//放在后面的话会先刷新返回时的一页 
            List<Order> orders = (List<Order>) data.getSerializableExtra("orderlist");
            tabNames.clear();
            fragmentList.clear();
            fragmentList.add(IndexFragment.newInstance());
            tabNames.add(getResources().getString(R.string.title1));
            for (Order order : orders) {
                if (order.getStatus() == OPENSTATUS) {
                    tabNames.add(order.getTheme());
                    fragmentList.add(CommonFragment.newInstance(order.getTheme()));
                }
            }

            tablayout.setupWithViewPager(viewpager);
            pagerAdapter.notifyDataSetChanged();
            viewpager.setOffscreenPageLimit(fragmentList.size());

        }
    }
}
