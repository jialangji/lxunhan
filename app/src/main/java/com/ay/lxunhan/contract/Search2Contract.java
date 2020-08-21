package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.HotSearchBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/8/14
 */
public interface Search2Contract  {
    interface Search2Presenter{
        void getHot();
    }
    interface Search2View{
        void getHotFinish(List<HotSearchBean> beans);
    }
}
