package com.gdou.yudong.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2018-01-24.
 * 发送验证码倒计时类
 */

public class CountTimer extends CountDownTimer {

    private View view;

    //构造函数  第一个参数代表总的计时时长  第二个参数代表计时间隔  单位都是毫秒
    public CountTimer(long millisInFuture, long countDownInterval,View view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.setClickable(false);//禁用60s
        ((Button)view).setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        view.setClickable(true);
        ((Button)view).setText("重新发送");
    }
}
