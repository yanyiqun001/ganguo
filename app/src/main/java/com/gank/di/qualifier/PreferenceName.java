package com.gank.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceName {
}
