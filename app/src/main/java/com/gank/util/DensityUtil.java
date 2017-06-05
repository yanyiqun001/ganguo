package com.gank.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by hefuyi on 16/7/30.
 */
public class DensityUtil {

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeightWithDecorations(Context context) {
        int heightPixes;
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point realSize = new Point();
        try {
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        heightPixes = realSize.y;
        return heightPixes;
    }

    public static int dip2px(Context context, float dpVale) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String sizeOfImageforFullWidth(Context context, float pxValue) {
        return   "?imageView2/0/w/"
                + getScreenWidth(context) + "/h/" + DensityUtil.dip2px(context, pxValue);
    }



}
