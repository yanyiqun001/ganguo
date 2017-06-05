package com.gank.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class MyItenTouchCallback extends ItemTouchHelper.Callback {
    private OrderAdapter recyclerViewAdapter;
    private SwapCallBack swapCallBack;
    public MyItenTouchCallback(OrderAdapter recyclerViewAdapter, SwapCallBack swapCallBack) {
        this.recyclerViewAdapter=recyclerViewAdapter;
        this.swapCallBack=swapCallBack;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag;
        int swipeFlag;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                    | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            swipeFlag = 0;
        } else {
            dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
            swipeFlag = 0;
        }
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        swapCallBack.onSwip(fromPosition,toPosition);
        return true;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    public interface SwapCallBack{
       void onSwip(int fromPosition ,int toPosition);
    }


}
