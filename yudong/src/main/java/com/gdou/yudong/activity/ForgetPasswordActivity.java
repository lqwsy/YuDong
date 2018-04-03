package com.gdou.yudong.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdou.yudong.R;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.network.SMSHttpManager;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.CountTimer;
import com.gdou.yudong.utils.VerificationCodeUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 用户忘记密码类
 * */
public class ForgetPasswordActivity extends BasicActivity implements Validator.ValidationListener {

    @BindView(R.id.btn_reset)
    Button btn_reset;

    @BindView(R.id.btn_back)
    Button btn_back;

    @BindView(R.id.btn_sendVerificationCode)
    Button btn_sendVerificationCode;

    @BindView(R.id.edt_username)
    @NotEmpty(messageResId = R.string.errorMessage)
    @Order(1)
    EditText edt_username;

    @BindView(R.id.edt_password)
    @NotEmpty(messageResId = R.string.errorMessage)
    @Order(2)
    EditText edt_password;

    @BindView(R.id.edt_verificationCode)
    @NotEmpty(messageResId = R.string.errorMessage)
    @Order(3)
    EditText edt_verificationCode;

    private Validator validator;//UI校验器
    private HttpConnectionManager httpConnectionManager;//网络连接
    private CountTimer countTimer;//时间计时器
    private VerificationCodeUtils verificationCodeUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);//对绑定的成员赋值
        validator = new Validator(this);//初始化UI校验
        validator.setValidationListener(this);
        httpConnectionManager = HttpConnectionManager.getInstance();//初始化网络连接类
        countTimer = new CountTimer(60000, 1000,btn_sendVerificationCode);//初始化计时类
        verificationCodeUtils = new VerificationCodeUtils(this);//验证码工具类
    }

    //点击重置密码
    @OnClick(R.id.btn_reset)
    public void registerClick() {
        validator.validate();//开启校验
    }

    //点击发送验证码
    @OnClick(R.id.btn_sendVerificationCode)
    public void sendVerificationCodeClick() {
        verificationCodeUtils.clearVerificationCod();
        final String username = edt_username.getText().toString().trim();
        if (!username.equals("")) {
            final String verificationCodes = String.valueOf(SMSHttpManager.smsContentGenernator());//生成系统验证码
            //开启线程发送验证码
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = SMSHttpManager.postSMS(username, verificationCodes);
                    JSONObject jsonObject = JSON.parseObject(result);

                    Log.i("yudong","验证码请求码："+jsonObject.getString("respCode"));

                    //请求成功
                    if (jsonObject.getString("respCode").equals("00000")) {
                        verificationCodeUtils.saveVerificationCodeToSharedPreferences(verificationCodes);//保存验证码，5分钟有效
                        //开启线程更新UI
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                countTimer.start();
                            }
                        });
                    }
                }
            }).start();
        } else {
            Toast.makeText(ForgetPasswordActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();
        }
    }

    //点击返回键,调用系统返回键
    @OnClick(R.id.btn_back)
    public void backClick() {
        onBackPressed();
        ForgetPasswordActivity.this.finish();
    }

    //校验通过
    @Override
    public void onValidationSucceeded() {
        //重置密码post请求URL
        String url = Common.LOCAL_URL + "clientForgetController";
        final String username = edt_username.getText().toString().trim();
        final String password = edt_password.getText().toString().trim();
        final String verificationCode = edt_verificationCode.getText().toString().trim();

        //校验验证码是否正确
        if (verificationCodeUtils.isVerificationCodeValuable(verificationCode)) {
            //向服务器发送重置密码请求
            httpConnectionManager.registerConnect(url, username, password, new HttpConnectionManager.RegisterResultCallBack() {
                @Override
                public void onResponse(int result) {//请求结果返回
                    //result,1:success 2:networkError 3:AccountExit
                    if (result == 1) {
                        Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                        backClick();//返回登录页面
                    } else if (result == 2) {
                        Toast.makeText(ForgetPasswordActivity.this, "连接失败，请检查网络", Toast.LENGTH_SHORT).show();
                    } else if (result == 3) {
                        Toast.makeText(ForgetPasswordActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //验证码不正确
        else {
            Toast.makeText(ForgetPasswordActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
        }
    }

    //校验不通过
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

    @Override
    protected void onStop() {
        super.onStop();
        countTimer.cancel();//取消发送验证码定时器
        btn_sendVerificationCode.setText("发送验证码");
    }
}