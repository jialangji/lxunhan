package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.model.CompleteInfoModel;
import com.ay.lxunhan.contract.CompleteInfoContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import okhttp3.MultipartBody;

public class CompleteInfoPresenter extends BasePresenter<CompleteInfoContract.CompleteInfoView> implements CompleteInfoContract.CompleteInfoPresenter {
    public CompleteInfoPresenter(CompleteInfoContract.CompleteInfoView view) {
        super(view);
    }

    @Override
    public void completeInfo(CompleteInfoModel completeInfoModel) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().completeInfo(completeInfoModel).subscribeWith(
                new BaseSubscriber<Object>(){
                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
                        getView().hideProgress();
                        getView().completeInfoFinish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        getView().hideProgress();
                    }
                }
        ));

    }
}
