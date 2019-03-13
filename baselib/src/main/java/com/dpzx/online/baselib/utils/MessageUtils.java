package com.dpzx.online.baselib.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 消息处理工具类
 */
public class MessageUtils {


    private static Toast mToast;

    public static void show(Context context,final String text) {
        show(context,text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context,final int resId) {
        show(context,context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context,CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
