package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CreateBean;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.bean.LiveDetailBean;
import com.ay.lxunhan.contract.CreateLiveContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/15
 */
public class CreateLivePresenter extends BasePresenter<CreateLiveContract.CreateLiveView> implements CreateLiveContract.CreateLivePresenter {
    public CreateLivePresenter(CreateLiveContract.CreateLiveView view) {
        super(view);
    }

    @Override
    public void getLiveType() {
        addDisposable(HttpMethods.getInstance().getLiveType(1).subscribeWith(new BaseSubscriber<List<LiveBean>>(){
            @Override
            public void onNext(List<LiveBean> o) {
                super.onNext(o);
                getView().getLiveTypeFinish(o);
            }
        }));
    }

    @Override
    public void getLiveDetail() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().liveDetail().subscribeWith(new BaseSubscriber<LiveDetailBean>(){
            @Override
            public void onNext(LiveDetailBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getLiveDetailFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void createLive(String title, String type, MultipartBody.Part part) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().createLive(title, type, part).subscribeWith(new BaseSubscriber<CreateBean>(){
            @Override
            public void onNext(CreateBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().createLiveFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
