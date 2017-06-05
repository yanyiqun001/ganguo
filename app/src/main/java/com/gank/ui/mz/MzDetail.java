package com.gank.ui.mz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gank.R;
import com.gank.ui.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/30 0030.
 */

public class MzDetail extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected int getLayoutId() {
        return R.layout.activcity_mzdetail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initToolbar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MzDetail.super.onBackPressed();
            }
        });
        Glide.with(this).load(getIntent().getStringExtra("img_url")).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(photoView);
    }

    @Override
    protected void refreshUI() {

    }
}
