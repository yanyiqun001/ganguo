package com.gank.ui.search;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gank.R;
import com.gank.data.network.response.Result;
import com.gank.ui.base.BaseActivity;
import com.gank.ui.base.BaseEnum;
import com.gank.util.ReavalUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.gank.R.id.tv_search;


/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class SearchActivity extends BaseActivity implements  OnSelectListener,SearchView{
    @Bind(R.id.floatButton)
    FloatingActionButton floatButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.mainContainer)
    RelativeLayout mainContainer;
    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(tv_search)
    TextView tvSearch;
    @Bind(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @Bind(R.id.toolbarlayout)
    RelativeLayout toolbarlayout;

    private SearchResultFragment resultfragment;
    private SearchEmptyFragment  emptyFragment;
    private String selectedType;
    @Inject
    SearchMVPPrensenter<SearchView> mSearchMVPPrensenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    //覆盖父类方法 不初始化主题 使用透明主题
    @Override
    protected void initTheme() {

    }
    //覆盖父类方法 手动设置statusbar
    @Override
    public void setColorStatusBar() {
        getmActivityComponent().inject(this);
        mSearchMVPPrensenter.attachView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(mSearchMVPPrensenter.getTheme()) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.color222222));
            }else{
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }
        setFloatingActionButtonBgColor();
    }

    private void setFloatingActionButtonBgColor() {

        if(mSearchMVPPrensenter.getTheme()){
            floatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.night_background)));
        }else{
            floatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        } else {
            SearchActivity.super.initTheme();
            initViews();
            initContent();
        }
    }

    @Override
    protected void refreshUI() {

    }

    private void initContent() {
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        emptyFragment=SearchEmptyFragment.newInstance();
        fragmentTransaction.replace(R.id.fragmentContainer, emptyFragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        toolbar.setVisibility(View.VISIBLE);
        initToolbar(toolbar);
        //如果是夜间模式
        if(mSearchMVPPrensenter.getTheme()) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.color222222));
        }else{
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();

            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    switchFragment(fragmentTransaction,emptyFragment);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(tv_search)
    public void onClick() {
        if(!TextUtils.isEmpty(editSearch.getText().toString())) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("content", editSearch.getText().toString());
            bundle.putString("type", selectedType == null ? BaseEnum.all.getValue() : selectedType);
            if (resultfragment == null) {
                resultfragment = SearchResultFragment.newInstance(bundle);
            } else {
                if (!resultfragment.isAdded()) {
                    resultfragment.setArguments(bundle);
                } else {
                    resultfragment.loadData(editSearch.getText().toString(), selectedType == null ? BaseEnum.all.getValue() : selectedType);
                    return;
                }
            }
            switchFragment(fragmentTransaction, resultfragment);
            //添加查询内容到数据库搜索历史记录
            mSearchMVPPrensenter.insertHistory(editSearch.getText().toString());
        }else{
            Snackbar.make(editSearch,"请输入搜索内容",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void switchFragment(FragmentTransaction ft, Fragment fragment){
        ft.replace(R.id.fragmentContainer,fragment);
        ft.commit();
    }

    @Override
    public void onSelected(String type) {
        selectedType=type;
    }

    @Override
    public void onSearch(String searchContent) {
        editSearch.setText(searchContent);
        tvSearch.performClick();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                floatButton.post(new Runnable() {
                    @Override
                    public void run() {
                        floatButton.setVisibility(View.INVISIBLE);
                    }
                });

                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        ReavalUtils.animateRevealShow(
                this, container,
                floatButton.getWidth() / 2, R.color.white,
                new ReavalUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                    }
                    @Override
                    public void onRevealShow() {
                    }
                    @Override
                    public void onRevealStart() {
                        SearchActivity.super.initTheme();
                        initViews();
                        initContent();
                    }
                }
        );
    }
    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fadeTranslation = new Fade();
        getWindow().setReturnTransition(fadeTranslation);
        fadeTranslation.setDuration(500);
    }

    private void goBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //效果不理想
            // animateRevealHide();
            floatButton.setVisibility(View.VISIBLE);
            defaultBackPressed();

        } else {
            defaultBackPressed();
        }
    }
    private void animateRevealHide() {
        // 退出事件
        ReavalUtils.animateRevealHide(
                this, container,
                floatButton.getWidth() / 2, R.color.white,
                new ReavalUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        floatButton.setVisibility(View.VISIBLE);
                        defaultBackPressed();
                    }
                    @Override
                    public void onRevealShow() {

                    }
                    @Override
                    public void onRevealStart() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    // 默认回退
    private void defaultBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void showError() {

    }

    @Override
    public void showList(List<Result> results) {

    }

    @Override
    public void showHistory(List<String> results) {

    }
}

