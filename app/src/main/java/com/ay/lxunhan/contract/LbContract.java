package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.TaskBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/10
 */
public interface LbContract {
    interface LbPresenter{
        void getShow();
        void getLbTask();
        void lbComplete(String lid);
    }
    interface LbView{
        void getShowFinish(LbBean lbBean);
        void getLbTask(List<TaskBean> list);
        void lbCompleteFinish();
        void showProgress();
        void hideProgress();
    }
}
