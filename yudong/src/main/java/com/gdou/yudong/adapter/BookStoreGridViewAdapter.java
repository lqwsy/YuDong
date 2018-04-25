package com.gdou.yudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gdou.yudong.R;

import java.util.List;

/**
 * Created by Administrator on 2018-04-25.
 */

public class BookStoreGridViewAdapter extends BaseAdapter implements View.OnClickListener{

    private List<String> bookNameList;//图书名称列表
    private List<String> bookUrlList;//图书列表
    private List<String> bookImgUrlList;//图书封面列表
    private LayoutInflater layoutInflater;
    private Context context;

    public BookStoreGridViewAdapter(Context context,List<String> bookNameList,List<String> bookImgUrlList,List<String> bookUrlList){
        this.bookNameList = bookNameList;
        this.bookImgUrlList = bookImgUrlList;
        this.bookUrlList = bookUrlList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookNameList.get(position);
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
        gridViewHolder.ib_book_img.setImageResource(R.drawable.middle);
        gridViewHolder.ib_book_img.setTag(position);
        gridViewHolder.ib_book_img.setOnClickListener(this);
        gridViewHolder.tv_book_name.setText(bookNameList.get(position));
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()){
            case R.id.ib_book_img:
                Toast.makeText(context,bookUrlList.get(position),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private static final class GridViewHolder{
        public ImageButton ib_book_img;
        public TextView tv_book_name;

    }


}
