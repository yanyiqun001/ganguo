package com.gank.ui.collection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.R;
import com.gank.data.database.entity.Image;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.CommonAdapter;
import com.gank.ui.base.BaseFragment;
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

import static com.gank.R.id.recyclerView;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public class CollectionFragment extends BaseFragment implements CollectionView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    CollectionPresenter<CollectionView> collectionPresenter;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private int page=1;
    private List<Result> list = new ArrayList<>();
    private CommonAdapter mCommonAdapter = new CommonAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        collectionPresenter.attachView(this);
        mActivity.initToolbar(toolbar);
        toolbar.setTitle(R.string.main3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new ItemDecoration(getActivity(),ItemDecoration.VERTICAL_LIST));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mCommonAdapter);
        RecyclerViewUtil.setHeader(getActivity(),refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        list.clear();
                        collectionPresenter.getCollectionData(page);
                    }
                }, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.v("onLoadmore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collectionPresenter.getCollectionData(page);
                    }
                }, 500);
            }

            @Override
            public void onFinishLoadMore() {
                page++;
                LogUtils.v("onFinishLoadMore");
                super.onFinishLoadMore();
            }

        });

        mCommonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Result resultbean=list.get(position);
                if(resultbean.getImg()!=null&&resultbean.getImg().size()>0) {
                    List<String> imgList = new ArrayList<String>();
                    for (Image image : resultbean.getImg()) {
                        imgList.add(image.getImageUrl());
                        resultbean.setImages(imgList);
                    }
                }
                intent.putExtra("bean", resultbean);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.startRefresh();
    }

//    @Override
//    public void loadData() {
//        refreshLayout.startRefresh();
//    }


    @Override
    protected void refreshUI() {
        refreshToolbar(toolbar);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        collectionPresenter.onDetach();
        ButterKnife.unbind(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showCollections(List<Result> collections) {
        list.addAll(collections);
        RecyclerViewUtil.loadMoreSetting(refreshLayout,list);
        mCommonAdapter.setData(list,true);
        mCommonAdapter.notifyDataSetChanged();
    }


}
