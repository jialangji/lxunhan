package com.ay.lxunhan.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.ay.lxunhan.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.wanjian.cockroach.Cockroach;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AppContext extends MultiDexApplication {
    public static AppContext instance;
    public static Handler mHandler = new Handler();
    public int count = 0;
    public static boolean isOpenUpdateDialog = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        closeAndroidPDialog();
//        //websocket初始化
//        initAppStatusListener();
//        WsManager.getInstance().init();
//        cockroach();
    }

    public static AppContext context() {
        return instance;
    }

    /**
     * 分割 Dex 支持
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    /**
//     * 初始化应用前后台状态监听
//     */
//    private void initAppStatusListener() {
//        AppUtils.registerAppStatusChangedListener("rex", new Utils.OnAppStatusChangedListener() {
//            @Override
//            public void onForeground() {
//                if (BuildConfig.DEBUG) {
//                    Logger.t("WsManager").d("应用回到前台调用重连方法");
//                }
//                WsManager.getInstance().reconnect();
//            }
//
//            @Override
//            public void onBackground() {
//                if (BuildConfig.DEBUG) {
//                    Logger.t("WsManager").d("应用回到后台");
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        isOpenUpdateDialog = false;
//    }

    /**
     * 较少app崩溃
     */
    private void cockroach(){
        // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
        Cockroach.install((thread, throwable) -> new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Log.d("Cockroach", thread + "\n" + throwable.toString());
            } catch (Throwable ignored) {

            }
        }));
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
