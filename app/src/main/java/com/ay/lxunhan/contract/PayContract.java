package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.WxOrderBean;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/14
 */
public interface PayContract {
    interface PayPresenter{
        void recharge(String id);
    }
    interface PayView{
        void rechargeFinish(WxOrderBean wxOrderBean);

        void showProgress();
        void hideProgress();
    }
}
