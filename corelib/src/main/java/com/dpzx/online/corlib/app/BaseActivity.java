package com.dpzx.online.corlib.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.dpzx.online.baselib.config.ActivityRequestContext;
import com.dpzx.online.corlib.helper.ActivityManager;
import com.luojilab.component.componentlib.service.AutowiredService;
import com.xlab.core.R;

//import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhxh on 2018/8/7
 */
public abstract class BaseActivity extends AppCompatActivity {
 //   protected CompositeDisposable mDisposables = new CompositeDisposable();
    protected ActivityRequestContext initRequest;

    /**
     * 设置布局
     */
    protected abstract void setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushActivity(this);
        AutowiredService.Factory.getSingletonImpl().autowire(this);

        setLayout();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);

//        if (mDisposables != null) {
//            mDisposables.clear();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回
    protected void goBack() {
        finish();
    }
}
