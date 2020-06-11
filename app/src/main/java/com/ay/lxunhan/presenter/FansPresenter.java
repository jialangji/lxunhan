package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AttentionBean;
import com.ay.lxunhan.contract.FansContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/10
 */
public class FansPresenter extends BasePresenter<FansContract.FansView>implements FansContract.FansPresenter {
    public FansPresenter(FansContract.FansView view) {
        super(view);
    }

    @Override
    public void getFans(int page) {
        addDisposable(HttpMethods.getInstance().fansList(page).subscribeWith(new BaseSubscriber<List<AttentionBean>>(){
            @Override
            public void onNext(List<AttentionBean> o) {
                super.onNext(o);
                getView().getFansFinish(o);
            }
        }));

    }

    @Override
    public void getMediaFans(String uzid, int page) {
        addDisposable(HttpMethods.getInstance().mediaFansList(uzid,page).subscribeWith(new BaseSubscriber<List<AttentionBean>>(){
            @Override
            public void onNext(List<AttentionBean> o) {
                super.onNext(o);
                getView().getFansFinish(o);
            }
        }));
    }

    @Override
    public void attention(String uzid) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().attention(uzid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().attention();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }
}
