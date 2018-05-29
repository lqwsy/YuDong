package com.gdou.yudong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;

import java.util.List;

/**
 * Created by admin on 2018/4/27.
 */

public class BookStoreRecyclerViewAdapter extends RecyclerView.Adapter{

    public ToDayRecyclerViewHolder toDayRecyclerViewHolder;
    private Context context;
    private List<Books> booksList;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public BookStoreRecyclerViewAdapter(Context context,List<Books> booksList){
        this.context = context;
        this.booksList = booksList;
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
        toDayRecyclerViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ToDayRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageButton ib_today_book_img;
        public TextView tv_today_book_name;

        public ToDayRecyclerViewHolder(View itemView) {
            super(itemView);
            ib_today_book_img = (ImageButton) itemView.findViewById(R.id.ib_today_book_img);
            tv_today_book_name = (TextView) itemView.findViewById(R.id.tv_today_book_name);
        }

        public void setData(final int position){
            GlideUtils.getInstence().setImageCacheResource(Common.WEB_BOOK_IMG_URL+booksList.get(position).getBookCoverPath(),context,ib_today_book_img);
            tv_today_book_name.setText(booksList.get(position).getBookName());
            ib_today_book_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnClickListener.onItemClick(booksList,v,position);
                }
            });
        }
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener){
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }


}
