package com.ay.lxunhan.utils;

import static android.provider.UserDictionary.Words.APP_ID;

public class Contacts {
    //网络拦截器类型
    public static final int NETWORK_INTERCEPTOR_TYPE_LOG = 1;
    public static final int NETWORK_INTERCEPTOR_TYPE_HEADER = 2;
    public static final int NETWORK_INTERCEPTOR_TYPE_PUBLIC_PARAMS = 3;

    static String DIR_NAME = "HLXImage";

    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
    public static final int REQ_PERM_EXTERNAL_STORAGE = 11004; // 读写文件

    public static final int LIMIT=20;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

    final static String USERINFO = APP_ID + "_userinfo";//存储本地用户信息的文件名
    final static String USERID = "userId";

    final static String ISLOGIN = "isLogin";
    final static String ISFIRSTOPEN = "isFirstOpen";
    final static String ISHAVEPASS="ISHAVEPASS";
    final static String AVATAR="avatar";
    //微信APPID
    public static final String WXAPPID = "wxd521cfe7c72dd213";

    public static final String ID="id";
    public static final String TITLE="title";
}
