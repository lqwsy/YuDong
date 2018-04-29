package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.gdou.yudong.R;
import com.gdou.yudong.adapter.SearchResultListViewAdapter;
import org.loader.autohideime.HideIMEUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookStoreSearchActivity extends BasicActivity {

    @BindView(R.id.et_result_search_book_name)
    public EditText et_search_book_name;
    @BindView(R.id.btn_result_search)
    public Button btn_search;
    @BindView(R.id.lv_bookstore_search_result)
    public ListView result_listview;
    private SearchResultListViewAdapter searchResultListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore_search);
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
        result_listview.setAdapter(searchResultListViewAdapter);

        //单击事件
        result_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(null,"点击行 "+bookUrlList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }



}
