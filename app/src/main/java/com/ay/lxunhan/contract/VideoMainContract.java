package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.TypeBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/29
 */
public interface VideoMainContract {
    interface VideoMainPresenter{
        void getVideoType();
    }
    interface VideoMainView{
        void getVideoTypeFinish(List<TypeBean> typeBeans);
    }
}
