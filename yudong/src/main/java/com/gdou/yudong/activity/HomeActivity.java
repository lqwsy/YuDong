package com.gdou.yudong.activity;

import android.os.Bundle;
import com.gdou.yudong.R;

public class HomeActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        clearLoginSharedPreferences();//清除登录数据
    }
}
