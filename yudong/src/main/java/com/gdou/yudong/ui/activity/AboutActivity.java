package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.gdou.yudong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BasicActivity {

    @BindView(R.id.btn_about_back)
    public Button btn_about_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_about_back)
    public void aboutBackClick(){
        onBackPressed();
    }

}
