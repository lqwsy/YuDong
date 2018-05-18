package com.gdou.yudong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class ClassificationLookMoreActivity extends AppCompatActivity {

    @BindView(R.id.ib_classification_lookmore_back)
    public Button ib_classification_lookmore_back;
    @BindView(R.id.tv_classification_lookmore)
    public TextView tv_classification_lookmore;
    @BindView(R.id.lv_classification_lookmore)
    public ListView lv_classification_lookmore;
    private SearchResultListViewAdapter searchResultListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_look_more);
        ButterKnife.bind(this);
        HideIMEUtil.wrap(this);
        String classificationName = getIntent().getExtras().get("look_book_name").toString();
        tv_classification_lookmore.setText(classificationName);
        getClassificationBooks(classificationName, Common.LOCAL_URL+"getClassificationBooks");
    }


    private void initData(final List<Books> moreBooks){
        searchResultListViewAdapter = new SearchResultListViewAdapter(this,moreBooks);
        lv_classification_lookmore.setAdapter(searchResultListViewAdapter);

        //单击事件
        lv_classification_lookmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*跳转到图书详情页*/
                Intent search_intent = new Intent();
                search_intent.setClass(ClassificationLookMoreActivity.this,BookDetailActivity.class);
                search_intent.putExtra("book",moreBooks.get(position));
                startActivity(search_intent);//跳转到图书页面
            }
        });

        ib_classification_lookmore_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    //从服务器获取分类图书
    private List<Books> getClassificationBooks(String classificationName,String url){
        final List<Books> booksList = new ArrayList<>();
        HttpConnectionManager.getInstance().getClassificationBooks(classificationName,url,new HttpConnectionManager.GetClassificationBookCallBack(){
            @Override
            public void onResponse(int result, List<Books> booksList) {
                if(result == 1){
                    initData(booksList);
                }else if(result ==2){
                    Log.i("yudong","get classification failed");
                }
            }
        });
        return booksList;
    }



}
