package com.gank.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gank.R;
import com.gank.data.DataManager;
import com.gank.ui.base.LazyFragment;
import com.gank.ui.base.RxBus;
import com.gank.ui.collection.CollectionActivity;
import com.gank.util.DataCleanManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.gank.Constants.isNight;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class SettingFragment extends LazyFragment {
    @Bind(R.id.switchCompat)
    SwitchCompat switchCompat;
    @Inject
    DataManager dataManager;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.tv_clean)
    TextView tvClean;
    @Bind(R.id.tv_night)
    TextView tvNight;
    @Bind(R.id.tv_collection)
    TextView tvCollection;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.ll_clean)
    LinearLayout llClean;
    @Bind(R.id.ll_collection)
    LinearLayout llCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("我的");
        llCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CollectionActivity.class));
            }
        });
        //清除缓存
        llClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.cleanApplicationData(getActivity(),new String[0]);
                Snackbar.make(llClean,"清除缓存成功",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loadData() {

        if (isNight) {
            Glide.with(getActivity()).load(R.drawable.night1).crossFade().into(ivBg);
            switchCompat.setChecked(true);
        } else {
            Glide.with(getActivity()).load(R.drawable.day1).crossFade().into(ivBg);
            switchCompat.setChecked(false);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isNight = true;
                    getActivity().setTheme(R.style.NightTheme);
                    Glide.with(getActivity()).load(R.drawable.night1).crossFade().into(ivBg);

                } else {
                    isNight = false;
                    getActivity().setTheme(R.style.DayTheme);
                    Glide.with(getActivity()).load(R.drawable.day1).crossFade().into(ivBg);
                }
                dataManager.setTheme(isNight);
                RxBus.getInstance().post(isNight);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void refreshUI() {
        refreshToolbar(toolbar);
        TypedValue bottomline = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.bottomline, bottomline, true);
        line1.setBackgroundResource(bottomline.resourceId);
        line2.setBackgroundResource(bottomline.resourceId);
        line3.setBackgroundResource(bottomline.resourceId);
    }
}
