package com.gank.ui.base;

/**
 * Created by wrh on 16/2/2.
 */
public enum BaseEnum {
     all("all"), Android("Android"),iOS("iOS") , qianduan("前端"),expand("拓展资源"),fuli("福利"), video("休息视频");

    String value;

    private BaseEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
