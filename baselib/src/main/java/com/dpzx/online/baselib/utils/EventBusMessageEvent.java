package com.dpzx.online.baselib.utils;

/**
 * Create by xuqunxing on  2019/3/13
 */
public class EventBusMessageEvent {

    private String message;

    public EventBusMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
