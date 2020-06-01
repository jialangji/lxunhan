package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.contract.RegisterContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

public class RegisterPresenter extends BasePresenter<RegisterContract.RegisterView> implements RegisterContract.RegisterPresenter {
    public RegisterPresenter(RegisterContract.RegisterView view) {
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
    public void register(PublicModel publicModel) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().register(publicModel).subscribeWith(new BaseSubscriber<LoginBean>(){
            @Override
            public void onNext(LoginBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().registerFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
