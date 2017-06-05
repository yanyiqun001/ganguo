package com.gank.data.preference;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public interface SharePreferenecesHelper {
    void setOrder(String orderJsonStirng);

    String getOrderString();

    void setTheme(boolean isNight);

    boolean getTheme();
}
