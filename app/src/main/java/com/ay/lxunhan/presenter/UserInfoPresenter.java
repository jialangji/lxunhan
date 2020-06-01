package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.UserInfoContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.UserInfoView> implements UserInfoContract.UserInfoPresenter {
    public UserInfoPresenter(UserInfoContract.UserInfoView view) {
        super(view);
    }

    @Override
    public void getUserInfo() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getUserInfo().subscribeWith(new BaseSubscriber<UserInfoBean>(){
            @Override
            public void onNext(UserInfoBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getUserInfoFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }

    @Override
    public void getCountry() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getAddress().subscribeWith(new BaseSubscriber<List<CountryBean>>(){
            @Override
            public void onNext(List<CountryBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getCountryFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }
}
