package com.gdou.yudong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdou.yudong.R;

import java.util.List;

/**
 * Created by admin on 2018/4/27.
 */

public class BookStoreRecyclerViewAdapter extends RecyclerView.Adapter{

    public ToDayRecyclerViewHolder toDayRecyclerViewHolder;
    private List<String> bookUrlList;
    private List<String> bookImgList;
    private List<String> bookNameList;
    private Context context;

    public BookStoreRecyclerViewAdapter(Context context,List<String> bookUrlList,List<String> bookImgList,List<String> bookNameList){
        this.context = context;
        this.bookUrlList = bookUrlList;
        this.bookImgList = bookImgList;
        this.bookNameList = bookNameList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        toDayRecyclerViewHolder = new ToDayRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recyclerview_bookstore_today_rank,parent,false));

        return toDayRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ToDayRecyclerViewHolder toDayRecyclerViewHolder = (ToDayRecyclerViewHolder) holder;
        toDayRecyclerViewHolder.setData(bookNameList.get(position),bookImgList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookNameList.size();
    }

    public class ToDayRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageButton ib_today_book_img;
        public TextView tv_today_book_name;

        public ToDayRecyclerViewHolder(View itemView) {
            super(itemView);
            ib_today_book_img = itemView.findViewById(R.id.ib_today_book_img);
            tv_today_book_name = itemView.findViewById(R.id.tv_today_book_name);
        }

        public void setData(String bookName,String bookImgUrl){
            ib_today_book_img.setImageResource(R.drawable.right);
            tv_today_book_name.setText(bookName);
        }
    }

}
