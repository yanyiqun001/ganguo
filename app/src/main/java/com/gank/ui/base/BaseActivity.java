package com.gank.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gank.GankApp;
import com.gank.R;
import com.gank.data.DataManager;
import com.gank.di.component.ActivityComponent;
import com.gank.di.component.DaggerActivityComponent;
import com.gank.di.module.ActivityModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;



/**
 * Created by Administrator on 2017/3/27 0027.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Inject
    DataManager dataManager;
    ActivityComponent mActivityComponent;
    Observable<Boolean> observable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        mActivityComponent.inject(this);
        initTheme();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setColorStatusBar();
        observable=RxBus.getInstance().register(Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                showAnimation();
                refreshBackground();
                refreshUI();
                refreshsSatusBar();
            }
        });
    }



    protected void initTheme() {
        boolean isNightMode = dataManager.getTheme();
        if (isNightMode) {
            setTheme(R.style.NightTheme);
        }else{
            setTheme(R.style.DayTheme);
        }
    }
    protected  void refreshBackground(){
        TypedValue bgroundcolor = new TypedValue();
        View window = getWindow().getDecorView();
        getTheme().resolveAttribute(R.attr.backgroundcolor, bgroundcolor, true);
        window.setBackgroundColor(getResources().getColor(bgroundcolor.resourceId));
    };

    private void refreshsSatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue statusBarColor = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimaryDark, statusBarColor, true);
            Resources resources = getResources();
            getWindow().setStatusBarColor(resources.getColor(statusBarColor.resourceId));
        }

    }
    private void showAnimation() {
        final View decorview = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorview);
        if (decorview instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackground(new BitmapDrawable((getResources()), cacheBitmap));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorview).addView(view, layoutParams);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorview).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnable = true;
        view.setDrawingCacheEnabled(drawingCacheEnable);
        view.buildDrawingCache(drawingCacheEnable);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
    protected abstract void refreshUI();


    private void inject() {
        mActivityComponent= DaggerActivityComponent.builder().
                applicationComponent(((GankApp) getApplication()).getApplicationComponent()).
                activityModule(new ActivityModule()).build();
    }

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }


    public void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            TypedValue statusBarColor = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimaryDark, statusBarColor, true);
            Resources resources = getResources();
            window.setStatusBarColor(resources.getColor(statusBarColor.resourceId));
        }
    }



     public void initToolbar(Toolbar toolbar) {
       setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    protected abstract int  getLayoutId();

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        RxBus.getInstance().unregister(Boolean.class,observable);
        super.onDestroy();
    }
}
