package com.gdou.yudong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.ActivityCollector;
import com.gdou.yudong.utils.Common;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户登录处理类
 * */
public class LoginActivity extends BasicActivity implements Validator.ValidationListener {

    //用注解的方式替代findViewById,不能用private修饰变量
    @BindView(R.id.btn_login)
    public Button btn_login;
    @BindView(R.id.btn_register)
    public Button btn_register;

    //绑定并添加校验
    @BindView(R.id.edt_username)
    @NotEmpty(messageResId = R.string.errorMessage)
    @Order(1)//校验顺序
    public EditText edt_username;
    @BindView(R.id.edt_password)
    @NotEmpty(messageResId = R.string.errorMessage)
    @Order(2)//校验顺序
    public EditText edt_password;

    private Validator validator;//UI校验器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);//对绑定的成员赋值

        validator = new Validator(this);//初始化UI校验
        validator.setValidationListener(this);
        clearLoginSharedPreferences();//清除登录信息
    }

    //注解绑定方法响应事件，响应用户登录按钮
    @OnClick(R.id.btn_login)
    public void loginClick(){
        validator.validate();//调用校验
    }

    //点击注册
    @OnClick(R.id.btn_register)
    public void registerClick(){
        startActivity(new Intent(this,RegisterActivity.class));//跳转到注册页面
    }

    //用户点击忘记密码
    @OnClick(R.id.txt_forget)
    public void forgetClick(){
        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));//跳转到重置密码页面
    }

    //校验监听的两个方法
    @Override
    public void onValidationSucceeded() {
        String url = Common.LOCAL_URL + "clientLoginController";
        final String username = edt_username.getText().toString().trim();
        final String password = edt_password.getText().toString().trim();
        //登录结果返回
        httpConnectionManager.loginConnect(url, username, password, new HttpConnectionManager.LoginResultCallBack() {
            @Override
            public void onResponse(int result) {
                //result,1:success 2:networkError 3:noAccount 4:wrongPassword
                if(result == 1){
                    //跳转到主界面
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //缓存头像和个人信息下来

                    //在SharedPreferences中保存登录用户数据，下次自动登录
                    saveLoginToSharedPreferences(username,password);
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    ActivityCollector.delActivity(LoginActivity.class);
                }
                else if(result == 2){
                    Toast.makeText(LoginActivity.this, "无法连接网络", Toast.LENGTH_SHORT).show();
                }else if(result == 3){
                    //提示错误信息
                    Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                }else if(result == 4){
                    //提示错误信息
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}
