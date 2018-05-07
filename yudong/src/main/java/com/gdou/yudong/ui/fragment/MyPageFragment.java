package com.gdou.yudong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.ui.activity.AboutActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-04-05.
 * 我的页面
 */

public class MyPageFragment extends Fragment {

    @BindView(R.id.tv_mypage_about)
    public TextView  tv_mypage_about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage,container,false);
    }

    @OnClick(R.id.tv_mypage_about)
    public void mypageAboutClick(){
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }

}
