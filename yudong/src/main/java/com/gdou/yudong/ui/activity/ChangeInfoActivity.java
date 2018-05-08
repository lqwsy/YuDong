package com.gdou.yudong.ui.activity;

import com.gdou.yudong.R;
import com.gdou.yudong.utils.Common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeInfoActivity extends BasicActivity {

    @BindView(R.id.ll_change_info_middle)
    public LinearLayout ll_change_info_middle;
    @BindView(R.id.btn_change_info_back)
    public Button btn_change_info_back;
    @BindView(R.id.btn_change_info_confirm)
    public Button btn_change_info_confirm;
    @BindView(R.id.et_change_info_nickname)
    public EditText et_change_info_nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_change_info_middle)
    public void mypageSafeClick() {
        Toast.makeText(this,"上传图片",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_change_info_back)
    public void changeInfoBackClick() {
        String nickName = et_change_info_nickname.getText().toString().trim();
        if(!nickName.equals("")){
            changeUserInfo(nickName);
            onBackPressed();
        }else {
            Toast.makeText(this,"请输入用户昵称",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_change_info_confirm)
    public void changeInfoConfirmClick() {
        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
    }

    /*更改用户信息*/
    private void changeUserInfo(String nickName) {
        Toast.makeText(this,"nickname is : " + nickName,Toast.LENGTH_SHORT).show();
    }
}
