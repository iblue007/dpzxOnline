package com.dpzx.online.logincomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpzx.online.baselib.base.ServerResult;
import com.dpzx.online.baselib.base.ServerResultHeader;
import com.dpzx.online.baselib.bean.VideoPaperBean;
import com.dpzx.online.baselib.config.Global;
import com.dpzx.online.baselib.network.NetApiUtil;
import com.dpzx.online.baselib.utils.MessageUtils;
import com.dpzx.online.baselib.utils.ScreenUtil;
import com.dpzx.online.baselib.utils.ThreadUtil;
import com.dpzx.online.corlib.app.BaseActivity;
import com.dpzx.online.logincomponent.widget.PhoneCodeView;


/**
 * Create by xuqunxing on  2019/3/8
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleTv;
    private RelativeLayout commonBackRl;
    private ImageView oneStepIv;
    private ImageView twoStepIv;
    private ImageView threeStepIv;
    private View lineView;
    private TextView oneStepTv;
    private LinearLayout oneStepLL;
    private LinearLayout threeStepLL;
    private EditText registerPhoneEt;
    private ImageView registerPhoneClearIv;
    private TextView registerCodeTv;
    private PhoneCodeView phoneCodeView;
    private LinearLayout oneStepContentLL;
    private LinearLayout twoStepContentLL;
    private LinearLayout threeStepContentLL;

    @Override
    protected void setLayout() {
        setContentView(R.layout.login_activity_register);
        RelativeLayout backRl = findViewById(R.id.common_back_rl);

        titleTv = findViewById(R.id.common_title_tv);
        commonBackRl = findViewById(R.id.common_back_rl);
        titleTv.setText("注册");
        oneStepIv = findViewById(R.id.login_register_1_iv);
        twoStepIv = findViewById(R.id.login_register_2_iv);
        threeStepIv = findViewById(R.id.login_register_3_iv);
        lineView = findViewById(R.id.login_register_line);
        oneStepTv = findViewById(R.id.login_register_1_tv);
        oneStepLL = findViewById(R.id.login_register_1_ll);
        threeStepLL = findViewById(R.id.login_register_3_ll);
        registerPhoneEt = findViewById(R.id.login_register_phone_et);
        registerPhoneClearIv = findViewById(R.id.login_register_phone_clear);
        registerCodeTv = findViewById(R.id.login_register_code_tv);
        oneStepContentLL = findViewById(R.id.login_register_onestep_ll);
        twoStepContentLL = findViewById(R.id.login_register_twostep_ll);
        threeStepContentLL = findViewById(R.id.login_register_threestep_ll);


        phoneCodeView = findViewById(R.id.login_register_phonecode_view);

        registerCodeTv.setOnClickListener(this);
        oneStepIv.setBackgroundResource(R.drawable.login_register_1_enable);
        oneStepContentLL.setVisibility(View.VISIBLE);
        oneStepIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                oneStepIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int oneStepIvHeight = oneStepIv.getHeight();
                int oneStepLLWidth = oneStepLL.getWidth();
                int threeStepLLWidth = threeStepLL.getWidth();

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
                layoutParams.topMargin = oneStepIvHeight / 2;
                layoutParams.width = ScreenUtil.getCurrentScreenWidth(getApplicationContext()) - (oneStepLLWidth/2 + threeStepLLWidth/2 + ScreenUtil.dip2px(getApplicationContext(),40) * 2);
                layoutParams.height = ScreenUtil.dip2px(getApplicationContext(),1);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lineView.setLayoutParams(layoutParams);
            }
        });
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s) && s.length() > 0){
                    registerPhoneClearIv.setVisibility(View.VISIBLE);

                }else {
                    registerPhoneClearIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneCodeView.setOnInputListener(new PhoneCodeView.OnInputListener() {
            @Override
            public void onSucess(String code) {
                Log.e("======","======onSucess");
                twoStepIv.setBackgroundResource(R.drawable.login_register_complete);
                twoStepContentLL.setVisibility(View.GONE);
                threeStepContentLL.setVisibility(View.VISIBLE);
            }

            @Override
            public void onInput() {
                Log.e("======","======onInput");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == registerCodeTv){
            String phonestr = registerPhoneEt.getText().toString();
            if(TextUtils.isEmpty(phonestr)){
                MessageUtils.show(getApplicationContext(),"常用号码不能为空");
                return;
            }
            oneStepIv.setBackgroundResource(R.drawable.login_register_complete);
            oneStepContentLL.setVisibility(View.GONE);
            twoStepContentLL.setVisibility(View.VISIBLE);
//            ThreadUtil.executeMore(new Runnable() {
//                @Override
//                public void run() {
//                    ServerResult<VideoPaperBean> phoneExit = NetApiUtil.getPhoneExit(phonestr);
//                    boolean requestOK = phoneExit.getCsResult().isRequestOK();
//                    boolean datas = phoneExit.getCsResult().getDatas();
//                    if(!datas){
//
//                        ServerResult<VideoPaperBean> videoPaperBeanServerResult = NetApiUtil.postPhonecode(phonestr);
//                        String resultMessage = videoPaperBeanServerResult.getCsResult().getResultMessage();
//                        if(!TextUtils.isEmpty(resultMessage)){
//                            NetApiUtil.postUserRegister(phonestr,"123456",resultMessage,0+"");
//                        }
//                    }
////                    if (!requestOK) {
////                        Global.runInMainThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                ServerResultHeader csResult = phoneExit.getCsResult();
////                                String resultMessage = csResult.getResultMessage();
////                                MessageUtils.show(getApplicationContext(),resultMessage);
////                                Log.e("======", "======" + resultMessage + "--requestOK:" + requestOK+"--datas:"+datas);
////                            }
////                        });
////                    }
//                }
//            });
        }
    }
}
