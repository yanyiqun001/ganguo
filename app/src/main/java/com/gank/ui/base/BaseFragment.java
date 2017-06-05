package com.gank.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.gank.R;
import com.gank.di.component.ActivityComponent;
import com.gank.ui.main.MainFragment;
import com.gank.ui.mz.MzFragment;
import com.gank.ui.setting.SettingFragment;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    Observable<Boolean> observable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void refreshUI();

    public static BaseFragment newInstance(int position) {
        BaseFragment fragment;
        if (position == 0) {
            fragment =new MainFragment();
        } else if(position==1){
            fragment =new MzFragment();
        }else{
            fragment =new SettingFragment();
        }
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            mActivity = activity;
            observable=RxBus.getInstance().register(Boolean.class);
            observable.subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    refreshUI();
                }
            });
        }

    }


    protected ActivityComponent getActivityComponent(){
        return mActivity.getmActivityComponent();
    }

    protected void refreshToolbar(Toolbar toolbar){
        TypedValue typedValue=new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.toolbarcolor,typedValue,true);
        toolbar.setBackgroundColor(getResources().getColor(typedValue.resourceId));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity=null;
        RxBus.getInstance().unregister(Boolean.class,observable);
    }


}
