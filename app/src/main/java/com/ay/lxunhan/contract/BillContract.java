package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.BillBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/11
 */
public interface BillContract  {
    interface BillPresenter{
        void getBillList(int type,int timeType,int page);
    }
    interface BillView{
        void getBillListFinish(BillBean list);
    }
}
