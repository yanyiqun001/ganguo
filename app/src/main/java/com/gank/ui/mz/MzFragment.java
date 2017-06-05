package com.gank.ui.mz;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.Constants;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.MzAdapter;
import com.gank.ui.adapter.base.MultiItemTypeAdapter;
import com.gank.ui.base.BaseEnum;
import com.gank.ui.base.LazyFragment;
import com.gank.util.LogUtils;
import com.gank.util.RecyclerViewUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class MzFragment extends LazyFragment implements MzVIew {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_mz)
    RecyclerView rvMz;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private MzAdapter mzAdapter;
    private int page;
    private boolean isRefresh;
    private List<Result> list=new ArrayList<>();
    @Inject
    MzMVPPresenter<MzVIew> mzMVPPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mz, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mzMVPPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.main2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMz.setLayoutManager(staggeredGridLayoutManager);
        mzAdapter = new MzAdapter(getActivity(),R.layout.item_mz,list);
        rvMz.setAdapter(mzAdapter);

        RecyclerViewUtil.setHeader(getActivity(),refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        isRefresh=true;
                        mzMVPPresenter.loadData(BaseEnum.fuli.getValue() + "/" + Constants.PAGECOUNT + "/" + page);
                    }
                }, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.v("onLoadmore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mzMVPPresenter.loadData(BaseEnum.fuli.getValue() + "/" + Constants.PAGECOUNT + "/" + page);
                    }
                }, 500);
            }

            @Override
            public void onFinishLoadMore() {
                if (Constants.ERROR) {
                    Constants.ERROR = false;
                } else {
                    page++;
                }
                LogUtils.v("onFinishLoadMore");
                super.onFinishLoadMore();
            }

        });

        mzAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), view,getString(R.string.translate_mz));
                    startActivity(new Intent(getActivity(), MzDetail.class).putExtra("img_url",list.get(position).getUrl()), options.toBundle());
                } else {
                    startActivity(new Intent(getActivity(), MzDetail.class).putExtra("img_url",list.get(position).getUrl()));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void loadData() {
        refreshLayout.startRefresh();
    }

    @Override
    public void showView(final List<Result> results) {
        if(isRefresh) {
            mzAdapter.clearData();
            isRefresh=false;
        }
        RecyclerViewUtil.loadMoreSetting(refreshLayout,results);
        mzAdapter.addData(results);
    }


    @Override
    public void showError() {
        Constants.ERROR=true;
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void refreshUI() {
        refreshToolbar(toolbar);
    }
}
