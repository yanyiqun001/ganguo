package com.gank.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.base.BaseActivity;
import com.gank.util.DensityUtil;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.gank.R.id.iv_head;

/**
 * Created by Administrator on 2017/4/10 0010.
 */


public class DetailActivity extends BaseActivity implements DetailView {
    @Bind(iv_head)
    ImageView ivHead;
    @Bind(R.id.wv_detail)
    WebView mWebView;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.pb)
    ProgressBar progressbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.headlayout)
    LinearLayout headlayout;
    @Bind(R.id.space)
    FrameLayout space;

    private String webUrl;

    private boolean isLike;

    private String detailId;
    private String imgUrl;

    private Result result;
    private boolean hasImage;
    @Inject
    DetailMVPPresenter<DetailView> mPresenter;

    private static Field sConfigCallback;

    static {
        try {
            sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            sConfigCallback.setAccessible(true);
        } catch (Exception e) {
            // ignored
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.attachView(this);

        result = getIntent().getParcelableExtra("bean");
        webUrl = result.getUrl();
        detailId = result.getGanhuo_id()==null?result.getId():result.getGanhuo_id();
        result.setId(detailId);
        initView();
        initWebview();
        initListener();
        mPresenter.queryIsLike(detailId);
    }

    @Override
    protected void refreshUI() {

    }


    private void initView() {

        //  initToolbar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle(result.getDesc());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (result.getImages() != null) {
            Glide.with(this)
                    .load(result.getImages().get(0) + DensityUtil.sizeOfImageforFullWidth(this, 200))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .crossFade()
                    .centerCrop()
                    .into(ivHead);
            hasImage=true;
        } else {
            ivHead.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
            hasImage=false;
        }
        progressbar.setVisibility(View.VISIBLE);
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.performClick();
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this,ImageDetailActivity.class).putExtra("imgUrl",result.getImages().get(0)));
            }
        });
    }

    private void initListener() {
        addScrollListener();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_like:
                        if (isLike) {
                            mPresenter.Cancel(detailId);
                            Snackbar.make(toolbar, "已取消收藏", Snackbar.LENGTH_SHORT).show();
                        } else {
                            mPresenter.add(result);
                            Snackbar.make(toolbar, "已收藏", Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void initWebview() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isAvailable()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//网络正常时使用默认缓存策略
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//网络不可用时只使用缓存
        }

//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                super.onProgressChanged(view, newProgress);
//
//            }
//        });



        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressbar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                if(hasImage) {
                    mScrollView.setScrollY(DensityUtil.dip2px(DetailActivity.this, 200));
                }else{
                    mScrollView.setScrollY(0);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //使webview切换页面后高度自适应，防止留下大片空白
                view.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                progressbar.setVisibility(View.GONE);
            }
        });
        mWebView.addJavascriptInterface(this, "App");
        mWebView.loadUrl(webUrl);

    }


    @JavascriptInterface
    public void resize(final float height) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mWebView.setLayoutParams(new FrameLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
                }
            });

    }

    private void addScrollListener() {
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(hasImage) {
                    changeHeaderPosition();
                    changeToolbarAlpha();
                }
            }
        });
    }
    //调整toolbar透明度
    private void changeToolbarAlpha() {
        int scrollY = mScrollView.getScrollY();
        float contentHeight = ivHead.getHeight();
        float ratio = Math.max(1 - scrollY / contentHeight, 0);
        if (ratio == 0) {
            toolbar.getBackground().mutate().setAlpha(0xFF);

        } else {
            toolbar.getBackground().mutate().setAlpha((int) (ratio * 0xFF));
        }

    }

    private void changeHeaderPosition() {
        int scrollY = mScrollView.getScrollY();
        int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
        headlayout.setScrollY(headerScrollY);
        headlayout.requestLayout();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        try {
            if( sConfigCallback!=null )
                sConfigCallback.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        destroy();
        super.onDestroy();
    }

    public void destroy() {
        if (mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();

            try {
                mWebView.destroy();
                mWebView=null;
            } catch (Throwable ex) {

            }
        }
    }

    @Override
    public void showLike() {
        toolbar.getMenu().getItem(0).setIcon(R.drawable.ic_star_black_24dp_red);
        isLike = true;
    }

    @Override
    public void showUnLike() {
        toolbar.getMenu().getItem(0).setIcon(R.drawable.ic_star_black_24dp);
        isLike = false;
    }

    @Override
    public void showError() {
    }


}
