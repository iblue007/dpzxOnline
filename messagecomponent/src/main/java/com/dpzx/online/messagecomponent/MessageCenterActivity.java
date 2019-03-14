package com.dpzx.online.messagecomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dpzx.online.baselib.utils.ApplicationUtil;
import com.dpzx.online.corlib.app.BaseActivity;
import com.luojilab.router.facade.annotation.RouteNode;

@RouteNode(path = "/message/messageactivity", desc = "消息中心的页面")
public class MessageCenterActivity extends BaseActivity {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_message_center);

        ApplicationUtil.init(getApplicationContext());
    }


}
