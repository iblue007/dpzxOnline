package com.dpzx.online;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dpzx.online.baselib.utils.EventBusMessageEvent;
import com.dpzx.online.corlib.app.BaseActivity;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        findViewById(R.id.main_login_cl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.e("======","======click");
                Bundle bundle = new Bundle();
                bundle.putString("bookName", "Gone with the Wind");
                bundle.putString("author", "000000");
                UIRouter.getInstance().openUri(MainActivity.this, "JIMU://login/login/loginmain", bundle);
//                UIRouter.getInstance().openUri(MainActivity.this, "JIMU://login/login/loginmain", null);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessageEvent messageEvent) {
        Log.e("======","======"+messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
