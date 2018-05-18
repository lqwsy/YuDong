package com.gdou.yudong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.gdou.yudong.R;
import com.gdou.yudong.adapter.SearchResultListViewAdapter;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.Common;

import org.loader.autohideime.HideIMEUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookStoreSearchActivity extends BasicActivity {

    @BindView(R.id.et_result_search_book_name)
    public EditText et_search_book_name;
    @BindView(R.id.btn_result_search)
    public Button btn_search;
    @BindView(R.id.ib_search_result_back)
    public Button ib_search_result_back;
    @BindView(R.id.lv_bookstore_search_result)
    public ListView result_listview;
    private SearchResultListViewAdapter searchResultListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore_search);
        ButterKnife.bind(this);
        HideIMEUtil.wrap(this);
        String search_book_name = getIntent().getExtras().get("search_book_name").toString();
        getSearchBooks(search_book_name, Common.LOCAL_URL+"getSearchBooks");
    }

    private void initData(final List<Books> searchBooks){
        searchResultListViewAdapter = new SearchResultListViewAdapter(this,searchBooks);
        result_listview.setAdapter(searchResultListViewAdapter);

        //单击事件
        result_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*跳转到图书详情页*/
                Intent search_intent = new Intent();
                search_intent.setClass(BookStoreSearchActivity.this,BookDetailActivity.class);
                search_intent.putExtra("book",searchBooks.get(position));
                startActivity(search_intent);//跳转到图书页面
            }
        });

        ib_search_result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @OnClick(R.id.btn_result_search)
    public void btnSearchClick(){
        String search_book_name = et_search_book_name.getText().toString().trim();
        if(!search_book_name.equals("")){
            getSearchBooks(search_book_name, Common.LOCAL_URL+"getSearchBooks");
        }else {
            Toast.makeText(this,"请输入书名",Toast.LENGTH_SHORT).show();
        }
    }

    //从服务器获取搜索图书
    private void getSearchBooks(String searchBookName,String url){
        HttpConnectionManager.getInstance().getSearchBooks(searchBookName,url,new HttpConnectionManager.GetSearchBookCallBack(){
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(result == 1){
                    initData(booksList);
                }else if(result ==2){
                    Log.i("yudong","get searchBook failed");
                }
            }
        });
    }


}
