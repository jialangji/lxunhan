package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.model.ComplaintModel;
import com.ay.lxunhan.contract.ComplaintContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/5/28
 */
public class ComplaintPresenter extends BasePresenter<ComplaintContract.ComplaintView> implements ComplaintContract.ComplaintPresenter {
    public ComplaintPresenter(ComplaintContract.ComplaintView view) {
        super(view);
    }

    @Override
    public void complaint(ComplaintModel model) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().complaint(model).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().hideProgress();
                getView().complaintFinish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }
}
