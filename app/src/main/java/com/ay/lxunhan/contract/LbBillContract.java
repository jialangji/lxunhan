package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.CoinBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/10
 */
public interface LbBillContract {
    interface LbBillPresenter{
        void getLbBIll(int page);
    }
    interface LbBillView{
        void getLbBillFinish(List<CoinBean> list);
    }
}
