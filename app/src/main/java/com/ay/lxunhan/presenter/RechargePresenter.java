package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.RechargeBean;
import com.ay.lxunhan.contract.RechargeContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/7/14
 */
public class RechargePresenter extends BasePresenter<RechargeContract.RechargeView> implements RechargeContract.RechargePresenter {
    public RechargePresenter(RechargeContract.RechargeView view) {
        super(view);
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
    public void getRechargeList() {
        addDisposable(HttpMethods.getInstance().rechargeList().subscribeWith(new BaseSubscriber<List<RechargeBean>>(){
            @Override
            public void onNext(List<RechargeBean> o) {
                super.onNext(o);
                getView().getRechargeListFinish(o);
            }
        }));
    }
}
