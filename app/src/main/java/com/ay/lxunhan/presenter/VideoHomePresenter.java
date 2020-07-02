package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.VideoHomeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/29
 */
public class VideoHomePresenter extends BasePresenter<VideoHomeContract.VideoHomeView> implements VideoHomeContract.VideoHomePresenter {
    public VideoHomePresenter(VideoHomeContract.VideoHomeView view) {
        super(view);
    }

    @Override
    public void getVideoHomeList(String title, int page) {
        addDisposable(HttpMethods.getInstance().videoHome(title, page).subscribeWith(new BaseSubscriber<List<VideoBean>>(){
            @Override
            public void onNext(List<VideoBean> o) {
                super.onNext(o);
                getView().getVideoHomeListFinish(o);
            }
        }));
    }

    @Override
    public void videoRecords() {
        addDisposable(HttpMethods.getInstance().videoRecord().subscribeWith(new BaseSubscriber<List<PeopleBean>>(){
            @Override
            public void onNext(List<PeopleBean> o) {
                super.onNext(o);
                getView().videoRecords(o);
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

    @Override
    public void addCollect(String aid, int type) {
        addDisposable(HttpMethods.getInstance().addCollect(aid, type).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().addCollectFinish();
            }
        }));
    }
}
