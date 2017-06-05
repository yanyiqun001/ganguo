package com.gank.data.network.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class Response {

    /**
     * error : false
     */

    @SerializedName("error")
    private boolean error;

    public static Response objectFromData(String str) {

        return new Gson().fromJson(str, Response.class);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
