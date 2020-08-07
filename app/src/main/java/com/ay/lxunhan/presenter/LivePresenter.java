package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.LiveDetail;
import com.ay.lxunhan.contract.LiveContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/21
 */
public class LivePresenter extends BasePresenter<
        LiveContract.LiveView> implements LiveContract.LivePresenter {
    public LivePresenter(LiveContract.LiveView view) {
        super(view);
    }

    @Override
    public void liveSeeCount(String lid, boolean isAdd) {
        addDisposable(HttpMethods.getInstance().liveSeeCount(lid,isAdd).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().liveSeecountFinish(isAdd);
            }
        }));
    }

    @Override
    public void giftList(String type) {
        addDisposable(HttpMethods.getInstance().giftList(type).subscribeWith(new BaseSubscriber<List<GiftBean>>(){
            @Override
            public void onNext(List<GiftBean> o) {
                super.onNext(o);
                getView().giftListFinish(o);
            }
        }));
    }

    @Override
    public void liveClose(String lid) {
        addDisposable(HttpMethods.getInstance().liveClose(lid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().liveCloseFinish();
            }
        }));
    }

    @Override
    public void sendGift(String lid, String aid, String count) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().sendGift(lid, aid, count).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().sendGiftFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getLiveInfo(String lid) {
        addDisposable(HttpMethods.getInstance().liveInfo(lid).subscribeWith(new BaseSubscriber<LiveDetail>(){
            @Override
            public void onNext(LiveDetail o) {
                super.onNext(o);
                getView().getLiveInfoFinish(o);
            }
        }));
    }

    @Override
    public void openLive(String openLive) {
        addDisposable(HttpMethods.getInstance().openLive(openLive).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().openLiveFinish();
            }
        }));
    }

    @Override
    public void getGold() {
        addDisposable(HttpMethods.getInstance().lbShow().subscribeWith(new BaseSubscriber<LbBean>(){
            @Override
            public void onNext(LbBean o) {
                super.onNext(o);
                getView().getShowFinish(o);
            }
        }));
    }

    @Override
    public void attention(String uid) {
        addDisposable(HttpMethods.getInstance().attention(uid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().attentionFinish();
            }
        }));
    }
}
