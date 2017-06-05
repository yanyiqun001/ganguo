package com.gank.ui.main.index;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gank.Constants;
import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.adapter.IndexAdapter;
import com.gank.ui.base.BaseFragment;
import com.gank.ui.base.LazyFragment;
import com.gank.ui.detail.DetailActivity;
import com.gank.ui.detail.ImageDetailActivity;
import com.gank.util.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.gank.R.id.tv_top;
import static com.gank.util.RecyclerViewUtil.invalidateCacheItem;

/**
 * Created by Administrator on 2017/4/4 0004.
 */



public class IndexFragment extends LazyFragment implements IndexView {
    @Bind(R.id.rv_index)
    RecyclerView rvIndex;
    @Bind(tv_top)
    TextView stickyView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    private IndexAdapter mAdapter=new IndexAdapter();
    private int page= 1;
    private int mPosition;
    private List<Result> list=new ArrayList<>();
    private  LinearLayoutManager mLinearLayoutManager;
    @Inject
    IndexMvpPresenter<IndexView> indexMvpPresenter;


    public static BaseFragment newInstance() {
        BaseFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void refreshUI() {

        TypedValue bground_itemcolor = new TypedValue();
        TypedValue textcolor = new TypedValue();
        TypedValue toplinecolor = new TypedValue();
        TypedValue bottomlinecolor = new TypedValue();
        TypedValue topviewcolor = new TypedValue();
        TypedValue toptextcolor = new TypedValue();
            Resources.Theme theme = mActivity.getTheme();
            theme.resolveAttribute(R.attr.backgroundcolor_item, bground_itemcolor, true);
            theme.resolveAttribute(R.attr.textcolor, textcolor, true);
            theme.resolveAttribute(R.attr.topline, toplinecolor, true);
            theme.resolveAttribute(R.attr.bottomline, bottomlinecolor, true);
            theme.resolveAttribute(R.attr.topview, topviewcolor, true);
            theme.resolveAttribute(R.attr.toptextcolor, toptextcolor, true);
            Resources resources = getResources();
            stickyView.setBackgroundColor(resources.getColor(topviewcolor.resourceId));
            stickyView.setTextColor(resources.getColor(toptextcolor.resourceId));
            int childCount = rvIndex.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++) {
                LinearLayout ll = (LinearLayout) rvIndex.getChildAt(childIndex).findViewById(R.id.item_container);
                ll.setBackgroundColor(resources.getColor(bground_itemcolor.resourceId));
                TextView title = (TextView) ll.findViewById(R.id.tv_title);
                TextView tv_top = (TextView) ll.findViewById(R.id.tv_top);
                View view1 = ll.findViewById(R.id.topline);
                View view2 = rvIndex.getChildAt(childIndex).findViewById(R.id.bottomline);
                title.setTextColor(resources.getColor(textcolor.resourceId));
                tv_top.setTextColor(resources.getColor(toptextcolor.resourceId));
                tv_top.setBackgroundColor(resources.getColor(topviewcolor.resourceId));
                view1.setBackgroundColor(resources.getColor(toplinecolor.resourceId));
                view2.setBackgroundColor(resources.getColor(bottomlinecolor.resourceId));
            }
            invalidateCacheItem(rvIndex);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        indexMvpPresenter.attachView(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // mAdapter = new IndexAdapter();
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvIndex.setLayoutManager(mLinearLayoutManager);
        rvIndex.setAdapter(mAdapter);
        rvIndex.setItemAnimator(new DefaultItemAnimator());
        //吸顶布局滚动监听
        rvIndex.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        stickyView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    stickyView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        stickyView.getMeasuredWidth() / 2, stickyView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - stickyView.getMeasuredHeight();

                    if (transViewStatus == IndexAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            stickyView.setTranslationY(dealtY);
                        } else {
                            stickyView.setTranslationY(0);
                        }
                    } else if (transViewStatus == IndexAdapter.NONE_STICKY_VIEW) {
                        stickyView.setTranslationY(0);
                    }
                }
            }
        });


        mAdapter.setOnItemClickListener(new IndexAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v,int position) {
                mPosition=position;
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("bean", list.get(position));
                startActivity(intent);
            }

            @Override
            public void OnImageViewClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("imgUrl", list.get(position).getImages().get(0));
                startActivity(intent);
            }
        });


        ProgressLayout header = new ProgressLayout(getActivity());
        header.setColorSchemeResources(R.color.Blue, R.color.Orange, R.color.Yellow, R.color.Green);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page=1;
                        list.clear();
                        indexMvpPresenter.loadData("all/" + Constants.PAGECOUNT + "/" + page);
                    }
                },0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.v("onLoadmore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        indexMvpPresenter.loadData("all/" + Constants.PAGECOUNT + "/" + page);
                        LogUtils.v(page);

                    }
                },1000);
            }


            @Override
            public void onLoadmoreCanceled() {
                LogUtils.v("onLoadmoreCanceled");
                super.onLoadmoreCanceled();
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
    }
    @Override
    public void loadData() {
        refreshLayout.startRefresh();
    }
    @Override
    public void showError() {
        LogUtils.v("error");
        Constants.ERROR=true;
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        indexMvpPresenter.onDetach();
        ButterKnife.unbind(this);
    }


    @Override
    public void showList(List<Result> list) {
        this.list.addAll(list);
        mAdapter.notifyData(this.list);
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();

    }
}
