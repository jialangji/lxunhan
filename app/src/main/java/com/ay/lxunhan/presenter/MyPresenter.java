package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.contract.MyContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/5
 */
public class MyPresenter extends BasePresenter<MyContract.MyView> implements MyContract.MyPresenter {
    public MyPresenter(MyContract.MyView view) {
        super(view);
    }

    @Override
    public void myinfo() {
        addDisposable(HttpMethods.getInstance().myInfo().subscribeWith(new BaseSubscriber<LoginBean>(){
            @Override
            public void onNext(LoginBean o) {
                super.onNext(o);
                getView().myInfoFinish(o);
            }
        }));

    }
}
