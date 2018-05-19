package com.gdou.yudong.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookStoreGridViewAdapter;
import com.gdou.yudong.adapter.BookStoreRecyclerViewAdapter;
import com.gdou.yudong.adapter.RecyclerViewOnClickListener;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.ui.activity.BookDetailActivity;
import com.gdou.yudong.ui.activity.BookStoreSearchActivity;
import com.gdou.yudong.ui.activity.ClassificationLookMoreActivity;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.SpacesItemDecoration;

import org.loader.autohideime.HideIMEUtil;

import java.io.Serializable;
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
    @BindView(R.id.sv_bookstore_refresh)
    public ScrollView sv_bookstore_refresh;
    @BindView(R.id.rv_today_rank)
    public RecyclerView recyclerView;
    @BindView(R.id.rl_bookstore_content)
    public RelativeLayout rl_bookstore_content;

    public List<Books> fictionBooks;
    public List<Books> literatureBooks;
    public List<Books> historyBooks;
    public List<Books> biographyBooks;
    public List<Books> economicsBooks;
    public List<Books> managementBooks;
    public List<Books> motivationalBooks;


    private BookStoreGridViewAdapter bookStoreGridViewAdapter;
    private BookStoreRecyclerViewAdapter bookStoreRecyclerViewAdapter;
    private HttpConnectionManager httpConnectionManager;
    /**
     * 输入法管理器
     */
    private InputMethodManager mInputMethodManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore,container,false);
        ButterKnife.bind(this,view);
        HideIMEUtil.wrap(getActivity());
        httpConnectionManager = HttpConnectionManager.getInstance();
        loadData(httpConnectionManager);

        return view;
    }

    private void initTodayRankData(List<Books> booksList){
        //输入法管理器初始化
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

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
        bookStoreRecyclerViewAdapter = new BookStoreRecyclerViewAdapter(getActivity(),booksList);
        bookStoreRecyclerViewAdapter.setRecyclerViewOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(40));
        recyclerView.setAdapter(bookStoreRecyclerViewAdapter);
    }

    private void initFictionData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),fictionBooks);
        gv_classificy_fiction.setAdapter(bookStoreGridViewAdapter);
    }

    private void initLiteratureData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),literatureBooks);
        gv_classificy_literature.setAdapter(bookStoreGridViewAdapter);
    }

    private void initBiographyData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),biographyBooks);
        gv_classificy_biography.setAdapter(bookStoreGridViewAdapter);
    }

    private void initHistoryData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),historyBooks);
        gv_classificy_history.setAdapter(bookStoreGridViewAdapter);
    }

    private void initEconomicsData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),economicsBooks);
        gv_classificy_economics.setAdapter(bookStoreGridViewAdapter);
    }

    private void initManagementData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),managementBooks);
        gv_classificy_management.setAdapter(bookStoreGridViewAdapter);
    }

    private void initMotivationalData(){
        //gridview初始化
        bookStoreGridViewAdapter = new BookStoreGridViewAdapter(getActivity(),motivationalBooks);
        gv_classificy_motivational.setAdapter(bookStoreGridViewAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_lookmore_fiction:
                turnToClassificationLookMore("小说",fictionBooks);
                break;
            case R.id.tv_lookmore_literature:
                turnToClassificationLookMore("文学",literatureBooks);
                break;
            case R.id.tv_lookmore_biography:
                turnToClassificationLookMore("传记",biographyBooks);
                break;
            case R.id.tv_lookmore_history:
                turnToClassificationLookMore("历史",historyBooks);
                break;
            case R.id.tv_lookmore_economics:
                turnToClassificationLookMore("经济",economicsBooks);
                break;
            case R.id.tv_lookmore_management:
                turnToClassificationLookMore("管理",managementBooks);
                break;
            case R.id.tv_lookmore_motivational:
                turnToClassificationLookMore("励志",motivationalBooks);
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
    public void onItemClick(List<Books> booksList,View view, int position) {
        turnToBookDetail(booksList.get(position));
    }

    /*点击查看更多，跳转到具体分类的页面*/
    private void turnToClassificationLookMore(String classifyName,List<Books> classificationBooks){
        Intent search_intent = new Intent();
        search_intent.setClass(getActivity(),ClassificationLookMoreActivity.class);
        search_intent.putExtra("look_book_name",classifyName);
        search_intent.putExtra("classificationBooks",(Serializable) classificationBooks);
        startActivity(search_intent);//跳转到查看更多页面
    }

    /*跳转到图书详情页*/
    private void turnToBookDetail(Books book){
        Intent search_intent = new Intent();
        search_intent.setClass(getActivity(),BookDetailActivity.class);
        search_intent.putExtra("book",book);
        startActivity(search_intent);//跳转到图书页面
    }

    private void loadData(HttpConnectionManager httpConnectionManager){
        httpConnectionManager.getTodayRankBooks(Common.LOCAL_URL + "getTodayBookRank", new HttpConnectionManager.GetTodayBookRankCallBack() {
            @Override
            public void onResponse(int result,List<Books> booksList) {
                if(booksList!=null){
                    if(result==1 && booksList.size()>0){
                        rl_bookstore_content.setVisibility(View.VISIBLE);
                        initTodayRankData(booksList);
                    }else if(result == 2){
                        rl_bookstore_content.setVisibility(View.GONE);
                    }
                }else{
                    rl_bookstore_content.setVisibility(View.GONE);
                }

            }
        });
        httpConnectionManager.getClassificationBooks("小说",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        fictionBooks = booksList;
                        initFictionData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("文学",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        literatureBooks = booksList;
                        initLiteratureData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("传记",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        biographyBooks = booksList;
                        initBiographyData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("历史",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        historyBooks = booksList;
                        initHistoryData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("经济",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        economicsBooks = booksList;
                        initEconomicsData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("管理",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        managementBooks = booksList;
                        initManagementData();
                    }
                }
            }
        });
        httpConnectionManager.getClassificationBooks("励志",Common.LOCAL_URL + "getClassificationBooks", new HttpConnectionManager.GetClassificationBookCallBack() {
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(booksList!=null){
                    if(result == 1){
                        motivationalBooks = booksList;
                        initMotivationalData();
                    }
                }
            }
        });
    }

}
