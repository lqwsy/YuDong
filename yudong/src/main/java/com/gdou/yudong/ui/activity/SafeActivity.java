package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdou.yudong.R;
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
        if(newPsw.equals(confirmPsw)){
            if(isTrue(prePsw)){
                Toast.makeText(this,"修改密码成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"原密码不正确",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"新密码不一致",Toast.LENGTH_SHORT).show();
        }
    }

    /*确认旧密码是否正确*/
    private boolean isTrue(String prePsw){
        return false;
    }
}
