package com.dpzx.online.baselib.base;

/**
 * Created by Corgi
 * on 2018/7/25.
 */
public class AppOkHttpClientImpl extends OkHttpClientImpl{
    @Override
    protected void addInterceptors() {
        builder.addInterceptor(new HttpCommonInterceptor());
    }
}
