package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.network.HttpConnectionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookDetailActivity extends AppCompatActivity {

    @BindView(R.id.ib_bookdetail_back)
    public ImageButton ib_bookdetail_back;
    @BindView(R.id.btn_bookdetail_download)
    public Button btn_bookdetail_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        ib_bookdetail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @OnClick(R.id.btn_bookdetail_download)
    public void downloadClick(){
        String bookName = "all.txt";
        String bookUrl = "http://172.16.0.26:8080/YuDongReader/static/books/"+bookName;
        HttpConnectionManager.getInstance().downloadBook(bookUrl,this, bookName,
                new HttpConnectionManager.DownloadBookResultCallBack() {
            @Override
            public void onResponse(Boolean result) {
                if(result){
                    Toast.makeText(BookDetailActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
                    btn_bookdetail_download.setText("阅读");
                }else {
                    Toast.makeText(BookDetailActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
