package com.gdou.yudong.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.gdou.yudong.R;
import com.gdou.yudong.bean.Users;
import com.gdou.yudong.utils.Common;

/**
 * 启动欢迎页,3秒后跳转到登录页面
 * */
public class WelcomeActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //获取SharedPreferences的登录数据
        SharedPreferences sharedPreferences = getSharedPreferences("loginUser",MODE_PRIVATE);
        final String username = sharedPreferences.getString("username","");
        final String nickName = sharedPreferences.getString("nickName","");
        final String imgUrl = sharedPreferences.getString("imgUrl","");

        //定时三秒后跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果用户已登录，则跳转到主页面
                if(!username.equals("") && !nickName.equals("") && !imgUrl.equals("")){
                    Users user = new Users();
                    user.setUserName(username);
                    user.setUserNickName(nickName);
                    user.setHeadImage(imgUrl);
                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    intent.putExtra("cur_user",user);
                    WelcomeActivity.this.startActivity(intent);
                    WelcomeActivity.this.finish();
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    WelcomeActivity.this.startActivity(intent);
                    WelcomeActivity.this.finish();

                    //跳转测试，去掉登录过程
                    /*WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this,HomeActivity.class));
                    WelcomeActivity.this.finish();*/
                }
            }
        }, Common.SPLASH_DISPLAY_LENGHT);
    }
}
