package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.WxOrderBean;
import com.ay.lxunhan.contract.PayContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/14
 */
public class PayPresenter extends BasePresenter<PayContract.PayView>implements PayContract.PayPresenter {
    public PayPresenter(PayContract.PayView view) {
        super(view);
    }

    @Override
    public void recharge(String id) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().recharge(id).subscribeWith(new BaseSubscriber<WxOrderBean>(){
            @Override
            public void onNext(WxOrderBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().rechargeFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));

    }
}
