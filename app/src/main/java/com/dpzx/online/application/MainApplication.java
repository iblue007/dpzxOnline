package com.dpzx.online.application;

import android.content.Context;

import com.dpzx.online.baselib.utils.ApplicationUtil;
import com.dpzx.online.corlib.app.BaseApplication;
import com.dpzx.online.corlib.imageloader.DisplayOptionUtil;
import com.luojilab.component.componentlib.log.ILogger;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.github.jimu.msg.EventManager;

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ILogger.logger.setDefaultTag("JIMU");
        UIRouter.enableDebug();
//        EventManager.appendMapper("com.zhxh.xjimudemo.application", MainProcessMsgService.class);
//        EventManager.appendMapper("com.zhxh.xjimudemo.application:remote", RemoteMsgService.class);

        EventManager.init(this);

        UIRouter.getInstance().registerUI("app");
        //如果isRegisterCompoAuto为false，则需要通过反射加载组件
        //  Router.registerComponent("com.zhxh.reader.applike.ReaderAppLike");
//        Router.registerComponent("com.dpzx.online.logincomponent.applike.LoginApplike");
//        Router.registerComponent("com.dpzx.online.messagecomponent.applike.MessageApplike");
//
//        Router.getInstance().addService(AppComponentEventManager.class.getSimpleName(),
//                EventManager.create(AppComponentEventManager.class));

        ApplicationUtil.init(getApplicationContext());
        initImageLoader(getApplicationContext());
    }

    /**
     * 初始化imageloader
     */
    private void initImageLoader(Context context) {
        try {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(DisplayOptionUtil.DEFAULT_OPTIONS)
                    .build();
            ImageLoader.getInstance().init(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
