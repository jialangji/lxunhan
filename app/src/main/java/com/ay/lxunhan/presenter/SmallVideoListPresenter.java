package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.contract.SmallVideoListContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/2
 */
public class SmallVideoListPresenter extends BasePresenter<SmallVideoListContract.SmallVideoListView> implements SmallVideoListContract.SmallVideoListPrenster {
    public SmallVideoListPresenter(SmallVideoListContract.SmallVideoListView view) {
        super(view);
    }

    @Override
    public void getSmallWatch(int id) {
        addDisposable(HttpMethods.getInstance().smallVideoWatch(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().getSmallWatchFinish();
            }
        }));

    }
}
