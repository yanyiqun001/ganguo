package com.gank.ui;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gank.R;
import com.gank.ui.base.BaseActivity;
import com.gank.ui.base.BaseFragment;
import com.gank.view.OnlyIconItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.tab)
    PageBottomTabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.home, R.drawable.home_selected))
                .addItem(newItem(R.drawable.fuli, R.drawable.fuli_selected))
                .addItem(newItem(R.drawable.mine, R.drawable.mine_selected))
                .build();
        fragmentList.add(BaseFragment.newInstance(0));
        fragmentList.add(BaseFragment.newInstance(1));
        fragmentList.add(BaseFragment.newInstance(2));
        viewpager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewpager.setAdapter(pagerAdapter);

        navigationController.setupWithViewPager(viewpager);

    }

    @Override
    protected void refreshUI() {
        TypedValue bottomColor = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.bottomcolor, bottomColor, true);
        Resources resources = getResources();
        if(tab!=null) {
            tab.setBackgroundColor(resources.getColor(bottomColor.resourceId));
        }

    }



    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable) {
        OnlyIconItemView onlyIconItemView = new OnlyIconItemView(this);
        onlyIconItemView.initialize(drawable, checkedDrawable);
        return onlyIconItemView;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
              //  System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
