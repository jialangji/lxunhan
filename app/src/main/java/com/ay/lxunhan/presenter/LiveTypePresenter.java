package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.contract.LiveTypeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/3
 */
public class LiveTypePresenter extends BasePresenter<LiveTypeContract.LiveTypeView> implements LiveTypeContract.LiveTypePresenter {
    public LiveTypePresenter(LiveTypeContract.LiveTypeView view) {
        super(view);
    }

    @Override
    public void getLiveType(int type) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().getLiveType(type).subscribeWith(new BaseSubscriber<List<LiveBean>>(){
            @Override
            public void onNext(List<LiveBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getLiveTypeFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
