package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.MainContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

public class MainPresenter extends BasePresenter<MainContract.MainView> implements MainContract.MainPresenter {

    public MainPresenter(MainContract.MainView view) {
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
}
