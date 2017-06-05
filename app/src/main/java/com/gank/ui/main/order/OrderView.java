package com.gank.ui.main.order;

import com.gank.ui.base.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public interface OrderView extends MvpView {
     void showView(List<Order> orders);
     void addOrder();
}
