package com.gdou.yudong.ui.fragment;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Users;
import com.gdou.yudong.ui.activity.AboutActivity;
import com.gdou.yudong.ui.activity.ChangeInfoActivity;
import com.gdou.yudong.ui.activity.HomeActivity;
import com.gdou.yudong.ui.activity.LoginActivity;
import com.gdou.yudong.ui.activity.SafeActivity;
import com.gdou.yudong.utils.ActivityCollector;
import com.gdou.yudong.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018-04-05.
 * 我的页面
 */

public class MyPageFragment extends Fragment {

    @BindView(R.id.rl_mapage_aboutus)
    public RelativeLayout rl_mapage_aboutus;
    @BindView(R.id.rl_mypage_change_info)
    public RelativeLayout rl_mypage_change_info;
    @BindView(R.id.rl_mypage_safe)
    public RelativeLayout rl_mypage_safe;
    @BindView(R.id.rl_mypage_logout)
    public RelativeLayout rl_mypage_logout;
    @BindView(R.id.iv_mypage_head_img)
    public ImageView iv_mypage_head_img;
    @BindView(R.id.tv_mypage_nickname)
    public TextView tv_mypage_nickname;
    private HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        activity = (HomeActivity) getActivity();
        GlideUtils.getInstence().setLocalImageResource(activity.getUserInfo("imgUrl",""),activity,iv_mypage_head_img);
        tv_mypage_nickname.setText(activity.getUserInfo("nickName",""));
    }

    @OnClick(R.id.rl_mapage_aboutus)
    public void mypageAboutClick(){
        startActivity(new Intent(activity, AboutActivity.class));
    }

    @OnClick(R.id.rl_mypage_change_info)
    public void mypageChangeInfoClick(){
        startActivity(new Intent(activity, ChangeInfoActivity.class));
    }

    @OnClick(R.id.rl_mypage_safe)
    public void mypageSafeClick(){
        startActivity(new Intent(activity, SafeActivity.class));
    }

    @OnClick(R.id.rl_mypage_logout)
    public void mypageLogoutClick(){
        activity.clearLoginSharedPreferences();//退出登陆，清除本地数据
        startActivity(new Intent(activity, LoginActivity.class));
        ActivityCollector.delActivity(activity.getClass());
    }

    public void setActivity(HomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("yudong"," mypage fragment onstart");
        initData();
    }

}
