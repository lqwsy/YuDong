package com.gdou.yudong.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookStoreGridViewAdapter;
import com.gdou.yudong.adapter.BookStoreRecyclerViewAdapter;
import com.gdou.yudong.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-04-05.
 * 书城fragment
 */

public class BookStoreFragment extends Fragment {

    @BindView(R.id.gv_bookstore_classificy)
    public GridView gv_bookstore_classificy;
    @BindView(R.id.rv_today_rank)
    public RecyclerView recyclerView;
    private List<String> bookNameList;//图书封面地址列表
    private List<String> bookImgUrlList;//图书名称地址列表
    private List<String> bookUrlList;//图书地址列表
    private BookStoreGridViewAdapter bookStoreGridViewAdapter;
    private BookStoreRecyclerViewAdapter bookStoreRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        bookNameList = new ArrayList<>();
        bookImgUrlList = new ArrayList<>();
        bookUrlList = new ArrayList<>();

        for(int i=0;i<7;i++){
            bookNameList.add("bookName:"+i);
            bookImgUrlList.add("bookUrl:"+i);
            bookUrlList.add("book:"+i);
        }

        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),bookNameList,bookImgUrlList,bookUrlList);
        gv_bookstore_classificy.setAdapter(bookStoreGridViewAdapter);

        bookStoreRecyclerViewAdapter = new BookStoreRecyclerViewAdapter(getActivity(),bookUrlList,bookImgUrlList,bookNameList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(bookStoreRecyclerViewAdapter);
    }



}
