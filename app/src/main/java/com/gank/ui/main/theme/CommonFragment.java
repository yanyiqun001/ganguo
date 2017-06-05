package com.gank.ui.main.theme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.Constants;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.CommonAdapter;
import com.gank.ui.base.BaseFragment;
import com.gank.ui.base.LazyFragment;
import com.gank.ui.detail.DetailActivity;
import com.gank.util.ItemDecoration;
import com.gank.util.LogUtils;
import com.gank.util.RecyclerViewUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.gank.util.RecyclerViewUtil.invalidateCacheItem;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class CommonFragment extends LazyFragment implements CommonView {
    @Inject
    CommonMvpPresenter<CommonView> commonPresenter;

    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String theme;
    private static final String THEME_ID = "theme_id";
    private int page=1;
    private List<Result> list = new ArrayList<>();
    private CommonAdapter mCommonAdapter = new CommonAdapter();

    public static BaseFragment newInstance(String themeId) {
        Bundle bundle = new Bundle();
        bundle.putString(THEME_ID, themeId);
        BaseFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getArguments().getString(THEME_ID);
    }

    @Override
    protected void refreshUI() {
            mRecyclerView.addItemDecoration(new ItemDecoration(getActivity(), ItemDecoration.VERTICAL_LIST));
            int childCount = mRecyclerView.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++) {
                TextView textView = (TextView) mRecyclerView.getChildAt(childIndex).findViewById(R.id.text);
                TypedValue textColor = new TypedValue();
                getActivity().getTheme().resolveAttribute(R.attr.textcolor, textColor, true);
                textView.setTextColor(getResources().getColor(textColor.resourceId));
            }
            invalidateCacheItem(mRecyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        commonPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new ItemDecoration(getActivity(),ItemDecoration.VERTICAL_LIST));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mCommonAdapter);
        RecyclerViewUtil.setHeader(getActivity(), refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        list.clear();
                        commonPresenter.loadData(theme+"/" + Constants.PAGECOUNT + "/" + page);
                    }
                }, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.v("onLoadmore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        commonPresenter.loadData(theme+"/" + Constants.PAGECOUNT + "/" + page);
                    }
                }, 500);
            }

            @Override
            public void onFinishLoadMore() {
                if( Constants.ERROR){
                    Constants.ERROR=false;
                }else {
                    page++;
                }
                LogUtils.v("onFinishLoadMore");
                super.onFinishLoadMore();
            }

        });

        mCommonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
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
    public void loadData() {
        refreshLayout.startRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        commonPresenter.onDetach();
    }

    @Override
    public void showError() {
        Constants.ERROR=true;
        refreshLayout.finishLoadmore();
    }

    @Override
    public void showList(List<Result> resultList) {

        int startPosition = list.size();
        list.addAll(resultList);
        RecyclerViewUtil.loadMoreSetting(refreshLayout,list);
        mCommonAdapter.setData(list);
        mCommonAdapter.notifyData(startPosition,list.size());
    }
}
