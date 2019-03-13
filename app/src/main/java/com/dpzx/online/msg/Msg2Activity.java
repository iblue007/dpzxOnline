package com.dpzx.online.msg;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import com.dpzx.online.msg.event.EventA;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.router.facade.annotation.RouteNode;

import org.github.jimu.msg.ConsumeOn;
import org.github.jimu.msg.EventListener;
import org.github.jimu.msg.EventManager;
import org.github.jimu.msg.bean.ConsumerMeta;

@RouteNode(path = "/msg/demo/2", desc = "主进程页面2")
public class Msg2Activity extends Foo {

    EventListener<EventA> eventAEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventAEventListener = new EventListener<EventA>() {
            @Override
            public void onEvent(EventA event) {
                tvMsg.setText("event has been send and received." + event.getMsg());
            }
        };
//        EventManager.getInstance().subscribe(EventA.class, eventAEventListener);

        AppComponentEventManager manager = (AppComponentEventManager) Router.getInstance()
                .getService(AppComponentEventManager.class.getSimpleName());

        manager.subscribeEventA(ConsumerMeta.<EventA>newBuilder()
                .consumeOn(ConsumeOn.Main)
                .eventListener(eventAEventListener)
                .build());
    }

    @Override
    protected CharSequence getBtnText() {
        SpannableString spans1 = new SpannableString("send eventA");
        spans1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                EventManager.getInstance().postEvent(new EventA("event a from Msg2Activity"));
            }
        }, 0, spans1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spans1;
    }

    @Override
    protected void onDestroy() {
        EventManager.getInstance().unsubscribe(eventAEventListener);
        super.onDestroy();
    }
}
