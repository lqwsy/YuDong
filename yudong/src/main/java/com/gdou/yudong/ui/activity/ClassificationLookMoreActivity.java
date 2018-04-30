package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.SearchResultListViewAdapter;

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
        initData();
    }


    private void initData(){
        List<String> bookNameList = new ArrayList<>();
        List<String> bookAuthorList = new ArrayList<>();
        List<String> bookIntroList = new ArrayList<>();
        final List<String> bookUrlList = new ArrayList<>();
        List<String> bookImgList = new ArrayList<>();

        for(int i=0;i<14;i++){
            bookNameList.add("第"+i+"本书");
            bookAuthorList.add("作者"+i+"号");
            bookIntroList.add("第"+i+"个随着新传媒的到来，互联成占据主要上网人员的极大部分之一，网络舆论也正逐渐变成影响大学生思想的主要方式");
            bookUrlList.add("图书地址"+i+"册");
            bookImgList.add("第"+i+"本书图标");
        }

        searchResultListViewAdapter = new SearchResultListViewAdapter(this,bookImgList,bookUrlList,bookNameList,bookAuthorList,bookIntroList);
        lv_classification_lookmore.setAdapter(searchResultListViewAdapter);

        //单击事件
        lv_classification_lookmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ClassificationLookMoreActivity.this,"点击行 "+bookUrlList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        ib_classification_lookmore_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



}
