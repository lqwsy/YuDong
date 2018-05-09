package com.gdou.yudong.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookShelfListViewAdapter;

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

        String[] imageUrl = {"a.jpg","b.jpg","c.jpg"};
        String[] bookNames = {"秘密——巴比伦尘封6000年的财富智慧","余华-兄弟","《被毁灭的人》[美] 阿尔弗雷德·贝斯特"};
        String[] bookUrl = {"book001.txt","book002.txt","book003.txt"};

        for(int i=0;i<10;i++){
            imageUrlList.add(imageUrl);
            bookUrlList.add(bookUrl);
        }

        bookShelfListViewAdapter = new BookShelfListViewAdapter(getActivity(),imageUrlList,bookUrlList);
        lv_books.setAdapter(bookShelfListViewAdapter);


        //初始化下拉控件颜色
/*        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        SystemClock.sleep(2000);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Toast.makeText(getActivity(), "下拉刷新成功", Toast.LENGTH_SHORT).show();
                        srl.setRefreshing(false);
                    }
                }.execute();
            }
        });*/
    }

    // 获取当前目录下所有的mp4文件
    public static List<String> getBookFileName(String fileAbsolutePath) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
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
        return vecFile;
    }

}
