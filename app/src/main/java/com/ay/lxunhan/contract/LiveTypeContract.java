package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LiveBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/3
 */
public interface LiveTypeContract {
    interface LiveTypePresenter{
        void getLiveType(int type);
    }

    interface LiveTypeView{
        void getLiveTypeFinish(List<LiveBean> list);
        void showProgress();
        void hideProgress();
    }
}
