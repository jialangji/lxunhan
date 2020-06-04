package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.PyqBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/4
 */
public interface PyqContract {
    interface PyqPresenter{
        void getPyqList(int page);
    }

    interface PyqView{
        void getPyqFinish(List<PyqBean> list);
        void showProgress();
        void hideProgress();
    }
}
