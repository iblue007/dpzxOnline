package com.dpzx.online.msg.event;

import org.github.jimu.msg.bean.EventBean;

/**
 * <p><b>Package:</b> com.zhxh.xjimudemo.msg.event </p>
 * <p><b>Project:</b> XJimuDemo </p>
 * <p><b>Classname:</b> EventA </p>
 * <p><b>Description:</b> TODO </p>
 * Created by zhxh on 2018/6/7.
 */
public class EventA implements EventBean {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public EventA(String msg) {
        this.msg = msg;
    }
}
