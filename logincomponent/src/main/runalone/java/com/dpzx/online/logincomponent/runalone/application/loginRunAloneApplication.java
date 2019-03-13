package com.dpzx.online.logincomponent.runalone.application;

import android.util.Log;
import com.dpzx.online.corlib.app.BaseApplication;

//组件化单独模块使用的时候才会调用
public class loginRunAloneApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("======","======loginRunAloneApplication");
    }

}