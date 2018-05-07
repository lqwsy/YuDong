package com.gdou.yudong.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2018-01-22.
 * 静态变量存储类
 */

public class Common {

    /**
     * 欢迎页停留时间
     * */
    public final static int SPLASH_DISPLAY_LENGHT = 2000;  //延迟3秒


    /**
     * 本地测试URL
     * */
    public final static String LOCAL_URL = "http://192.168.99.183:8080/YuDongReader/";


    /**
     * 发送验证码url
     */
    public static final String BASE_URL = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";

    /**
     * 开发者注册后系统自动生成的账号，可在官网登录后查看
     */
    public static final String ACCOUNT_SID = "6db6fd5109e440389964837643f45930";

    /**
     * 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
     */
    public static final String AUTH_TOKEN = "276399b467d44e789cc7b281ce2b3c09";

    /**
     * 响应数据类型, JSON或XML
     */
    public static final String RESP_DATA_TYPE = "json";

    /**
     * 封面保存路径:/storage/emulated/0/yudong
     * */

    public static final String BOOK_IMAGE_PATH = Environment.getExternalStorageDirectory()+"/yudong/";

    /**
     * 图书保存路径
     * */
    public static final String BOOK_PATH = "/data/data/com.gdou.yudong/files";
}
