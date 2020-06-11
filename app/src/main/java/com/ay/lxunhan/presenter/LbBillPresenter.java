package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CoinBean;
import com.ay.lxunhan.contract.LbBillContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/10
 */
public class LbBillPresenter extends BasePresenter<LbBillContract.LbBillView> implements LbBillContract.LbBillPresenter {
    public LbBillPresenter(LbBillContract.LbBillView view) {
        super(view);
    }

    @Override
    public void getLbBIll(int page) {
        addDisposable(HttpMethods.getInstance().lbBill(page).subscribeWith(new BaseSubscriber<List<CoinBean>>(){
            @Override
            public void onNext(List<CoinBean> o) {
                super.onNext(o);
                getView().getLbBillFinish(o);
            }
        }));
    }
}
