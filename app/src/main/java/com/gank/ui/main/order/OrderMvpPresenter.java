package com.gank.ui.main.order;

import com.gank.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public interface OrderMvpPresenter<V extends OrderView> extends MvpPresenter<V> {
    void setOrderString(List<Order> orderList);
    void getOrderList();
}
