package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.SingBean;
import com.ay.lxunhan.bean.TaskBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/12
 */
public interface SingContract {
    interface SingPresenter{
        void singInfo();
        void userSing();
        void getLbTask();
        void lbComplete(String lid);

    }
    interface SingView{
        void singInfoFinish(SingBean singBean);
        void userInfo(String str);
        void getLbTask(List<TaskBean> list);
        void lbCompleteFinish();
    }
}
