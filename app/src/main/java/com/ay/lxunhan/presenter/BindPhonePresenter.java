package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.contract.BindPhoneContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/13
 */
public class BindPhonePresenter extends BasePresenter<BindPhoneContract.BindPhoneView> implements BindPhoneContract.BindPhonePresenter {
    public BindPhonePresenter(BindPhoneContract.BindPhoneView view) {
        super(view);
    }

    @Override
    public void getCode(String phone) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getCode(phone,3).subscribeWith(new BaseSubscriber<Object>(){
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
            }
        }));
    }

    @Override
    public void getBindPhone(String phone, String code) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().bindPhone(phone, code).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().bindPhoneFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
