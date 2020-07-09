package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.LiveListBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/7/3
 */
public interface LiveListContract {
    interface LiveListPresenter{
        void getLiveList(String type,int page);
    }

    interface LiveListView{
        void getLiveListFinish(List<LiveListBean> list);
    }
}
