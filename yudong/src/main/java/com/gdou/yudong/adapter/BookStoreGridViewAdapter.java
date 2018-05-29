package com.gdou.yudong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.ui.activity.BookDetailActivity;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2018-04-25.
 */

public class BookStoreGridViewAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Books> booksList;

    public BookStoreGridViewAdapter(Context context,List<Books> booksList){
        this.context = context;
        this.booksList = booksList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(booksList.size()>8){
            return 8;
        }
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder gridViewHolder;
        if(convertView == null){
            gridViewHolder = new GridViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gridview_bookstore,null);
            gridViewHolder.ib_book_img = (ImageButton) convertView.findViewById(R.id.ib_book_img);
            gridViewHolder.tv_book_name = (TextView) convertView.findViewById(R.id.tv_book_name);
            convertView.setTag(gridViewHolder);
        }else{
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }
        GlideUtils.getInstence().setImageCacheResource(Common.WEB_BOOK_IMG_URL+booksList.get(position).getBookCoverPath(),context,gridViewHolder.ib_book_img);
        gridViewHolder.ib_book_img.setTag(position);
        gridViewHolder.ib_book_img.setOnClickListener(this);
        gridViewHolder.tv_book_name.setText(booksList.get(position).getBookName());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()){
            case R.id.ib_book_img:
                turnToBookDetail(booksList.get(position));
                break;
            default:
                break;
        }
    }


    private static final class GridViewHolder{
        public ImageButton ib_book_img;
        public TextView tv_book_name;

    }

    /*跳转到图书详情页*/
    private void turnToBookDetail(Books book){
        Intent search_intent = new Intent();
        search_intent.setClass(context,BookDetailActivity.class);
        search_intent.putExtra("book",book);
        context.startActivity(search_intent);//跳转到图书页面
    }


}
