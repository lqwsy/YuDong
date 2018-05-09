package com.gdou.yudong.adapter;

import android.view.View;

import com.gdou.yudong.bean.Books;

import java.util.List;

/**
 * Created by Administrator on 2018-04-28.
 * recyclerview item点击事件回调接口
 */

public interface RecyclerViewOnClickListener {

    void onItemClick(List<Books> booksList, View view, int position);

}
