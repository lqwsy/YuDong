package com.gdou.yudong.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018-04-22.
 * 保存文件到本地
 */

public class SaveFileUtil {

    /**
     * file in sdcard
     * 保存图书封面
     * */
    public static File getCustomPathFile(String fileName){
        File fileDir = new File(Environment.getExternalStorageDirectory()+"/yudong/");//自定义文件存放路径
        if(!fileDir.exists()){
            fileDir.mkdir();//创建新目录
        }
        File file = new File(fileDir,fileName);//新建一个名为fileName的文件
        return file;
    }

    /**
     * file in app launch path
     * 保存图书
     * */
    public static File getAppLaunchPathFile(Context context,String fileName) throws IOException{
        File appPath = context.getFilesDir();//获取当前APP运行路径:/data/data/com.github.okhttpctice/files
        File file = new File(appPath,fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 判断是否有sdcard
     * */
    public static boolean ifExistSdCard(){
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
