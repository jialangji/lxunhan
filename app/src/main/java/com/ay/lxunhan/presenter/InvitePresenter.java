package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.InviteBean;
import com.ay.lxunhan.contract.InviteContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/29
 */
public class InvitePresenter extends BasePresenter<InviteContract.InviteView> implements InviteContract.InvitePresenter {
    public InvitePresenter(InviteContract.InviteView view) {
        super(view);
    }

    @Override
    public void sendInviteCode(String code) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().sendInviteCode(code).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().sendInviteCodeFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getInviteCoin(int page) {
        addDisposable(HttpMethods.getInstance().inviteList(page).subscribeWith(new BaseSubscriber<InviteBean>(){
            @Override
            public void onNext(InviteBean o) {
                super.onNext(o);
                getView().getInviteCoinFinish(o);
            }
        }));

    }
}
