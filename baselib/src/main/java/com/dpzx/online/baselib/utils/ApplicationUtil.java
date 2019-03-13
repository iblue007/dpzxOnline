package com.dpzx.online.baselib.utils;

import android.content.Context;
import android.os.Handler;

import com.dpzx.online.baselib.base.AppOkHttpClientImpl;
import com.dpzx.online.baselib.base.HttpCommon;
import com.dpzx.online.baselib.config.Global;

/**
 * Create by xuqunxing on  2019/3/8
 */
public class ApplicationUtil {

    public static void init(Context context){
        try {
            Global.setContext(context);
            Global.setHandler(new Handler());
            HttpCommon.initClient(new AppOkHttpClientImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
