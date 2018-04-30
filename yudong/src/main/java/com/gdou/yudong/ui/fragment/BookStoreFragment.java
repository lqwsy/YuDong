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

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookStoreGridViewAdapter;
import com.gdou.yudong.adapter.BookStoreRecyclerViewAdapter;
import com.gdou.yudong.adapter.RecyclerViewOnClickListener;
import com.gdou.yudong.ui.activity.BookDetailActivity;
import com.gdou.yudong.ui.activity.BookStoreSearchActivity;
import com.gdou.yudong.ui.activity.ClassificationLookMoreActivity;
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
                turnToClassificationLookMore("小说");
                break;
            case R.id.tv_lookmore_literature:
                turnToClassificationLookMore("文学");
                break;
            case R.id.tv_lookmore_biography:
                turnToClassificationLookMore("传记");
                break;
            case R.id.tv_lookmore_history:
                turnToClassificationLookMore("历史");
                break;
            case R.id.tv_lookmore_economics:
                turnToClassificationLookMore("经济");
                break;
            case R.id.tv_lookmore_management:
                turnToClassificationLookMore("管理");
                break;
            case R.id.tv_lookmore_motivational:
                turnToClassificationLookMore("励志");
                break;
            case R.id.btn_search:
                String bookName = et_search_book_name.getText().toString();
                Intent search_intent = new Intent();
                search_intent.setClass(getActivity(),BookStoreSearchActivity.class);
                search_intent.putExtra("search_book_name",bookName);
                startActivity(search_intent);//跳转到图书搜索页
                /*if(!bookName.equals("")){
                }else{
                    Toast.makeText(getActivity(),"请输入图书名称",Toast.LENGTH_SHORT).show();
                }*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String bookName = bookNameList.get(position);
        turnToBookDetail(bookName);
    }

    /*点击查看更多，跳转到具体分类的页面*/
    private void turnToClassificationLookMore(String classifyName){
        Intent search_intent = new Intent();
        search_intent.setClass(getActivity(),ClassificationLookMoreActivity.class);
        search_intent.putExtra("search_book_name",classifyName);
        startActivity(search_intent);//跳转到查看更多页面
    }

    /*跳转到图书详情页*/
    private void turnToBookDetail(String bookName){
        Intent search_intent = new Intent();
        search_intent.setClass(getActivity(),BookDetailActivity.class);
        search_intent.putExtra("search_book_name",bookName);
        startActivity(search_intent);//跳转到图书页面
    }

}
