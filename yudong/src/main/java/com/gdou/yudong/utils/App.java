package com.gdou.yudong.utils;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.gdou.yudong.R;

/**
 * Created by admin on 2018/5/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
