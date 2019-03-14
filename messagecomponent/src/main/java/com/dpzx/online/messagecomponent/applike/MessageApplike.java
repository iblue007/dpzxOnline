package com.dpzx.online.messagecomponent.applike;

import android.util.Log;

import com.dpzx.online.messagecomponent.MessageComponentImpl;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.xlab.componentservice.ComponentInterface;


public class MessageApplike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();

    @Override
    public void onCreate() {
        Log.e("======","======message_oncreate");
        router.addService(ComponentInterface.class.getSimpleName(), new MessageComponentImpl());
        uiRouter.registerUI("message");
    }

    @Override
    public void onStop() {
        Log.e("======","======message_onStop");
        uiRouter.unregisterUI("message");
    }
}
