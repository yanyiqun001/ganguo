package com.gank.ui.main.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class Order implements Serializable{
    private String theme;
    private int status;

    public Order(String theme, int status) {
        this.theme = theme;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (status != order.status) return false;
        return theme != null ? theme.equals(order.theme) : order.theme == null;

    }

    @Override
    public int hashCode() {
        int result = theme != null ? theme.hashCode() : 0;
        result = 31 * result + status;
        return result;
    }
}
