package com.gdou.yudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gdou.yudong.R;

import java.util.List;

/**
 * Created by Administrator on 2018-04-22.
 */

public class SearchResultListViewAdapter extends BaseAdapter{

    private List<String> imageUrlList;//图书封面地址列表
    private List<String> bookUrlList;//图书地址列表
    private List<String> bookNameList;//图书名称列表
    private List<String> bookAuthorList;//图书作者列表
    private List<String> bookIntroList;//图书简介列表
    private LayoutInflater layoutInflater;
    private Context context;

    public SearchResultListViewAdapter(Context context, List<String> imageUrlList, List<String> bookUrlList,
                 List<String> bookNameList,List<String> bookAuthorList,List<String> bookIntroList){
        this.imageUrlList = imageUrlList;
        this.bookUrlList = bookUrlList;
        this.bookNameList = bookNameList;
        this.bookAuthorList = bookAuthorList;
        this.bookIntroList = bookIntroList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookUrlList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_listview_search_result,null);

            imageViewHolder.ib_result_book_img = (ImageButton) convertView.findViewById(R.id.ib_result_book_img);
            imageViewHolder.tv_result_book_name = (TextView) convertView.findViewById(R.id.tv_result_book_name);
            imageViewHolder.tv_result_book_author = (TextView) convertView.findViewById(R.id.tv_result_book_author);
            imageViewHolder.tv_result_book_introduce = (TextView) convertView.findViewById(R.id.tv_result_book_introduce);

            convertView.setTag(imageViewHolder);
        }else{
            imageViewHolder = (ImageViewHolder) convertView.getTag();
        }
        imageViewHolder.ib_result_book_img.setImageResource(R.drawable.middle);
        imageViewHolder.tv_result_book_name.setText(bookNameList.get(position).toString());
        imageViewHolder.tv_result_book_author.setText(bookAuthorList.get(position).toString());
        imageViewHolder.tv_result_book_introduce.setText(bookIntroList.get(position).toString());

        return convertView;
    }

    /**
     * 利用holder缓存
     * */
    private static class ImageViewHolder{
        ImageButton ib_result_book_img;
        TextView tv_result_book_name;
        TextView tv_result_book_author;
        TextView tv_result_book_introduce;
    }

}
