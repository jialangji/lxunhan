package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.model.LbModel;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/10
 */
public interface LbExchangeContract {
    interface LbExchangePresenter{
        void getShow();
        void exchange(LbModel lbModel);
    }

    interface LbExchangeView{
        void getShowFinish(LbBean lbBean);
        void exchangeFinish();
        void exChangeErro();
    }
}
