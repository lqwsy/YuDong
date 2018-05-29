package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Users;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SafeActivity extends BasicActivity {

    @BindView(R.id.btn_safe_back)
    public Button btn_safe_back;
    @BindView(R.id.btn_safe_confirm)
    public Button btn_safe_confirm;
    @BindView(R.id.et_safe_prepsw)
    public EditText et_safe_prepsw;
    @BindView(R.id.et_safe_newpsw)
    public EditText et_safe_newpsw;
    @BindView(R.id.et_safe_confirmpsw)
    public EditText et_safe_confirmpsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_safe_back)
    public void safeBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_safe_confirm)
    public void confirmChangePswClick() {
        String prePsw = et_safe_prepsw.getText().toString().trim();
        String newPsw = et_safe_newpsw.getText().toString().trim();
        String confirmPsw = et_safe_confirmpsw.getText().toString().trim();
        if(prePsw.equals("")&&newPsw.equals("")&&confirmPsw.equals("")){
            Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();
        }else{
            if(newPsw.equals(confirmPsw)){
                updatePassword(Integer.parseInt(getUserInfo("userId","")),newPsw,prePsw);
            }else {
                Toast.makeText(this,"新密码不一致",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updatePassword(int userId ,String newPassword, String pasword){
        String updateUrl = Common.LOCAL_URL + "clientUpdatePasswordController";
        HttpConnectionManager.getInstance().updatePassword(userId,newPassword, pasword, updateUrl, new HttpConnectionManager.UpdatePasswordCallBack() {
            @Override
            public void onResponse(int result) {
                if(result == 1){
                    et_safe_prepsw.setText("");
                    et_safe_newpsw.setText("");
                    et_safe_confirmpsw.setText("");
                    Toast.makeText(SafeActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }else if(result == 2){
                    Toast.makeText(SafeActivity.this,"原密码有误，请重试",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SafeActivity.this,"修改失败，请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
