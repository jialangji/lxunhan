package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.model.ComplaintModel;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/28
 */
public interface ComplaintContract {
    interface ComplaintPresenter{
        void complaint(ComplaintModel model);
    }
    interface ComplaintView{
        void complaintFinish();
        void showProgress();
        void hideProgress();
    }
}
