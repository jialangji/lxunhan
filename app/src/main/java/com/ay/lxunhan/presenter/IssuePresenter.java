package com.ay.lxunhan.presenter;



import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.contract.IssueContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

import okhttp3.MultipartBody;

public class IssuePresenter extends BasePresenter<IssueContract.IssueView> implements IssueContract.IssuePresenter {
    public IssuePresenter(IssueContract.IssueView view) {
        super(view);
    }


    @Override
    public void issue(String title, List<MultipartBody.Part> partList) {

        getView().hideProgress();
        addDisposable(HttpMethods.getInstance().issue( title, partList).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().issueFinish();

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
