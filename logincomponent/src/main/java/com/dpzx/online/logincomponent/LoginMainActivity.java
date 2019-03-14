package com.dpzx.online.logincomponent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.dpzx.online.baselib.base.ServerResult;
import com.dpzx.online.baselib.base.ServerResultHeader;
import com.dpzx.online.baselib.bean.VideoPaperBean;
import com.dpzx.online.baselib.config.Global;
import com.dpzx.online.baselib.network.NetApiUtil;
import com.dpzx.online.baselib.utils.ApplicationUtil;
import com.dpzx.online.baselib.utils.EventBusMessageEvent;
import com.dpzx.online.baselib.utils.MessageUtils;
import com.dpzx.online.baselib.utils.ThreadUtil;
import com.dpzx.online.corlib.app.BaseActivity;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.router.facade.annotation.RouteNode;

import org.greenrobot.eventbus.EventBus;

@RouteNode(path = "/login/loginmain", desc = "登录的页面")
public class LoginMainActivity extends BaseActivity implements View.OnClickListener {

    private Button loginLoginTv;
    private EditText pwdEt;
    private EditText phoneEt;
    private String pwdStr;
    private String phoneStr;
    private ImageView pwdClear;
    private ImageView phoneClear;
    private TextView toRegisterTv;
    private TextView forgetPwdTv;

    @Override
    protected void setLayout() {
        setContentView(R.layout.login_activity_main);
        ApplicationUtil.init(getApplicationContext());
        loginLoginTv = findViewById(R.id.login_login_tv);
        pwdEt = findViewById(R.id.login_login_pwd_et);
        phoneEt = findViewById(R.id.login_login_phone_et);
        pwdClear = findViewById(R.id.login_login_pwd_clear);
        phoneClear = findViewById(R.id.login_login_phone_clear);
        toRegisterTv = findViewById(R.id.login_login_register_tv);
        forgetPwdTv = findViewById(R.id.login_login_forgetpwd_tv);

        loginLoginTv.setOnClickListener(this);
        pwdClear.setOnClickListener(this);
        phoneClear.setOnClickListener(this);
        toRegisterTv.setOnClickListener(this);
        forgetPwdTv.setOnClickListener(this);


        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdStr = s.toString();
                if ("".equals(pwdStr) || "".equals(phoneStr)) {
                    loginLoginTv.setEnabled(false);
                } else {
                    loginLoginTv.setEnabled(true);
                }
                if(s.length() > 0){
                    pwdClear.setVisibility(View.VISIBLE);
                }else {
                    pwdClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneStr = s.toString();
                if ("".equals(pwdStr) || "".equals(phoneStr)) {
                    loginLoginTv.setEnabled(false);
                } else {
                    loginLoginTv.setEnabled(true);
                }
                if(s.length() > 0){
                    phoneClear.setVisibility(View.VISIBLE);
                }else {
                    phoneClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == loginLoginTv) {
            ThreadUtil.executeMore(new Runnable() {
                @Override
                public void run() {
                    ServerResult<VideoPaperBean> login = NetApiUtil.getLogin(phoneStr, pwdStr, null);
                    boolean requestOK = login.getCsResult().isRequestOK();
                    if (!requestOK) {
                        Global.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ServerResultHeader csResult = login.getCsResult();
                                String resultMessage = csResult.getResultMessage();
                                MessageUtils.show(getApplicationContext(),resultMessage);
                                Log.e("======", "======" + resultMessage + "--requestOK:" + requestOK);
                            }
                        });

                    }
                }
            });
        }else if(v== phoneClear){
            phoneClear.setVisibility(View.GONE);
            phoneEt.setText("");
        }else if(v== pwdClear){
            pwdClear.setVisibility(View.GONE);
            pwdEt.setText("");
        }else if(v == toRegisterTv){

            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        }else if(v == forgetPwdTv){
            EventBus.getDefault().post(new EventBusMessageEvent("欢迎大家浏览我写的博客"));
            //UIRouter.getInstance().openUri(LoginMainActivity.this, "JIMU://message/message/messageactivity", null);
        }
    }


}
