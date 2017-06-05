package com.gank.ui.mz;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public interface MzVIew extends MvpView{
    void showView(List<Result> results);
}
