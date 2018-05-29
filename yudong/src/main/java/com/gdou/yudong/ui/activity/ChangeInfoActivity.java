package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;

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
    @BindView(R.id.iv_change_info_img)
    public ImageView iv_change_info_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        et_change_info_nickname.setText(getUserInfo("nickName",""));
        GlideUtils.getInstence().setLocalImageResource(getUserInfo("imgUrl",""),this,iv_change_info_img);
    }

    @OnClick(R.id.btn_change_info_back)
    public void changeInfoBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.btn_change_info_confirm)
    public void changeInfoConfirmClick() {
        String nickName = et_change_info_nickname.getText().toString().trim();
        if(!nickName.equals("")){
            Log.i("yudong","userId is === "+Integer.parseInt(getUserInfo("userId","")));
            changeUserInfo(Integer.parseInt(getUserInfo("userId","")),nickName,Common.LOCAL_URL+"clientUpdateUserInfoController");
        }else{
            Toast.makeText(ChangeInfoActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
        }
    }

    public void changeUserInfo(int userId, final String nickName, String Url){
        Log.i("yudong","changeuserinfo");
        ChangeInfoActivity.this.changeUserLocalInfo("nickName",nickName);
        initData();
        Toast.makeText(ChangeInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
        /*HttpConnectionManager.getInstance().updateUserInfo(userId, nickName, Url, new HttpConnectionManager.UpdateUserInfoCallBack() {
            @Override
            public void onResponse(int result) {
                if(result == 1){
                    ChangeInfoActivity.this.changeUserLocalInfo("nickName",nickName);
                    initData();
                    Toast.makeText(ChangeInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangeInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

}
