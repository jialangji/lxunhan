package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.contract.LbContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/10
 */
public class LbPresenter extends BasePresenter<LbContract.LbView> implements LbContract.LbPresenter {
    public LbPresenter(LbContract.LbView view) {
        super(view);
    }

    @Override
    public void getShow() {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().lbShow().subscribeWith(new BaseSubscriber<LbBean>(){
            @Override
            public void onNext(LbBean o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getShowFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void getLbTask() {
        addDisposable(HttpMethods.getInstance().lbTask().subscribeWith(new BaseSubscriber<List<TaskBean>>(){
            @Override
            public void onNext(List<TaskBean> o) {
                super.onNext(o);
                getView().getLbTask(o);
            }
        }));
    }
}
