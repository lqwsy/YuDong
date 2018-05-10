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
import com.gdou.yudong.bean.Books;

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
        gridViewHolder.ib_book_img.setImageResource(R.drawable.middle);
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
                Toast.makeText(context,booksList.get(position).getBookName(),Toast.LENGTH_SHORT).show();
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
