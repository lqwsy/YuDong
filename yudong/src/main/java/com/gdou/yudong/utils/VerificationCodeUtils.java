package com.gdou.yudong.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018-01-24.
 */

public class VerificationCodeUtils {

    private Activity activity;

    public VerificationCodeUtils(Activity activity){
        this.activity = activity;
    }

    //保存验证码到sharedpreferences
    public void saveVerificationCodeToSharedPreferences(String verificationCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("verificationCode", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("verificationCode", verificationCode);//添加验证码
        editor.putString("verificationCodeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//验证码时间
        editor.commit();//保存到本地
    }

    //判断验证码是否有效
    public boolean isVerificationCodeValuable(String verificationCode) {
        try {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("verificationCode", MODE_PRIVATE);
            String localVerificationCode = sharedPreferences.getString("verificationCode", "");
            Date localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sharedPreferences.getString("verificationCodeTime", ""));
            Date nowTime = new Date();
            long time = nowTime.getTime() - localTime.getTime();
            //验证码一致而且时间有效
            if (verificationCode.equals(localVerificationCode)) {
                if(time / 60000 < 5){
                    return true;
                }else{
                    sharedPreferences.edit().clear().commit();//失效清除验证码
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //清空验证码
    public void clearVerificationCod() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("verificationCode", MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().commit();
        }
    }

}
