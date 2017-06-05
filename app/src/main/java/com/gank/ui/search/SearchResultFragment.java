package com.gank.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.Constants;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.CommonAdapter;
import com.gank.ui.base.BaseFragment;
import com.gank.ui.detail.DetailActivity;
import com.gank.util.ItemDecoration;
import com.gank.util.RecyclerViewUtil;
import com.gank.util.WrapContentLinearLayoutManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class SearchResultFragment extends BaseFragment implements SearchView {
    @Inject
    SearchMVPPrensenter<SearchView> mSearchMVPPrensenter;
    @Bind(R.id.rv_result)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private CommonAdapter commonAdapter=new CommonAdapter();
    private int page;
    private boolean isRefresh = true;
    private String content;
    private String type;
    private List<Result> list=new ArrayList<>();

    public static SearchResultFragment newInstance(Bundle bundle) {
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_two, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mSearchMVPPrensenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new ItemDecoration(getActivity(),ItemDecoration.VERTICAL_LIST));
        RecyclerViewUtil.setHeader(getActivity(), refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        isRefresh = true;
                        mSearchMVPPrensenter.loadData(content,type,page+"");
                    }
                }, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSearchMVPPrensenter.loadData(content,type,page+"");
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
                super.onFinishLoadMore();
            }

        });

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Result resultbean=list.get(position);
                intent.putExtra("bean", resultbean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setAdapter(commonAdapter);
        loadData(getArguments().getString("content"),getArguments().getString("type"));
    }

    public void loadData(String content,String type) {
        this.content=content;
        this.type=type;
        refreshLayout.startRefresh();
    }

    @Override
    public void showError() {
        Constants.ERROR =true;
        refreshLayout.onFinishLoadMore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mSearchMVPPrensenter.onDetach();
    }

    @Override
    public void showList(List<Result> results) {
        if(isRefresh){
            list.clear();
            isRefresh=false;
        }
        int start=list.size();
        list.addAll(results);
        RecyclerViewUtil.loadMoreSetting(refreshLayout,list);
        commonAdapter.setSearchData(list,true);
        commonAdapter.notifyData(start,results.size());
    }

    @Override
    public void showHistory(List<String> results) {

    }

    @Override
    protected void refreshUI() {

    }
}
