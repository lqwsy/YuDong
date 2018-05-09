package com.gdou.yudong.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gdou.yudong.R;
import com.gdou.yudong.utils.Common;
import com.hw.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
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
            convertView = layoutInflater.inflate(R.layout.item_listview_bookshelf,null);
            imageViewHolder.iv_book_left = (ImageView) convertView.findViewById(R.id.iv_book_left);
            imageViewHolder.iv_book_middle = (ImageView) convertView.findViewById(R.id.iv_book_middle);
            imageViewHolder.iv_book_right = (ImageView) convertView.findViewById(R.id.iv_book_right);
            convertView.setTag(imageViewHolder);
        }else{
            imageViewHolder = (ImageViewHolder) convertView.getTag();
        }

        imageViewHolder.iv_book_left.setOnClickListener(this);
        imageViewHolder.iv_book_middle.setOnClickListener(this);
        imageViewHolder.iv_book_right.setOnClickListener(this);

        imageViewHolder.relativeLayout = (RelativeLayout) imageViewHolder.iv_book_left.getParent();
        imageViewHolder.relativeLayout.setTag(position);
        imageViewHolder.iv_book_left.setTag(position);
        imageViewHolder.iv_book_middle.setTag(position);
        imageViewHolder.iv_book_right.setTag(position);

        setImageResource(Common.WEB_BOOK_IMG_URL+imageUrlList.get(position)[0],context,imageViewHolder.iv_book_left);
        setImageResource(Common.WEB_BOOK_IMG_URL+imageUrlList.get(position)[1],context,imageViewHolder.iv_book_middle);
        setImageResource(Common.WEB_BOOK_IMG_URL+imageUrlList.get(position)[2],context,imageViewHolder.iv_book_right);

        return convertView;
    }

    /**
     * 利用holder缓存
     * */
    private static class ImageViewHolder{
        RelativeLayout relativeLayout;
        ImageView iv_book_left;
        ImageView iv_book_middle;
        ImageView iv_book_right;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        switch(view.getId()){
            case R.id.iv_book_left:
                readBook(position,0);
                break;
            case R.id.iv_book_middle:
                readBook(position,1);
                break;
            case R.id.iv_book_right:
                readBook(position,2);
                break;
            default:
                break;
        }
    }

    /**
     * 点击图书封面，进入阅读
     * */
    private void readBook(int position,int secondPosition){
        String filePath = Common.WEB_BOOK_URL + bookUrlList.get(position)[secondPosition];
        if (TextUtils.isEmpty(filePath) || !(new File(filePath)).exists()) {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        } else {
            HwTxtPlayActivity.loadTxtFile(context, filePath);
        }
    }

    /**
     * 用glide加载网络图片
     * */
    private void setImageResource(String url,Context context,ImageView imageView){
        Glide.with(context)
            .load(url)//可以是网络url，本地图片，应用资源，二进制流，uri对象等
            //.asGif()//制定加载动态图片，非动态则显示error
            //.asBitmap()//指定加载静态图片，gif图只显示第一帧
            .override(90,120)//指定加载图片的大小，不管imageview的大小
            .placeholder(R.drawable.loading)//添加占位图，加载网络图片前显示，加载后替换占位图
            .error(R.drawable.error)//添加加载失败占位图
            .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用缓存功能
            .into(imageView);//要显示的imageview对象
    }

}
