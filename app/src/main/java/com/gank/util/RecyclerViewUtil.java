package com.gank.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.gank.Constants;
import com.gank.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class RecyclerViewUtil {
    public static void  setHeader(Context context, TwinklingRefreshLayout refreshLayout) {
        ProgressLayout header = new ProgressLayout(context);
        header.setColorSchemeResources(R.color.Blue, R.color.Orange, R.color.Yellow, R.color.Green);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setAutoLoadMore(false);
    }
    public static void  loadMoreSetting(TwinklingRefreshLayout refreshLayout,List list) {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
        if (list.size() >= Constants.PAGECOUNT) {
            refreshLayout.setEnableLoadmore(true);
            refreshLayout.setAutoLoadMore(true);
        }
    }

    /**
     * 使RecyclerView缓存中在pool中的Item失效
     */
    public static void invalidateCacheItem(RecyclerView recyclerView) {
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(recyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
