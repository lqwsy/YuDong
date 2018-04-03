package com.gdou.yudong.network;

import com.gdou.yudong.utils.Common;
import com.gdou.yudong.utils.JavaMD5Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-01-23.
 */

public class SMSHttpManager {

    /**
     * 构造通用参数timestamp、sig和respDataType
     *
     * @return
     */
    public static String createCommonParam() {
        // 时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        // 签名，32位加密后的哈希字符串
        String sig = JavaMD5Util.MD5ToHex(Common.ACCOUNT_SID + Common.AUTH_TOKEN + timestamp);

        return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + Common.RESP_DATA_TYPE;
    }

    /**
     * post请求
     * @param username 手机号
     * @param verificationCode 验证码
     * @return result 返回封装好的json结果
     * @throws IOException
     */
    public static String postSMS(String username, String verificationCode) {
        String result = "";
        String smsContent = "【鱼洞阅读】您的验证码为" + verificationCode + "，请于5分钟内正确输入，如非本人操作，请忽略此短信。";
        //组装请求体
        String body = "accountSid=" + Common.ACCOUNT_SID + "&to=" + username +
                "&smsContent=" + smsContent + createCommonParam();
        try {
            URL connUrl = new URL(Common.BASE_URL);
            URLConnection conn = connUrl.openConnection();
            // 设置连接参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);
            // 设置读写流
            OutputStreamWriter out = null;
            BufferedReader br = null;

            // 提交数据
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            // 读取返回数据
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            result.trim();//去空格
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 生成验证码
    public static int smsContentGenernator() {
        //随机生成6位验证码
        int randomCode = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println("验证码： " + randomCode);
        return randomCode;
    }

}
