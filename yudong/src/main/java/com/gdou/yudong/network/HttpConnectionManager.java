package com.gdou.yudong.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gdou.yudong.bean.Books;
import com.gdou.yudong.bean.Users;
import com.gdou.yudong.utils.SaveFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-01-22.
 * 网络连接类
 */

public class HttpConnectionManager {

    private OkHttpClient mOkHttpClient;//全局唯一一个网络连接实例
    private Handler mHandler;//用子线程连接网络

    /**
     * 限制外部创建实例
     */
    private HttpConnectionManager() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 静态内部类实现懒汉单例模式，提供唯一一个的okhttpmanager实例
     */
    private static class LazyLoader {
        private static final HttpConnectionManager OK_HTTP_MANAGER = new HttpConnectionManager();
    }

    /**
     * 保持系统只有一个okhttpmanager对象，也就是一个okhttpclient对象
     * @return OkHttpManager唯一对象
     */
    public static final HttpConnectionManager getInstance() {
        return LazyLoader.OK_HTTP_MANAGER;
    }

    /**
     * 登录异步post请求访问服务器
     * @param url      请求链接
     * @param username 登录名
     * @param password 登录密码
     * @return result 登录结果返回值
     */
    public void loginConnect(final String url,final String username,final String password, final LoginResultCallBack callBack) {
        FormBody mFormBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onLoginCallInHandler(null,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Users user = JSON.parseObject(response.body().string(),Users.class);
                onLoginCallInHandler(user,callBack);
            }
        });
    }

    /**
     * 注册异步post请求访问服务器
     * @param url      请求链接
     * @param username 注册手机号
     * @param password 注册密码
     * @return result 注册结果返回值
     */
    public void registerConnect(final String url,final String username,final String password, final RegisterResultCallBack callBack) {
        FormBody mFormBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onRegisterCallInHandler(2,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onRegisterCallInHandler(Integer.parseInt(response.body().string()),callBack);
            }
        });
    }

    /**
     * 异步下载图书
     * @param bookUrl 图书下载地址
     * @param context context
     * @param  fileName 文件名称
     * */
    public boolean downloadBook(final int type,String bookUrl,final Context context, final String fileName,final DownloadBookResultCallBack callBack){
        boolean result = false;
        Log.i("yudong",""+bookUrl);
        Request request = new Request.Builder()
                .url(bookUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","下载失败");
                onDownloadCallInHandler(false,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream mInputStream = response.body().byteStream();
                FileOutputStream mFileOutputStream = null;
                File file = new File("");
                if(type == 1){
                    file = new SaveFileUtil(context).getBookFilePath(fileName);
                }else if (type == 2){
                    file = new SaveFileUtil(context).getImageFilePath(fileName);
                }
                mFileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len=mInputStream.read(buffer))!=-1){
                    mFileOutputStream.write(buffer,0,len);
                }
                mFileOutputStream.flush();
                Log.i("yudong","下载成功");
                onDownloadCallInHandler(true,callBack);
            }
        });
        return result;
    }

    /**
     * 异步下载图书
     * @param downloadUrl 图书下载地址
     * @param context context
     * @param  bookId 文件名称
     * */
    public boolean downloadBookController(final String downloadUrl,final Context context,final int bookId,final DownloadBookControllerCallBack callBack){
        boolean result = false;
        FormBody mFormBody = new FormBody.Builder()
                .add("bookId",""+bookId)
                .build();

        Request request = new Request.Builder()
                .url(downloadUrl)
                .post(mFormBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDownloadBookCallBackHandler(false,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response!=null&&response.isSuccessful()){
                    onDownloadBookCallBackHandler(true,callBack);
                }
            }
        });
        return result;
    }

    /**
     * 异步GET请求，返回今日排行图书的列表
     * @param url 访问服务器的url
     * @param callBack 自定义的回调接口，由自己实现具体逻辑
     * */
    public void getTodayRankBooks(String url,final GetTodayBookRankCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","今日getToday Faild");
                onGetTodayBookRankCallInHandler(2,null,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("yudong","今日getToday Success");
                if(response!=null&&response.isSuccessful()){
                    List<Books> booksList = JSON.parseArray(response.body().string(),Books.class);
                    onGetTodayBookRankCallInHandler(1,booksList,callBack);//把数据传出去
                }
            }
        });
    }

    /**
     * 异步GET请求，返回分类图书的列表
     * @param url 访问服务器的url
     * @param callBack 自定义的回调接口，由自己实现具体逻辑
     * */
    public void getClassificationBooks(String classificationName,String url,final GetClassificationBookCallBack callBack){
        FormBody mFormBody = new FormBody.Builder()
                .add("classificationName", classificationName)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","获取分类图书 Faild");
                onGetClassificationBookCallInHandler(2,null,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("yudong","获取分类图书 Success");
                if(response!=null&&response.isSuccessful()){
                    List<Books> booksList = JSON.parseArray(response.body().string(),Books.class);
                    onGetClassificationBookCallInHandler(1,booksList,callBack);//把数据传出去
                }
            }
        });
    }

    /**
     * 异步GET请求，返回搜索图书的列表
     * @param url 访问服务器的url
     * @param callBack 自定义的回调接口，由自己实现具体逻辑
     * */
    public void getSearchBooks(String searchBookName,String url,final GetSearchBookCallBack callBack){
        FormBody mFormBody = new FormBody.Builder()
                .add("searchBookName", searchBookName)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","获取搜索图书 Faild");
                onGetSearchBookCallInHandler(2,null,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("yudong","获取搜索图书 Success");
                if(response!=null&&response.isSuccessful()){
                    List<Books> booksList = JSON.parseArray(response.body().string(),Books.class);
                    onGetSearchBookCallInHandler(1,booksList,callBack);//把数据传出去
                }
            }
        });
    }

    /**
     * 异步GET请求，返回搜索图书的列表
     * @param url 访问服务器的url
     * @param callBack 自定义的回调接口，由自己实现具体逻辑
     * */
    public void updatePassword(final int userId,final String newPassword,final String password,String url,final UpdatePasswordCallBack callBack){
        FormBody mFormBody = new FormBody.Builder()
                .add("userId", ""+userId)
                .add("password", password)
                .add("newPassword", newPassword)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","修改密码 Faild");
                onUpdatePasswordCallInHandler(2,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("yudong","修改密码 Success");
                if(response!=null&&response.isSuccessful()){
                    String result = response.body().string();
                    onUpdatePasswordCallInHandler(Integer.parseInt(result),callBack);
                }
            }
        });
    }

    /**
     * 异步GET请求，返回搜索图书的列表
     * @param url 访问服务器的url
     * @param callBack 自定义的回调接口，由自己实现具体逻辑
     * */
    public void updateUserInfo(int userId,String nickName,String url,final UpdateUserInfoCallBack callBack){
        FormBody mFormBody = new FormBody.Builder()
                .add("userId", ""+userId)
                .add("nickName", nickName)
                .build();
        final Request request = new Request.Builder().url(url).post(mFormBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("yudong","修改用户信息 Faild");
                onUpdateUserInfoCallInHandler(2,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response!=null&&response.isSuccessful()){
                    Log.i("yudong","修改用户信息 Success"+response.body().string());
                    String result = response.body().string();
                    onUpdateUserInfoCallInHandler(Integer.parseInt(result),callBack);
                }
            }
        });
    }


    /**
     * 异步更新UI线程,登录成功回调
     * @param users 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onLoginCallInHandler(final Users users, final LoginResultCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(users);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,注册成功回调
     * @param value 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onRegisterCallInHandler(final int value, final RegisterResultCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(value);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,下载图书成功回调
     * @param value 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onDownloadCallInHandler(final Boolean value, final DownloadBookResultCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(value);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,下载图书成功回调
     * @param value 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onDownloadBookCallBackHandler(final Boolean value, final DownloadBookControllerCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(value);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,获取今日图书排行成功回调
     * @param booksList 服务器返回的图书列表
     * @param callBack  自定义的回调接口
     */
    private void onGetTodayBookRankCallInHandler(final int result,final List<Books> booksList, final GetTodayBookRankCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result,booksList);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,获取分类图书成功回调
     * @param booksList 服务器返回的图书列表
     * @param callBack  自定义的回调接口
     */
    private void onGetClassificationBookCallInHandler(final int result,final List<Books> booksList, final GetClassificationBookCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result,booksList);//在这里把数据传到前台activity
                }
            }
        });
    }
    /**
     * 异步更新UI线程,获取搜索图书成功回调
     * @param booksList 服务器返回的图书列表
     * @param callBack  自定义的回调接口
     */
    private void onGetSearchBookCallInHandler(final int result,final List<Books> booksList, final GetSearchBookCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result,booksList);//在这里把数据传到前台activity
                }
            }
        });
    }

    /**
     * 异步更新UI线程,获取搜索图书成功回调
     * @param callBack  自定义的回调接口
     */
    private void onUpdatePasswordCallInHandler(final int result,final UpdatePasswordCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);//在这里把数据传到前台activity
                }
            }
        });
    }

    /**
     * 异步更新UI线程,获取搜索图书成功回调
     * @param callBack  自定义的回调接口
     */
    private void onUpdateUserInfoCallInHandler(final int result,final UpdateUserInfoCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);//在这里把数据传到前台activity
                }
            }
        });
    }




    /**
     * 创建登陆接口回调，把okhttp的数据传出来用
     */
    public interface LoginResultCallBack {
        void onResponse(Users users);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建注册接口回调，把okhttp的数据传出来用
     */
    public interface RegisterResultCallBack {
        void onResponse(int result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建下载图书接口回调，把okhttp的数据传出来用
     */
    public interface DownloadBookResultCallBack {
        void onResponse(Boolean result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建下载图书接口回调，通过ID下载图书
     */
    public interface DownloadBookControllerCallBack {
        void onResponse(Boolean result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建获取今日排行图书接口回调，把okhttp的数据传出来用
     */
    public interface GetTodayBookRankCallBack {
        void onResponse(int result,List<Books> booksList);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建获取分类图书接口回调，把okhttp的数据传出来用
     */
    public interface GetClassificationBookCallBack {
        void onResponse(int result,List<Books> booksList);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }
    /**
     * 创建获取搜索图书接口回调，把okhttp的数据传出来用
     */
    public interface GetSearchBookCallBack {
        void onResponse(int result,List<Books> booksList);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }

    /**
     * 创建获取搜索图书接口回调，把okhttp的数据传出来用
     */
    public interface UpdatePasswordCallBack {
        void onResponse(int result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }

    /**
     * 创建获取搜索图书接口回调，把okhttp的数据传出来用
     */
    public interface UpdateUserInfoCallBack {
        void onResponse(int result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }

}
