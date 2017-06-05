package com.gank.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.base.BaseEnum;
import com.gank.ui.base.BaseFragment;
import com.gank.util.DensityUtil;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class SearchEmptyFragment extends BaseFragment implements SearchView {


    @Bind(R.id.fbl_type)
    FlexboxLayout fblType;
    @Bind(R.id.fbl_history)
    FlexboxLayout fblHistory;
    OnSelectListener listener;
    @Inject
    SearchMVPPrensenter<SearchView> mSearchMVPPrensenter;
    @Bind(R.id.clear)
    TextView clear;

    public static SearchEmptyFragment newInstance() {
        Bundle args = new Bundle();
        SearchEmptyFragment fragment = new SearchEmptyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void refreshUI() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchActivity) {
            listener = (OnSelectListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_first, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mSearchMVPPrensenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFlexbox();
    }

    private void initFlexbox() {
        fblType.setFlexWrap(FlexWrap.WRAP);
        for (BaseEnum nameEnum : BaseEnum.values()) {
            final TextView tv_bq = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tv_bq, fblType, false);
            fblType.addView(tv_bq);
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) tv_bq.getLayoutParams();
            layoutParams.setMargins(DensityUtil.dip2px(getActivity(), 10),
                    DensityUtil.dip2px(getActivity(), 5), 0, DensityUtil.dip2px(getActivity(), 5));
            tv_bq.setText(nameEnum.getValue());
            fblType.getChildAt(0).setSelected(true);
            tv_bq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < fblType.getChildCount(); i++) {
                        fblType.getChildAt(i).setSelected(false);
                    }
                    tv_bq.setSelected(true);
                    listener.onSelected(tv_bq.getText().toString());
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchMVPPrensenter.loadSearchHistory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mSearchMVPPrensenter.onDetach();
    }


    @Override
    public void showError() {

    }

    @Override
    public void showHistory(List<String> results) {
        fblHistory.setFlexWrap(FlexWrap.WRAP);
        fblHistory.removeAllViews();
        for (String content : results) {
            final TextView tv_his = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tv_bq, fblHistory, false);
            fblHistory.addView(tv_his);
            tv_his.setText(content);
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) tv_his.getLayoutParams();
            layoutParams.setMargins(DensityUtil.dip2px(getActivity(), 10),
                    DensityUtil.dip2px(getActivity(), 5), 0, DensityUtil.dip2px(getActivity(), 5));
            tv_his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSearch(tv_his.getText().toString());
                }
            });
        }
    }

    @Override
    public void showList(List<Result> results) {
    }

    @OnClick(R.id.clear)
    public void onClick() {
        mSearchMVPPrensenter.deleteAll();
        mSearchMVPPrensenter.loadSearchHistory();
    }
}
