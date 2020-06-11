package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.BillBean;
import com.ay.lxunhan.contract.BillContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/11
 */
public class BillPresenter extends BasePresenter<BillContract.BillView> implements BillContract.BillPresenter {
    public BillPresenter(BillContract.BillView view) {
        super(view);
    }

    @Override
    public void getBillList(int type, int timeType, int page) {
        addDisposable(HttpMethods.getInstance().billList(type, timeType, page).subscribeWith(new BaseSubscriber<BillBean>(){
            @Override
            public void onNext(BillBean o) {
                super.onNext(o);
                getView().getBillListFinish(o);
            }
        }));
    }
}
