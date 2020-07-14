package com.ay.lxunhan.utils;

import static android.provider.UserDictionary.Words.APP_ID;

public class Contacts {
    //网络拦截器类型
    public static final int NETWORK_INTERCEPTOR_TYPE_LOG = 1;
    public static final int NETWORK_INTERCEPTOR_TYPE_HEADER = 2;
    public static final int NETWORK_INTERCEPTOR_TYPE_PUBLIC_PARAMS = 3;

    public static String DIR_NAME = "HLXImage";

    public static final int QQLOGIN=1;
    public static final int WXLOGIN=2;
    public static final int WBLOGIN=3;
    static final String LOGIN_TYPE="login_type";

    public static final String QQ_APP_ID="101891062";
    public static final String WX_APP_ID="wx08cbd3a5db2809a2";
    public static final String WB_APP_ID="";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static String SCOPE =  "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
    public static final int REQ_PERM_EXTERNAL_STORAGE = 11004; // 读写文件

    public static final int LIMIT=20;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

    final static String USERINFO = APP_ID + "_userinfo";//存储本地用户信息的文件名
    final static String USERID = "userId";
    final static String UID="uid";

    final static String ISLOGIN = "isLogin";
    final static String ISFIRSTOPEN = "isFirstOpen";
    final static String ISHAVEPASS="ISHAVEPASS";
    final static String AVATAR="avatar";


    final static String WYY_ACCOUNT="wyyAccount";
    final static String WYY_TOKEN="wyyToken";
    final static String WX_CODE="wxcode";
    final static String WX_ISLOGIN="wxIsLogin";

    public final static String HISTORY="history";
    public final static String HISTORY_HOME="1";
    public final static String HISTORY_VIDEO="2";
    public final static String HISTORY_LIVE="3";
    public final static String HISTORY_FRIEND="4";
    public final static String HISTORY_ADD_FRIEND="5";
}
