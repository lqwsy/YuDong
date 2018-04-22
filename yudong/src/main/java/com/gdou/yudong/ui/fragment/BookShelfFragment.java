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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.BookShelfListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-04-05.
 */

public class BookShelfFragment extends Fragment {

    @BindView(R.id.lv_mybooks)
    public ListView lv_books;
    @BindView(R.id.swip_refresh_layout)
    public SwipeRefreshLayout srl;
    private List<String[]> imageUrlList;
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
        String[] imageUrl = {"","",""};
        for(int i=0;i<10;i++){
            imageUrlList.add(imageUrl);
        }
        bookShelfListViewAdapter = new BookShelfListViewAdapter(getActivity(),imageUrlList );
        lv_books.setAdapter(bookShelfListViewAdapter);

        //单击事件
        lv_books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"点击行 "+imageUrlList.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        //长按事件
        lv_books.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"长按行 "+imageUrlList.get(position),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //初始化下拉控件颜色
        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
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
        });
    }

}
