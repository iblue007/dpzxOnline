package com.dpzx.online.logincomponent.applike;

import android.util.Log;

import com.dpzx.online.baselib.config.Global;
import com.dpzx.online.logincomponent.LoginComponentImpl;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.xlab.componentservice.ComponentInterface;


public class LoginApplike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();

    @Override
    public void onCreate() {
        Log.e("======","======login_oncreate");
        router.addService(ComponentInterface.class.getSimpleName(), new LoginComponentImpl());
        uiRouter.registerUI("login");
    }

    @Override
    public void onStop() {
        Log.e("======","======login_onStop");
        uiRouter.unregisterUI("login");
    }
}
