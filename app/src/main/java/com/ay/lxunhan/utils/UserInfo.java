package com.ay.lxunhan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ay.lxunhan.base.AppContext;

public class UserInfo {
    private static volatile UserInfo userInfo;
    private SharedPreferences userSp;
    private SharedPreferences.Editor editor;
    private boolean isLogin;//是否已经登录
    private boolean isFirstOpen;//是否第一次安装app
    private boolean isHavePass;
    private String userId;
    private String avatar;
    private int loginType;
    private String uid;

    private String wyyAccount;
    private String wyyToken;
    private String wxCode;
    private boolean isWxLogin;
    private String userName;


    private UserInfo(Context context) {
        userSp = context.getSharedPreferences(Contacts.USERINFO, Context.MODE_PRIVATE);
        editor = userSp.edit();
    }

    public String getWyyAccount() {
        return userSp.getString(Contacts.WYY_ACCOUNT,"");
    }

    public void setWyyAccount(String wyyAccount) {
        this.wyyAccount = wyyAccount;
        editor.putString(Contacts.WYY_ACCOUNT,wyyAccount);
        editor.commit();
    }

    public boolean isWxLogin() {
        return userSp.getBoolean(Contacts.WX_ISLOGIN,false);
    }

    public void setWxLogin(boolean wxLogin) {
        isWxLogin = wxLogin;
        editor.putBoolean(Contacts.WX_ISLOGIN,wxLogin);
        editor.commit();
    }

    public String getWxCode() {
        return userSp.getString(Contacts.WX_CODE,"");
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
        editor.putString(Contacts.WX_CODE,wxCode);
        editor.commit();
    }

    public String getWyyToken() {
        return userSp.getString(Contacts.WYY_TOKEN,"");
    }

    public void setWyyToken(String wyyToken) {
        this.wyyToken = wyyToken;
        editor.putString(Contacts.WYY_TOKEN,wyyToken);
        editor.commit();
    }

    public String getUserName() {
        return userSp.getString(Contacts.USERNAME,"");
    }

    public void setUserName(String userName) {
        this.userName = userName;
        editor.putString(Contacts.USERNAME,userName);
        editor.commit();
    }

    public static UserInfo getInstance() {
        if (userInfo == null) {
            synchronized (UserInfo.class) {
                if (userInfo == null) {
                    userInfo = new UserInfo(AppContext.context());
                }
            }
        }
        return userInfo;
    }

    public String getUid() {
        return userSp.getString(Contacts.UID,"");
    }

    public void setUid(String uid) {
        this.uid = uid;
        editor.putString(Contacts.UID,uid);
        editor.commit();
    }

    public String getAvatar() {
        return userSp.getString(Contacts.AVATAR,"");
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        editor.putString(Contacts.AVATAR,avatar);
        editor.commit();
    }

    public boolean isHavePass() {
        return userSp.getBoolean(Contacts.ISHAVEPASS,isHavePass);
    }

    public void setHavePass(boolean isHavePass) {
        this.isHavePass = isHavePass;
        editor.putBoolean(Contacts.ISHAVEPASS, isHavePass);
        editor.commit();
    }

    public boolean isFirstOpen() {
        return isFirstOpen;
    }

    public void setFirstOpen(boolean isFirstOpen) {
        this.isFirstOpen = isFirstOpen;
        editor.putBoolean(Contacts.ISFIRSTOPEN, isFirstOpen);
        editor.commit();
    }

    public int getLoginType() {
        return userSp.getInt(Contacts.LOGIN_TYPE,0);
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
        editor.putInt(Contacts.LOGIN_TYPE,0);
        editor.commit();
    }

    public boolean isLogin() {
        return userSp.getBoolean(Contacts.ISLOGIN,isLogin);
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
        editor.putBoolean(Contacts.ISLOGIN, isLogin);
        editor.commit();
    }


    public String getUserId() {
        return userSp.getString(Contacts.USERID,userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
        editor.putString(Contacts.USERID, userId);
        editor.commit();
    }




    public void clearAccess() {
        //清空用户信息但是不清空语言和单位信息
        editor.clear();
        editor.commit();
        //设置语言配置
        getAccess();
    }


    public void getAccess() {
        isFirstOpen = userSp.getBoolean(Contacts.ISFIRSTOPEN, true);
        isLogin = userSp.getBoolean(Contacts.ISLOGIN, false);
        isHavePass = userSp.getBoolean(Contacts.ISHAVEPASS, false);
        editor.commit();
    }


}
