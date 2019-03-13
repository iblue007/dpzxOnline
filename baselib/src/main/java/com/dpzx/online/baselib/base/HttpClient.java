package com.dpzx.online.baselib.base;

import okhttp3.OkHttpClient;

/**
 * Created by Corgi
 * on 2018/7/25.
 */
public interface HttpClient {

    OkHttpClient.Builder getBuilder();

    OkHttpClient getDefaultOkHttpClient();

    OkHttpClient getDefaultTLSOkHttpClient();
}
