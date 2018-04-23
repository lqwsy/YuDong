package com.gdou.yudong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.gdou.yudong.R;

import java.util.List;

/**
 * Created by Administrator on 2018-04-22.
 */

public class BookShelfListViewAdapter extends BaseAdapter implements View.OnClickListener{

    private List<String[]> imageUrlList;//图书封面地址列表
    private List<String[]> bookUrlList;//图书地址列表
    private LayoutInflater layoutInflater;
    private Context context;

    public BookShelfListViewAdapter(Context context,List<String[]> imageUrlList,List<String[]> bookUrlList){
        this.imageUrlList = imageUrlList;
        this.bookUrlList = bookUrlList;
        this.context = context;
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

        imageViewHolder.iv_book_left.setTag(position);
        imageViewHolder.iv_book_middle.setTag(position);
        imageViewHolder.iv_book_right.setTag(position);

        return convertView;
    }

    /**
     * 利用holder缓存
     * */
    private static class ImageViewHolder{
        ImageView iv_book_left;
        ImageView iv_book_middle;
        ImageView iv_book_right;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        switch(view.getId()){
            case R.id.iv_book_left:
                Toast.makeText(context,""+bookUrlList.get(position)[0],Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_book_middle:
                Toast.makeText(context,""+bookUrlList.get(position)[1],Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_book_right:
                Toast.makeText(context,""+bookUrlList.get(position)[2],Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
