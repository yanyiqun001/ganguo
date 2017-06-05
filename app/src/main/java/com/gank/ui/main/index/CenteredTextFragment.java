package com.gank.ui.main.index;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.R;
import com.gank.ui.base.BaseFragment;


/**
 * Created by yarolegovich on 25.03.2017.
 */

public class CenteredTextFragment extends BaseFragment {
    public static final String TAG = CenteredTextFragment.class.getSimpleName();
    private static final String EXTRA_TEXT = "text";

    public static BaseFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    //    String text = getArguments().getString(EXTRA_TEXT);
        TextView textView = (TextView) view.findViewById(R.id.text);
    //    textView.setText(text);
    }

    @Override
    protected void refreshUI() {

    }
}
