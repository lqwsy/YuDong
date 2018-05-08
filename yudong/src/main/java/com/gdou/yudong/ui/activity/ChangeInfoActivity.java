package com.gdou.yudong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdou.yudong.R;
import com.gdou.yudong.utils.CircleTransform;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageLoader;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.List;

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
    public static final int REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_change_info_middle)
    public void mypageSafeClick() {
        openSelector();
    }

    @OnClick(R.id.btn_change_info_back)
    public void changeInfoBackClick() {
        String nickName = et_change_info_nickname.getText().toString().trim();
        if (!nickName.equals("")) {
            changeUserInfo(nickName);
            onBackPressed();
        } else {
            Toast.makeText(this, "请输入用户昵称", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_change_info_confirm)
    public void changeInfoConfirmClick() {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    /*更改用户信息*/
    private void changeUserInfo(String nickName) {
        Toast.makeText(this, "nickname is : " + nickName, Toast.LENGTH_SHORT).show();
    }

    /**/
    private void openSelector() {
        ImageConfig imageConfig = new ImageConfig.Builder(new ImageLoader() {//ImageLoader定义imageseclet的每一项的缩略图的加载
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {//此处暂时若使用picasso会出现无缩略图的状况，没解决
                Glide.with(context)
                        .load(path)
                        .centerCrop()//优化性能作用，与resize方法联用
                        .into(imageView);

            }
        })
                .crop(1, 1, 800, 800)// (截图默认配置：关闭    比例 1：1 裁剪   输出分辨率  500*500)
                .mutiSelect()//（默认为多选）
                .singleSelect()  // 开启单选   （默认为多选）
                .filePath("/ImageSelector/Pictures") // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .showCamera() // 开启拍照功能 （默认关闭）
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(ChangeInfoActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//得到选择的裁剪过的图片的路径
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);//此处暂时为有优化
            for (String path : pathList) {
                Glide.with(ChangeInfoActivity.this).load(path).transform(new CircleTransform(ChangeInfoActivity.this)).into(iv_change_info_img);
                System.out.println(path);
            }
        }
    }

}
