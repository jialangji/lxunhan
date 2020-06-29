package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.NotationBean;
import com.ay.lxunhan.bean.NotationSystemBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/12
 */
public interface NotificationContact {
    interface NotificationPresenter{
        void getNotification(int page);
        void getNotificationSystem(int page);
    }

    interface NotificationView{
        void getNotificationFinish(NotationBean notationBean);
        void getNotificationSystemFinish(List<NotationSystemBean> notationSystemBean);
    }
}
