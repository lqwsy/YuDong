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
import com.gdou.yudong.utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-04-05.
 */

public class BookShelfFragment extends Fragment{

    @BindView(R.id.lv_mybooks)
    public ListView lv_books;
    private List<String[]> imageUrlList;
    private List<String[]> bookUrlList;
    private BookShelfListViewAdapter bookShelfListViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        imageUrlList = new ArrayList<>();
        bookUrlList = new ArrayList<>();

        String[] imageUrl = {"a.jpg","b.jpg","cc.jpg"};
        String[] bookNames = {"秘密——巴比伦尘封6000年的财富智慧","余华-兄弟","《被毁灭的人》[美] 阿尔弗雷德·贝斯特"};
        String[] bookUrl = {"book001.txt","book002.txt","book003.txt"};

        for(int i=0;i<10;i++){
            imageUrlList.add(imageUrl);
            bookUrlList.add(bookUrl);
        }

        List<String> fileList = getBookFileName(Common.FILE_PATH);
        if(fileList!=null && fileList.size()>0){
            for(int i=0;i<fileList.size();i++){
                Log.i("yudong","fileName is === " + fileList.get(i));
            }
        }

        bookShelfListViewAdapter = new BookShelfListViewAdapter(getActivity(),imageUrlList,bookUrlList);
        lv_books.setAdapter(bookShelfListViewAdapter);

    }

    // 获取当前目录下所有的mp4文件
    public static List<String> getBookFileName(String fileAbsolutePath) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if(subFile!=null){
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    // 判断是否为txt结尾
                    if (filename.trim().toLowerCase().endsWith(".txt")) {
                        vecFile.add(filename);
                    }
                }
            }
        }else {
            Log.i("yudong","subFile is == null");
        }
        return vecFile;
    }

}
