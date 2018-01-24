package com.gdou.yudong.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

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
                onLoginSuccess(2,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onLoginSuccess(Integer.parseInt(response.body().string()),callBack);
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
                onRegisterSuccess(2,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onRegisterSuccess(Integer.parseInt(response.body().string()),callBack);
            }
        });
    }

    /**
     * 异步更新UI线程,登录成功回调
     * @param value 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onLoginSuccess(final int value, final LoginResultCallBack callBack) {
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
     * 异步更新UI线程,注册成功回调
     * @param value 服务器返回的json数据
     * @param callBack  自定义的回调接口
     */
    private void onRegisterSuccess(final int value, final RegisterResultCallBack callBack) {
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
     * 创建接口回调，把okhttp的数据传出来用
     */
    public interface LoginResultCallBack {
        void onResponse(int result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }

    public interface RegisterResultCallBack {
        void onResponse(int result);//具体方法逻辑由自己实现这个接口时定义，可在里面更新UI
    }


}
