package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUitls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookDetailActivity extends AppCompatActivity {

    @BindView(R.id.ib_bookdetail_back)
    public ImageButton ib_bookdetail_back;
    @BindView(R.id.iv_bookdetail_img)
    public ImageView iv_bookdetail_img;
    @BindView(R.id.tv_bookdetail_name)
    public TextView tv_bookdetail_name;
    @BindView(R.id.tv_bookdetail_author)
    public TextView tv_bookdetail_author;
    @BindView(R.id.tv_bookdetail_count)
    public TextView tv_bookdetail_count;
    @BindView(R.id.tv_bookdetail_intro)
    public TextView tv_bookdetail_intro;
    @BindView(R.id.btn_bookdetail_download)
    public Button btn_bookdetail_download;
    private Books book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        book = (Books) getIntent().getExtras().getSerializable("book");
        initData();
    }

    private void initData(){
        new GlideUitls().setImageResource(Common.WEB_BOOK_IMG_URL+book.getBookCoverPath(),this,iv_bookdetail_img);
        tv_bookdetail_name.setText(book.getBookName());
        tv_bookdetail_author.setText(book.getBookAuthor());
        tv_bookdetail_count.setText(book.getBookDownloads());
        tv_bookdetail_intro.setText(book.getBookIntroduction());

        ib_bookdetail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @OnClick(R.id.btn_bookdetail_download)
    public void downloadClick(){
        String bookUrl = "http://172.16.0.26:8080/YuDongReader/static/books/"+book.getBookLocation();
        HttpConnectionManager.getInstance().downloadBook(bookUrl,this, book.getBookName(),
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
