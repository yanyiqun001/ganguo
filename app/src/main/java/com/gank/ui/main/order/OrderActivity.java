package com.gank.ui.main.order;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.gank.R;
import com.gank.ui.adapter.MyItenTouchCallback;
import com.gank.ui.adapter.OrderAdapter;
import com.gank.ui.base.BaseActivity;
import com.gank.util.ListUtil;
import com.gank.util.LogUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

import static com.gank.Constants.CLOSESTATUS;
import static com.gank.Constants.OPENSTATUS;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class OrderActivity extends BaseActivity implements OrderView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private List<Order> orderList = new ArrayList<>();
    private List<Order> originList;
    private OrderAdapter orderAdapter ;

    public static final int ORDERCHANGE = 100;

    @Inject
    OrderMvpPresenter<OrderView> presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        getmActivityComponent().inject(this);
        presenter.attachView(this);
        initToolbar(toolbar);
        toolbar.setTitle("拖拽可排序");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChange = compareList(originList, orderList);
                LogUtils.v(originList+"======"+orderList);
                if (isChange) {
                    presenter.setOrderString(orderList);
                    setResult(ORDERCHANGE, new Intent().putExtra("orderlist", (Serializable) orderList));
                }
                finish();
            }
        });
        initRecyclerView();
       //获取栏目列表
        presenter.getOrderList();
    }

    @Override
    protected void refreshUI() {

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //分界线
        TypedArray typedArray = this.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, divider.getIntrinsicHeight());
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final int top = child.getBottom();
                    final int bottom = top + divider.getIntrinsicHeight();
                    divider.setBounds(0, top, parent.getWidth(), bottom);
                    divider.draw(c);

                }
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(new MyItenTouchCallback(orderAdapter, new MyItenTouchCallback.SwapCallBack() {
            @Override
            public void onSwip(int fromPosition, int toPosition) {  //数据交换位置
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(orderList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(orderList, i, i - 1);
                    }
                }
                orderAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        }));
        helper.attachToRecyclerView(recyclerView);

        orderAdapter= new OrderAdapter(this,R.layout.item_order,orderList);
        orderAdapter.setOnItemCheckedChanged(new OrderAdapter.SwitchChangeCallback() {
            @Override
            public void OnChange(int position, boolean isChecked) {
                if (isChecked) {
                    orderList.get(position).setStatus(OPENSTATUS);
                } else {
                    orderList.get(position).setStatus(CLOSESTATUS);
                }
            }
        });
        recyclerView.setAdapter(orderAdapter);

    }

    @Override
    public void showView(List<Order> orders) {
        //复制一份原始list
        try {
            originList= ListUtil.deepCopy(orders);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.v(originList.toString());
        orderAdapter.addData(orders);

    }

    /**
     * 初始化栏目
     */
    @Override
    public void addOrder() {
        String themeItem[] = getResources().getStringArray(R.array.themeItem);
        for (int i = 0; i < themeItem.length; i++) {
            if (i < 4) {
                orderList.add(new Order(themeItem[i], OPENSTATUS));
            } else {
                orderList.add(new Order(themeItem[i], CLOSESTATUS));
            }
        }
        presenter.setOrderString(orderList);
        orderAdapter.notifyItemRangeChanged(0,orderList.size());
    }

    @Override
    public void showError() {

    }

    private boolean compareList(List<Order> list1, List<Order> list2) {
        if (list1 != null && list2 != null) {
            if (list1.size() == list2.size()) {
                for (int i = 0; i < list1.size(); i++) {
                    if (!list1.get(i).equals(list2.get(i))) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}
