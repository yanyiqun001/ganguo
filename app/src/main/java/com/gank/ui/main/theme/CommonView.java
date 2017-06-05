package com.gank.ui.main.theme;

import com.gank.data.network.response.Result;
import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public interface CommonView extends MvpView{
    void showList(List<Result> resultsBeen);
}
