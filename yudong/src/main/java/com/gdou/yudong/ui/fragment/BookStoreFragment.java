package com.gdou.yudong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookStoreGridViewAdapter;
import com.gdou.yudong.adapter.BookStoreRecyclerViewAdapter;
import com.gdou.yudong.adapter.RecyclerViewOnClickListener;
import com.gdou.yudong.ui.activity.BookStoreSearchActivity;
import com.gdou.yudong.utils.SpacesItemDecoration;

import org.loader.autohideime.HideIMEUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-04-05.
 * 书城fragment
 */

public class BookStoreFragment extends Fragment implements View.OnClickListener,RecyclerViewOnClickListener{

    @BindView(R.id.et_search_book_name)
    public EditText et_search_book_name;
    @BindView(R.id.btn_search)
    public Button btn_search;

    @BindView(R.id.gv_bookstore_classificy_fiction)
    public GridView gv_classificy_fiction;
    @BindView(R.id.gv_bookstore_classificy_literature)
    public GridView gv_classificy_literature;
    @BindView(R.id.gv_bookstore_classificy_biography)
    public GridView gv_classificy_biography;
    @BindView(R.id.gv_bookstore_classificy_history)
    public GridView gv_classificy_history;
    @BindView(R.id.gv_bookstore_classificy_economics)
    public GridView gv_classificy_economics;
    @BindView(R.id.gv_bookstore_classificy_management)
    public GridView gv_classificy_management;
    @BindView(R.id.gv_bookstore_classificy_motivational)
    public GridView gv_classificy_motivational;

    @BindView(R.id.tv_lookmore_fiction)
    public TextView tv_lookmore_fiction;
    @BindView(R.id.tv_lookmore_literature)
    public TextView tv_lookmore_literature;
    @BindView(R.id.tv_lookmore_biography)
    public TextView tv_lookmore_biography;
    @BindView(R.id.tv_lookmore_history)
    public TextView tv_lookmore_history;
    @BindView(R.id.tv_lookmore_economics)
    public TextView tv_lookmore_economics;
    @BindView(R.id.tv_lookmore_management)
    public TextView tv_lookmore_management;
    @BindView(R.id.tv_lookmore_motivational)
    public TextView tv_lookmore_motivational;

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
        HideIMEUtil.wrap(getActivity());
        initData();
        return view;
    }

    private void initData(){
        bookNameList = new ArrayList<>();
        bookImgUrlList = new ArrayList<>();
        bookUrlList = new ArrayList<>();

        for(int i=0;i<6;i++){
            bookNameList.add("bookName:"+i);
            bookImgUrlList.add("bookUrl:"+i);
            bookUrlList.add("book:"+i);
        }

        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),bookNameList,bookImgUrlList,bookUrlList);
        gv_classificy_fiction.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_literature.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_biography.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_history.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_economics.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_management.setAdapter(bookStoreGridViewAdapter);
        gv_classificy_motivational.setAdapter(bookStoreGridViewAdapter);

        //textview初始化
        tv_lookmore_fiction.setOnClickListener(this);
        tv_lookmore_literature.setOnClickListener(this);
        tv_lookmore_biography.setOnClickListener(this);
        tv_lookmore_history.setOnClickListener(this);
        tv_lookmore_economics.setOnClickListener(this);
        tv_lookmore_management.setOnClickListener(this);
        tv_lookmore_motivational.setOnClickListener(this);
        //搜索按钮
        btn_search.setOnClickListener(this);

        //recyclerview 初始化
        bookStoreRecyclerViewAdapter = new BookStoreRecyclerViewAdapter(getActivity(),bookUrlList,bookImgUrlList,bookNameList);
        bookStoreRecyclerViewAdapter.setRecyclerViewOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(bookStoreRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_lookmore_fiction:
                Toast.makeText(getActivity(),"查看小说",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_literature:
                Toast.makeText(getActivity(),"查看文学",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_biography:
                Toast.makeText(getActivity(),"查看传记",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_history:
                Toast.makeText(getActivity(),"查看历史",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_economics:
                Toast.makeText(getActivity(),"查看经济",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_management:
                Toast.makeText(getActivity(),"查看管理",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_lookmore_motivational:
                Toast.makeText(getActivity(),"查看励志",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_search:
                String bookName = et_search_book_name.getText().toString();
                if(!bookName.equals("")){
                    Intent search_intent = new Intent();
                    search_intent.setClass(getActivity(),BookStoreSearchActivity.class);
                    search_intent.putExtra("search_book_name",bookName);
                    startActivity(search_intent);//跳转到图书搜索页
                }else{
                    Toast.makeText(getActivity(),"请输入图书名称",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(),"recycleview:"+bookNameList.get(position),Toast.LENGTH_SHORT).show();
    }
}
