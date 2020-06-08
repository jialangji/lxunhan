package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.HeUserListContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/8
 */
public class HeUserListPresenter extends BasePresenter<HeUserListContract.HeUserListView> implements HeUserListContract.HeUserListPresenter {
    public HeUserListPresenter(HeUserListContract.HeUserListView view) {
        super(view);
    }

    @Override
    public void getHeUserMeidaList(String uzid, int page, int type) {
        addDisposable(HttpMethods.getInstance().userMediaList(uzid, page, type).subscribeWith(new BaseSubscriber<List<UserMediaListBean>>(){
            @Override
            public void onNext(List<UserMediaListBean> o) {
                super.onNext(o);
                getView().getHeUserMeidaListFinish(o);
            }
        }));
    }

    @Override
    public void getHeUserList(String uzid, int page) {
        addDisposable(HttpMethods.getInstance().userList(uzid, page).subscribeWith(new BaseSubscriber<List<PyqBean>>(){
            @Override
            public void onNext(List<PyqBean> o) {
                super.onNext(o);
                getView().getHeUserListFinish(o);
            }
        }));
    }
}
