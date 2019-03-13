package com.dpzx.online.msg;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.dpzx.online.R;

/**
 * <p><b>Package:</b> com.zhxh.xjimudemo.msg </p>
 * <p><b>Project:</b> XJimuDemo </p>
 * <p><b>Classname:</b> Foo </p>
 * <p><b>Description:</b> TODO </p>
 * Created by zhxh on 2018/6/7.
 */
public abstract class Foo extends Activity {
    protected TextView tvMsg;
    protected TextView btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        tvMsg = findViewById(R.id.demo_msg_tv);
        btn = findViewById(R.id.demo_msg_btn);
        btn.setText(getBtnText());
        btn.setMovementMethod(LinkMovementMethod.getInstance());
        btn.setHighlightColor(Color.TRANSPARENT);
    }

    protected abstract CharSequence getBtnText();

}
