package com.dpzx.online.logincomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpzx.online.baselib.utils.ScreenUtil;


/**
 * Create by xuqunxing on  2019/3/8
 */
public class RegisterActivity extends AppCompatActivity {

    private TextView titleTv;
    private RelativeLayout commonBackRl;
    private ImageView oneStepIv;
    private View lineView;
    private TextView oneStepTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_register);
        titleTv = findViewById(R.id.common_title_tv);
        commonBackRl = findViewById(R.id.common_back_rl);
        titleTv.setText("注册");
        oneStepIv = findViewById(R.id.login_register_1_iv);
        lineView = findViewById(R.id.login_register_line);
        oneStepTv = findViewById(R.id.login_register_1_tv);
        oneStepIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                oneStepIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int oneStepIvHeight = oneStepIv.getHeight();
                int oneStepTvWidth = oneStepTv.getWidth();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
                layoutParams.topMargin = oneStepIvHeight / 2;
                layoutParams.width = ScreenUtil.getCurrentScreenWidth(getApplicationContext()) - (oneStepTvWidth + ScreenUtil.dip2px(getApplicationContext(),40) * 2);
                layoutParams.height = ScreenUtil.dip2px(getApplicationContext(),1);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lineView.setLayoutParams(layoutParams);
            }
        });
    }
}
