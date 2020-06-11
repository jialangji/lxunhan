package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.contract.PyqContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/4
 */
public class PyqPresenter extends BasePresenter<PyqContract.PyqView> implements PyqContract.PyqPresenter {
    public PyqPresenter(PyqContract.PyqView view) {
        super(view);
    }

    @Override
    public void getPyqList(int page) {
        getView().showProgress();
        addDisposable(HttpMethods.getInstance().pyqList(page).subscribeWith(new BaseSubscriber<List<PyqBean>>(){
            @Override
            public void onNext(List<PyqBean> o) {
                super.onNext(o);
                getView().hideProgress();
                getView().getPyqFinish(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                getView().hideProgress();
            }
        }));
    }

    @Override
    public void pyqDelete(String id) {
        addDisposable(HttpMethods.getInstance().pyqDelete(id).subscribeWith(new BaseSubscriber<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().pyqDeleteFinish();
            }
        }));
    }
}
