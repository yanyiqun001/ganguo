package com.gank.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.gank.di.qualifier.ApplicationContext;
import com.gank.di.qualifier.PreferenceName;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/18 0018.
 */


public class AppSharePreferences implements  SharePreferenecesHelper{

    private  String PREF_KEY_ORDER="PREF_KEY_ORDER";
    private  String MODE_NIGHT_OR_DAY="MODE_NIGHT_OR_DAY";

    private final SharedPreferences mPrefs;
    @Inject
    public AppSharePreferences(@ApplicationContext  Context context, @PreferenceName String preferencesname) {
        mPrefs = context.getSharedPreferences(preferencesname, Context.MODE_PRIVATE);
    }

    @Override
    public void setOrder(String orderJsonStirng) {
        mPrefs.edit().putString(PREF_KEY_ORDER,orderJsonStirng).commit();
    }

    @Override
    public String getOrderString() {
        return mPrefs.getString(PREF_KEY_ORDER,"");
    }

    @Override
    public void setTheme(boolean isNight) {
        mPrefs.edit().putBoolean(MODE_NIGHT_OR_DAY,isNight).commit();
    }

    @Override
    public boolean getTheme() {
        return mPrefs.getBoolean(MODE_NIGHT_OR_DAY,false);
    }


}
