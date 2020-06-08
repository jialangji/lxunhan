package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.contract.HeUserInfoContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/8
 */
public class HeUserInfoPresenter extends BasePresenter<HeUserInfoContract.HeUserInfoView> implements HeUserInfoContract.HeUserInfoPresenter {

    public HeUserInfoPresenter(HeUserInfoContract.HeUserInfoView view) {
        super(view);
    }

    @Override
    public void getHeUserInfo(String uzid) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().userInfo(uzid,true).subscribeWith(new BaseSubscriber<HeUserBean>(){
            @Override
            public void onNext(HeUserBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getHeUserInfoFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getHeUserInfoData(String uzid) {
        addDisposable(HttpMethods.getInstance().userInfo(uzid,false).subscribeWith(new BaseSubscriber<HeUserBean>(){
            @Override
            public void onNext(HeUserBean o) {
                super.onNext(o);
                getView().getHeUserInfoDataFinish(o);
            }
        }));
    }
}
