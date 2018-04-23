package com.gdou.yudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gdou.yudong.R;

import java.util.List;

/**
 * Created by Administrator on 2018-04-22.
 */

public class BookShelfListViewAdapter extends BaseAdapter implements View.OnClickListener{

    private List<String[]> imageUrlList;
    private List<String[]> bookUrlList;
    private LayoutInflater layoutInflater;
    private int bookPosition;

    public BookShelfListViewAdapter(){

    }

    public BookShelfListViewAdapter(Context context,List<String[]> imageUrlList,List<String[]> bookUrlList){
        this.imageUrlList = imageUrlList;
        this.bookUrlList  = bookUrlList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageViewHolder imageViewHolder;
        if(convertView == null){//第一次加载
            imageViewHolder = new ImageViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_bookshelf,null);
            imageViewHolder.iv_book_left = (ImageView) convertView.findViewById(R.id.iv_book_left);
            imageViewHolder.iv_book_middle = (ImageView) convertView.findViewById(R.id.iv_book_middle);
            imageViewHolder.iv_book_right = (ImageView) convertView.findViewById(R.id.iv_book_right);
            convertView.setTag(imageViewHolder);
        }else{
            imageViewHolder = (ImageViewHolder) convertView.getTag();
        }
        imageViewHolder.iv_book_left.setImageResource(R.drawable.left);
        imageViewHolder.iv_book_middle.setImageResource(R.drawable.middle);
        imageViewHolder.iv_book_right.setImageResource(R.drawable.right);

        imageViewHolder.iv_book_left.setOnClickListener(this);
        imageViewHolder.iv_book_middle.setOnClickListener(this);
        imageViewHolder.iv_book_right.setOnClickListener(this);
        bookPosition = position;

        return convertView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_book_left:
                break;
            case R.id.iv_book_middle:
                break;
            case R.id.iv_book_right:
                break;
            default:
                break;
        }
    }

    /**
     * 利用holder缓存
     * */
    private static class ImageViewHolder{
        ImageView iv_book_left;
        ImageView iv_book_middle;
        ImageView iv_book_right;
    }

}
