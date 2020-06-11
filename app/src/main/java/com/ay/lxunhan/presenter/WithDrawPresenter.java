package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.model.WithdrawModel;
import com.ay.lxunhan.contract.WithdrawContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/11
 */
public class WithDrawPresenter extends BasePresenter<WithdrawContract.WithdrawView> implements WithdrawContract.WithdrawPresenter {
    public WithDrawPresenter(WithdrawContract.WithdrawView view) {
        super(view);
    }

    @Override
    public void getUserInfo() {
        addDisposable(HttpMethods.getInstance().getUserInfo().subscribeWith(new BaseSubscriber<UserInfoBean>(){
            @Override
            public void onNext(UserInfoBean o) {
                super.onNext(o);
                getView().getUserInfoFinish(o);
            }
        }));
    }

    @Override
    public void withdraw(WithdrawModel withdrawModel) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().withdraw(withdrawModel).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().withdrawFinish();
            }
        }));
    }
}
