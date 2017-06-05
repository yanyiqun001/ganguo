package com.gank.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.gank.R;
import com.gank.ui.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class ImageDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.photo_view)
    PhotoView photoView;
    @Bind(R.id.pb)
    ProgressBar pb;

    @Override
    protected int getLayoutId() {
        return R.layout.acticity_imagedetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        String url = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(photoView) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        pb.setVisibility(View.GONE);
                    }
                });
        initToolbar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));

    }

    @Override
    protected void refreshUI() {

    }
}
