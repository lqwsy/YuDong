package com.gdou.yudong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gdou.yudong.R;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
    }

    //通过泛型简化findviewbyid方法
    public final <T> T getView (int id) {
        return (T) findViewById(id);
    }
}
