package com.gank.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hefuyi on 16/7/30.
 */
public class NetWorkUtils
{
    public static final boolean isNetWorkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null)
            {
                for (NetworkInfo info : networkInfos)
                {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
