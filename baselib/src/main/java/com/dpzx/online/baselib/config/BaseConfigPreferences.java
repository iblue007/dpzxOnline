package com.dpzx.online.baselib.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by xuqunxing on  2019/3/14
 */
public class BaseConfigPreferences {
    public static final String NAME = "dpzxsp";
    private static SharedPreferences baseSP;
    private static BaseConfigPreferences baseConfig;

    protected BaseConfigPreferences(Context context) {
        baseSP = context.getSharedPreferences(NAME, 4);
    }

    public static BaseConfigPreferences getInstance(Context context) {
        if (null == baseConfig) {
            baseConfig = new BaseConfigPreferences(context);
        }

        return baseConfig;
    }

    public SharedPreferences getBaseSP() {
        return baseSP;
    }
    //------------------------------------------------------
    public static final String LOGIN_ACCOUNT = "login_account";
    //------------------------------------------------------
    public void setLoginAccount(String count) {
        baseSP.edit().putString(LOGIN_ACCOUNT, count).commit();
    }

    public String getLoginAccount() {
        return baseSP.getString(LOGIN_ACCOUNT, (String)null);
    }

}
