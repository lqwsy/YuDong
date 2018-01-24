package com.gdou.yudong.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018-01-24.
 * 添加关闭指定的activity
 * 用A.class 来充当指定ID
 */

public class ActivityCollector {

    private static Map<Class<?>,Activity> activityMap = new HashMap<>();//保存key，value值

    /**
     * 限制外部创建实例,用静态内部类创建单一实例
     */
    private ActivityCollector() {

    }
    private static class LazyLoader {
        private static final ActivityCollector ACTIVITY_UTILS = new ActivityCollector();
    }
    public static final ActivityCollector getInstance() {
        return ActivityCollector.LazyLoader.ACTIVITY_UTILS;
    }

    /**
     * 保存指定key值的activity（activity启动时调用）
     *
     * @param key
     * @param activity
     */
    public static void addActivity(Class<?> key, Activity activity) {
        if (activityMap.get(key) == null) {
            activityMap.put(key, activity);
        }
    }

    /**
     * 移除指定key值的activity （activity关闭时调用）
     *isDestroyed 方法要API 17以上才能使用
     * @param key
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void delActivity(Class<?> key) {
        Activity activity = activityMap.get(key);
        if (activity != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){//如果当前系统API大于17
                if (activity.isDestroyed() || activity.isFinishing()) {
                    activityMap.remove(key);
                    return;
                }
            }else{//API版本低于17则不调用isDestroyed()方法
                if (activity.isFinishing()) {
                    activityMap.remove(key);
                    return;
                }
            }
            activity.finish();
            activityMap.remove(key);
        }
    }


    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public static <T extends Activity> T getActivity(Class<T> clazz) {
        return (T) activityMap.get(clazz);
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activityMap.containsValue(activity)) {
            activityMap.remove(activity.getClass());
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activityMap != null && activityMap.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = activityMap.entrySet();
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
        }
        activityMap.clear();
    }

}
