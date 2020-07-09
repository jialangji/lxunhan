package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LoginBean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/5
 */
public interface MyContract {
    interface MyPresenter{
        void myinfo();
        void userIsVail(String id);
    }
    interface MyView{
        void myInfoFinish(LoginBean bean);
        void userIsVailFinish(String id);
    }
}
