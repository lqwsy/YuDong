package com.gdou.yudong.ui.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdou.yudong.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("loginUser",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();//清空登录缓存
    }
}
