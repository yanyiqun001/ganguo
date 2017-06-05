package com.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.gank.R;
import com.gank.ui.adapter.base.CommonSimpleAdapter;
import com.gank.ui.adapter.base.ViewHolder;
import com.gank.ui.main.order.Order;

import java.util.List;

import static com.gank.Constants.OPENSTATUS;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderAdapter extends CommonSimpleAdapter<Order>{
    private SwitchChangeCallback switchChangeCallback;
    public OrderAdapter(Context context, int layoutId, List<Order> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Order order, final int position) {
        holder.setText(R.id.title,order.getTheme());
        ((SwitchCompat)holder.getView(R.id.switchCompat)).setChecked(order.getStatus()==OPENSTATUS);
        ((SwitchCompat)holder.getView(R.id.switchCompat)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchChangeCallback.OnChange(position,isChecked);
            }
        });
    }

    public void setOnItemCheckedChanged(SwitchChangeCallback switchChangeCallback){
        this.switchChangeCallback=switchChangeCallback;
    }

    public interface SwitchChangeCallback{
        void OnChange(int position, boolean isChecked);
    }
}
