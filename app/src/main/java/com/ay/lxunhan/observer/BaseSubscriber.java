package com.ay.lxunhan.observer;


import android.util.Log;

import com.ay.lxunhan.BuildConfig;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.http.ApiException;
import com.ay.lxunhan.ui.login.LoginActivity;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.blankj.utilcode.util.NetworkUtils;

import io.reactivex.subscribers.DisposableSubscriber;

import static com.ay.lxunhan.http.HttpMethods.HTTP_LOGIN_BlACK;
import static com.ay.lxunhan.http.HttpMethods.HTTP_LOGIN_UNREGISTER;
import static com.ay.lxunhan.http.HttpMethods.HTTP_NOT_LOGIN;
import static com.ay.lxunhan.http.HttpMethods.HTTP_SUCCESS;


/**
 * author: yjl
 * e-mail: 1271901673@qq.com
 * time  : 2018/9/11
 * desc  :
 */
public class BaseSubscriber<T> extends DisposableSubscriber<T> {

    public BaseSubscriber() {
    }


    @Override
    public void onNext(T o) {
        //每次接口请求成功，判断有网络的状态如果socket没连着就重新连接，防止意外socket断开后无法接收数据的问题
        if (NetworkUtils.isConnected()) {
            if (BuildConfig.DEBUG) {
                Log.e("LOG:------","判断是否需要重连");
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ApiException) {
            ApiException apiException = (ApiException) t;
            if (apiException.getErrCode() != HTTP_SUCCESS) {
                if (null != AppContext.instance) {
                    if (apiException.getErrCode()==HTTP_NOT_LOGIN){
                        ToastUtil.makeShortText(AppContext.context(), apiException.getErrMessage());
                        UserInfo.getInstance().clearAccess();
                        LoginActivity.startLoginActivity2(AppContext.context());
                    }else if (apiException.getErrCode()==HTTP_LOGIN_BlACK){
                        ToastUtil.makeShortText(AppContext.context(), R.string.account_black);
                        UserInfo.getInstance().clearAccess();
                        LoginActivity.startLoginActivity2(AppContext.context());
                    }else if (apiException.getErrCode()==HTTP_LOGIN_UNREGISTER){
                        ToastUtil.makeShortText(AppContext.instance, apiException.getErrMessage());
                        UserInfo.getInstance().clearAccess();
                        LoginActivity.startLoginActivity2(AppContext.context());
                    }else {
                        ToastUtil.makeShortText(AppContext.instance, apiException.getErrMessage());
                    }
                }
            }
        }else{
            if (null != AppContext.instance) {
                Log.e("OKHTTP:error",t.getMessage());
                ToastUtil.makeShortText(AppContext.instance, t.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    protected void onStart() {
        if (!NetworkUtils.isConnected()) {
            if (null != AppContext.context()) {
                ToastUtil.makeShortText(AppContext.instance, "网络异常");
            }
        }
        super.onStart();

    }
}
