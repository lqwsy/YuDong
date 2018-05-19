package com.gdou.yudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gdou.yudong.R;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2018-04-22.
 */

public class SearchResultListViewAdapter extends BaseAdapter{

    private List<Books> booksList;
    private LayoutInflater layoutInflater;
    private Context context;

    public SearchResultListViewAdapter(Context context,List<Books> booksList){
        this.booksList = booksList;
        this.context = context;
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
        GlideUtils.getInstence().setImageResource(Common.WEB_BOOK_IMG_URL+booksList.get(position).getBookCoverPath(),context,imageViewHolder.ib_result_book_img);
        imageViewHolder.tv_result_book_name.setText("书名："+booksList.get(position).getBookName());
        imageViewHolder.tv_result_book_author.setText("作者："+booksList.get(position).getBookAuthor());
        imageViewHolder.tv_result_book_introduce.setText("阅读量："+booksList.get(position).getBookIntroduction());

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
