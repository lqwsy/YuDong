package com.gdou.yudong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.ui.activity.AboutActivity;
import com.gdou.yudong.ui.activity.BasicActivity;
import com.gdou.yudong.ui.activity.ChangeInfoActivity;
import com.gdou.yudong.ui.activity.HomeActivity;
import com.gdou.yudong.ui.activity.LoginActivity;
import com.gdou.yudong.ui.activity.SafeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage,container,false);
        ButterKnife.bind(this,view);
        return view;
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
    }

    public void setActivity(HomeActivity activity){
        this.activity = activity;
    }

}
