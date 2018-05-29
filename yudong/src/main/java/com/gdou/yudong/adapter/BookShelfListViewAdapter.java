package com.gdou.yudong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gdou.yudong.R;
import com.gdou.yudong.ui.fragment.BookShelfFragment;
import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.GlideUtils;
import com.hw.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018-04-22.
 */

public class BookShelfListViewAdapter extends BaseAdapter implements View.OnClickListener,View.OnLongClickListener{

    private List<String[]> imageUrlList;//图书封面地址列表
    private List<String[]> bookUrlList;//图书地址列表
    private LayoutInflater layoutInflater;
    private Context context;

    public  MyLongClickListener myLongClickListener;

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

        //点击
        imageViewHolder.iv_book_left.setOnClickListener(this);
        imageViewHolder.iv_book_middle.setOnClickListener(this);
        imageViewHolder.iv_book_right.setOnClickListener(this);
        //长按
        imageViewHolder.iv_book_left.setOnLongClickListener(this);
        imageViewHolder.iv_book_middle.setOnLongClickListener(this);
        imageViewHolder.iv_book_right.setOnLongClickListener(this);

//        imageViewHolder.relativeLayout = (RelativeLayout) imageViewHolder.iv_book_left.getParent();
//        imageViewHolder.relativeLayout.setTag(position);
        imageViewHolder.iv_book_left.setTag(position);
        imageViewHolder.iv_book_middle.setTag(position);
        imageViewHolder.iv_book_right.setTag(position);

        GlideUtils glideUtils = GlideUtils.getInstence();
        glideUtils.setLocalImageResource(Common.BOOK_IMAGE_PATH+imageUrlList.get(position)[0],context,imageViewHolder.iv_book_left);
        glideUtils.setLocalImageResource(Common.BOOK_IMAGE_PATH+imageUrlList.get(position)[1],context,imageViewHolder.iv_book_middle);
        glideUtils.setLocalImageResource(Common.BOOK_IMAGE_PATH+imageUrlList.get(position)[2],context,imageViewHolder.iv_book_right);

        return convertView;
    }

    @Override
    public boolean onLongClick(View view) {
        int position = (Integer) view.getTag();
        String filePath;
        String imgPath;
        switch(view.getId()){
            case R.id.iv_book_left:
                filePath = Common.FILE_PATH + bookUrlList.get(position)[0];
                imgPath = Common.BOOK_IMAGE_PATH + imageUrlList.get(position)[0];
                myLongClickListener.onItemLongClick(filePath,imgPath);
                break;
            case R.id.iv_book_middle:
                filePath = Common.FILE_PATH + bookUrlList.get(position)[1];
                imgPath = Common.BOOK_IMAGE_PATH + imageUrlList.get(position)[1];
                myLongClickListener.onItemLongClick(filePath,imgPath);
                break;
            case R.id.iv_book_right:
                filePath = Common.FILE_PATH + bookUrlList.get(position)[2];
                imgPath = Common.BOOK_IMAGE_PATH + imageUrlList.get(position)[2];
                myLongClickListener.onItemLongClick(filePath,imgPath);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 利用holder缓存
     * */
    private static class ImageViewHolder{
//        RelativeLayout relativeLayout;
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
        String filePath = Common.FILE_PATH + bookUrlList.get(position)[secondPosition];
        if (TextUtils.isEmpty(filePath) || !(new File(filePath)).exists()) {
//            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        } else {
            HwTxtPlayActivity.loadTxtFile(context, filePath);
        }
    }

    public void setMyLongClickListener(MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }

}
