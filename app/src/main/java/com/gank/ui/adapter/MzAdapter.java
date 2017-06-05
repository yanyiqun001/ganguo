package com.gank.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.base.CommonSimpleAdapter;
import com.gank.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class MzAdapter extends CommonSimpleAdapter<Result>{


    public MzAdapter(Context context, int layoutId, List<Result> datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, Result result, int position) {
        Glide.with(holder.itemView.getContext())
                .load(result.getUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) holder.getView(R.id.mzimage));
    }
}
