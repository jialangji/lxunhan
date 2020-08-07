package com.ay.lxunhan.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;

import com.ay.lxunhan.BuildConfig;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.wyyim.liveuser.CustomAttachParser;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.wanjian.cockroach.Cockroach;
import com.youdao.sdk.app.YouDaoApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.app.UiModeManager.MODE_NIGHT_YES;

public class AppContext extends MultiDexApplication {
    public static AppContext instance;
    public static Handler mHandler = new Handler();
    public int count = 0;
    public static boolean isOpenUpdateDialog = false;
    public static IWXAPI mWxApi;
    public static Tencent mTencent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (UserInfo.getInstance().isNight()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
        //logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        closeAndroidPDialog();

        YouDaoApplication.init(this, Contacts.YOUDAO_APP_ID);
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());
//        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
        }

        registToWX();
        mTencent = Tencent.createInstance(Contacts.QQ_APP_ID, this.getApplicationContext());
//        initWebSDK();
//        WsManager.getInstance().init();
//        cockroach();

    }


    private SDKOptions options() {
        SDKOptions options = new SDKOptions();
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String account = UserInfo.getInstance().getWyyAccount();
        String token =  UserInfo.getInstance().getWyyToken();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private void initWebSDK() {
        WbSdk.install(this,new AuthInfo(this, Contacts.WB_APP_ID, Contacts.REDIRECT_URL,
                Contacts.SCOPE));
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Contacts.WX_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Contacts.WX_APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                mWxApi.registerApp(Contacts.WX_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

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
