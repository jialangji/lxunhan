package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.AttentionBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/10
 */
public interface FansContract  {
    interface FansPresenter{
        void getFans(int page);
        void getMediaFans(String uzid,int page);
        void attention(String uzid);
    }
    interface FansView{
        void getFansFinish(List<AttentionBean> list);
        void attention();
        void showProgress();
        void hideProgress();
    }
}
