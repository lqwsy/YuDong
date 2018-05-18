package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.ui.fragment.BookShelfFragment;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;
import com.gdou.yudong.utils.SaveFileUtil;
import com.hw.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.IOException;

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
    private SaveFileUtil saveFileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        book = (Books) getIntent().getExtras().getSerializable("book");
        initData();
    }

    private void initData(){
        new GlideUtils().setImageResource(Common.WEB_BOOK_IMG_URL+book.getBookCoverPath(),this,iv_bookdetail_img);
        saveFileUtil = new SaveFileUtil(this);
        tv_bookdetail_name.setText("书名："+book.getBookName());
        tv_bookdetail_author.setText("作者："+book.getBookAuthor());
        tv_bookdetail_count.setText("阅读量："+book.getBookDownloads().toString());
        tv_bookdetail_intro.setText("简介："+book.getBookIntroduction());

        if(saveFileUtil.ifFileExit(book.getBookName()+".txt")){
            btn_bookdetail_download.setText("阅读");
        }else{
            btn_bookdetail_download.setText("下载");
        }

    }

    @OnClick(R.id.ib_bookdetail_back)
    public void bookDetailBackClick(){
        iv_bookdetail_img.setBackgroundResource(0);
        onBackPressed();
    }


    @OnClick(R.id.btn_bookdetail_download)
    public void downloadClick(){
        if(btn_bookdetail_download.getText().equals("下载")){
            String bookUrl = Common.WEB_BOOK_URL+book.getBookLocation();
            String bookImgUrl = Common.WEB_BOOK_IMG_URL + book.getBookCoverPath();
            //下载电子书
            HttpConnectionManager.getInstance().downloadBook(1,bookUrl,this, book.getBookName()+".txt",
                    new HttpConnectionManager.DownloadBookResultCallBack() {
                        @Override
                        public void onResponse(Boolean result) {
                            if(result){
                                Toast.makeText(BookDetailActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
                                btn_bookdetail_download.setText("阅读");
                                BookShelfFragment.getInstence().notifiDataChange();
                            }else {
                                Toast.makeText(BookDetailActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //下载封面
            HttpConnectionManager.getInstance().downloadBook(2,bookImgUrl,this, book.getBookName()+".jpg",
                    new HttpConnectionManager.DownloadBookResultCallBack() {
                        @Override
                        public void onResponse(Boolean result) {
                            if(result){
                                Toast.makeText(BookDetailActivity.this,"下载封面成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(BookDetailActivity.this,"下载封面失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else if(btn_bookdetail_download.getText().equals("阅读")){
            try {
                HwTxtPlayActivity.loadTxtFile(this, saveFileUtil.getBookFilePath(book.getBookName()+".txt").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
