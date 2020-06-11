package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.model.LbModel;
import com.ay.lxunhan.contract.LbExchangeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/10
 */
public class LbExchangePresenter extends BasePresenter<LbExchangeContract.LbExchangeView> implements LbExchangeContract.LbExchangePresenter {
    public LbExchangePresenter(LbExchangeContract.LbExchangeView view) {
        super(view);
    }

    @Override
    public void getShow() {
        addDisposable(HttpMethods.getInstance().lbShow().subscribeWith(new BaseSubscriber<LbBean>(){
            @Override
            public void onNext(LbBean o) {
                super.onNext(o);
                getView().getShowFinish(o);
            }
        }));

    }

    @Override
    public void exchange(LbModel lbModel) {
        addDisposable(HttpMethods.getInstance().lbExchange(lbModel).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().exchangeFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().exChangeErro();
            }
        }));
    }
}
