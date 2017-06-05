package com.gank.ui.main.index;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public interface IndexView extends MvpView{
    void showList(List<Result> resultsBeen);

}
