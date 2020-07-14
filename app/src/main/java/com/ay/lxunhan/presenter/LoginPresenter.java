package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.contract.LoginContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    public LoginPresenter(LoginContract.LoginView view) {
        super(view);
    }

    @Override
    public void getCode(String phone, int type) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getCode(phone, type).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getCodeFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
                getView().getCodeError();
            }
        }));
    }

    @Override
    public void login(PublicModel publicModel) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().login(publicModel).subscribeWith(new BaseSubscriber<LoginBean>(){
            @Override
            public void onNext(LoginBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().loginFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void threeLogin(String openid, String token, int type) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().threeLogin(openid, token, type).subscribeWith(new BaseSubscriber<LoginBean>(){
            @Override
            public void onNext(LoginBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().threeFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
