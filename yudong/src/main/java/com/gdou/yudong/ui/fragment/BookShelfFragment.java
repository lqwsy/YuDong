package com.gdou.yudong.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookShelfListViewAdapter;
import com.gdou.yudong.network.HttpConnectionManager;
import com.gdou.yudong.ui.activity.BookDetailActivity;
import com.gdou.yudong.utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-04-05.
 */

public class BookShelfFragment extends Fragment{

    @BindView(R.id.lv_mybooks)
    public ListView lv_books;
    private List<String[]> imageUrlList = new ArrayList<>();
    private List<String[]> bookUrlList = new ArrayList<>();
    private BookShelfListViewAdapter bookShelfListViewAdapter;
    private static BookShelfFragment bookShelfFragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        getData(bookUrlList,imageUrlList);

        bookShelfListViewAdapter = new BookShelfListViewAdapter(getActivity(),imageUrlList,bookUrlList);
        lv_books.setAdapter(bookShelfListViewAdapter);
        bookShelfListViewAdapter.notifyDataSetChanged();

    }

    // 获取当前目录下所有的txt文件
    public static List<String> getFileList(String fileAbsolutePath,String fileType) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if(subFile!=null){
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    // 判断是否为type结尾
                    if (filename.trim().toLowerCase().endsWith(fileType)) {
                        vecFile.add(filename);
                    }
                }
            }
        }else {
            Log.i("yudong",fileType + "subFile is == null");
        }
        return vecFile;
    }

    //读取图书
    public void getData(List<String[]> bookList,List<String[]> imgUrl){
        bookList.clear();
        imgUrl.clear();


        List<String> books = getFileList(Common.FILE_PATH,".txt");
        List<String> imgs = getFileList(Common.BOOK_IMAGE_PATH ,".jpg");
        List<String> mapBook = new ArrayList<>();
        List<String> mapImg = new ArrayList<>();


        for(int i=0;i<books.size();i++){
            for(int j=0;j<imgs.size();j++){
                String bookName = books.get(i).substring(0,books.get(i) .lastIndexOf("."));
                String imgName = imgs.get(j).substring(0,imgs.get(j) .lastIndexOf("."));
                if(bookName.equals(imgName)){
                    mapBook.add(books.get(i));
                    mapImg.add(imgs.get(j));
                }
            }
        }

        if(mapBook.size()<12){
            int times = 12-mapBook.size();
            for(int i=0;i<times;i++){
                mapBook.add("temp");
                mapImg.add("temp");
             }
        }

        for(int i=0;i<mapBook.size();i=i+3){
            String[] book = new String[3];
            String[] img = new String[3];
            for(int j=0;j<book.length&&j<(mapBook.size()-i*3);j++){
                book[j] = mapBook.get(i+j);
                img[j] = mapImg.get(i+j);
            }
            bookList.add(book);
            imgUrl.add(img);
        }
    }

    //获取fragment对象
    public static BookShelfFragment getInstence(){
        if(bookShelfFragment == null){
            bookShelfFragment = new BookShelfFragment();
        }
        return bookShelfFragment;
    }

    //下载图书时,更新数据
    public void notifiDataChange(){
        getData(bookUrlList,imageUrlList);
        bookShelfListViewAdapter.notifyDataSetChanged();
    }
}
