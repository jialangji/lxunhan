package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.ChannelBean;
import com.ay.lxunhan.contract.ChannelContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

public class ChannelPresenter extends BasePresenter<ChannelContract.ChannelView> implements ChannelContract.ChannelPresenter {

    public ChannelPresenter(ChannelContract.ChannelView view) {
        super(view);
    }

    @Override
    public void channelManagement() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getChannelManagement().subscribeWith(new BaseSubscriber<ChannelBean>(){
            @Override
            public void onNext(ChannelBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().channelManageFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }

    @Override
    public void channelVideoManagement() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getChannelVideoManagement().subscribeWith(new BaseSubscriber<ChannelBean>(){
            @Override
            public void onNext(ChannelBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().channelManageFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void addChannel(String id) {
        addDisposable(HttpMethods.getInstance().addChannel(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addChannelFinish();
            }
        }));
    }

    @Override
    public void deleteChannel(String id) {
        addDisposable(HttpMethods.getInstance().deleteChannel(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().deleteChannelFinish();
            }
        }));
    }

    @Override
    public void addVideoChannel(String id) {
        addDisposable(HttpMethods.getInstance().addVideoChannel(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addChannelFinish();
            }
        }));
    }

    @Override
    public void deleteVideoChannel(String id) {
        addDisposable(HttpMethods.getInstance().deleteVideoChannel(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().deleteChannelFinish();
            }
        }));
    }
}
