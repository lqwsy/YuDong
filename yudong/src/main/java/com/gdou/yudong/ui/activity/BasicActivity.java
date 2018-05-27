package com.gdou.yudong.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gdou.yudong.R;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.ActivityCollector;

public class BasicActivity extends AppCompatActivity {

    public HttpConnectionManager httpConnectionManager;//网络连接

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ActivityCollector.addActivity(getClass(), this);//添加到activity管理器
        httpConnectionManager = HttpConnectionManager.getInstance();//初始化网络连接类
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//从管理器中移除
    }

    /**
     * 保存登录数据到SharedPreferences
     *
     * @param username 登录用户名
     *                 00
     */
    public void saveLoginToSharedPreferences(String username, String password,String nickName,String imgUrl) {
        //在SharedPreferences中保存登录用户数据，下次自动登录
        SharedPreferences sharedPreferences = getSharedPreferences("loginUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);//添加用户名密码
        editor.putString("password", password);
        editor.putString("nickName", nickName);
        editor.putString("imgUrl",imgUrl);
        editor.commit();//保存到本地
    }

    /**
     * 清除SharedPreferences中的登录数据
     */
    public void clearLoginSharedPreferences() {
        //清空在SharedPreferences中保存的登录用户数据
        SharedPreferences sharedPreferences = getSharedPreferences("loginUser", MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().commit();
        }
    }


    //通过泛型简化findviewbyid方法,本次用注解方式了
    public final <T> T getView(int id) {
        return (T) findViewById(id);
    }
}
