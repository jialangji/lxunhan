package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.SingBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.contract.SingContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/12
 */
public class SingPresenter extends BasePresenter<SingContract.SingView> implements SingContract.SingPresenter {
    public SingPresenter(SingContract.SingView view) {
        super(view);
    }

    @Override
    public void singInfo() {
        addDisposable(HttpMethods.getInstance().singInfo().subscribeWith(new BaseSubscriber<SingBean>(){
            @Override
            public void onNext(SingBean o) {
                super.onNext(o);
                getView().singInfoFinish(o);
            }
        }));
    }

    @Override
    public void userSing() {
        addDisposable(HttpMethods.getInstance().userSing().subscribeWith(new BaseSubscriber<String>(){
            @Override
            public void onNext(String o) {
                super.onNext(o);
                getView().userInfo(o);
            }
        }));
    }

    @Override
    public void getLbTask() {
        addDisposable(HttpMethods.getInstance().lbDayTask().subscribeWith(new BaseSubscriber<List<TaskBean>>(){
            @Override
            public void onNext(List<TaskBean> o) {
                super.onNext(o);
                getView().getLbTask(o);
            }
        }));
    }

    @Override
    public void lbComplete(String lid) {
        addDisposable(HttpMethods.getInstance().lbTaskComplete(lid).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().lbCompleteFinish();
            }
        }));
    }
}
