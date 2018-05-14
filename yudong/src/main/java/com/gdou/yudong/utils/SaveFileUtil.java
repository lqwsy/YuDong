package com.gdou.yudong.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018-04-22.
 * 保存文件到本地
 */

public class SaveFileUtil {

    private Context context;

    public SaveFileUtil(Context context){
        this.context = context;
    }

    /**
     * file in sdcard
     * 保存图书封面
     *  path:/storage/emulated/0/Android/data/com.gdou.yudong/files/Picture/
     * */
    public File getImageFilePath(String fileName){
        File filePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
//        File filePath = new File(context.getExternalFilesDir("image"), "斗破苍穹.txt");
        return filePath;
    }

    /**
     * file in app launch path
     * 保存图书
     *  path:/storage/emulated/0/Android/data/com.gdou.yudong/files/image/
     * */
    public File getBookFilePath(String fileName) throws IOException{
        File filePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

        Log.i("yudong","download book filePath === "+filePath);

        return filePath;
    }

    /**
     * 是否已存在文件
    * */
    public boolean ifFileExit(String fileName){
        File filePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if(filePath.exists()){
            return true;
        }
        return false;
    }

}
