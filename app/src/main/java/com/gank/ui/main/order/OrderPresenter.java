package com.gank.ui.main.order;

import com.gank.data.DataManager;
import com.gank.ui.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class OrderPresenter<V extends OrderView> extends BasePresenter<V> implements OrderMvpPresenter<V> {
    private Gson gson=new Gson();
    @Inject
    public OrderPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }


    @Override
    public void setOrderString(List<Order> orderList) {
        String jsonString=gson.toJson(orderList);
        getDataManager().setOrder(jsonString);
    }

    @Override
    public void getOrderList() {
        String orderJsonString=getDataManager().getOrderString();
        List<Order> retList = gson.fromJson(orderJsonString,
                new TypeToken<List<Order>>() {
                }.getType());
        if(retList!=null&&retList.size()>0){
            getMvpview().showView(retList);
        }else{
            getMvpview().addOrder();
        }
    }


}
