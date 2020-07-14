package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.RechargeBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/14
 */
public interface RechargeContract {
    interface RechargePresenter{
        void getGold();
        void getRechargeList();
    }
    interface RechargeView{
        void getShowFinish(LbBean lbBean);
        void getRechargeListFinish(List<RechargeBean> list);
    }
}
