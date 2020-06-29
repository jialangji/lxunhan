package com.ay.lxunhan.presenter;

import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.NotationBean;
import com.ay.lxunhan.bean.NotationSystemBean;
import com.ay.lxunhan.contract.NotificationContact;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.observer.BaseSubscriber;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.presenter
 * @date 2020/6/12
 */
public class NotificationPresenter extends BasePresenter<NotificationContact.NotificationView> implements NotificationContact.NotificationPresenter {
    public NotificationPresenter(NotificationContact.NotificationView view) {
        super(view);
    }

    @Override
    public void getNotification(int page) {
        addDisposable(HttpMethods.getInstance().notation(page).subscribeWith(new BaseSubscriber<NotationBean>(){
            @Override
            public void onNext(NotationBean o) {
                super.onNext(o);
                getView().getNotificationFinish(o);
            }
        }));
    }

    @Override
    public void getNotificationSystem(int page) {
        addDisposable(HttpMethods.getInstance().notationSystem(page).subscribeWith(new BaseSubscriber<List<NotationSystemBean>>(){
            @Override
            public void onNext(List<NotationSystemBean> o) {
                super.onNext(o);
                getView().getNotificationSystemFinish(o);
            }
        }));
    }
}
