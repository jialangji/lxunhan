package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.AttentionBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/10
 */
public interface AttentionContract {
    interface AttentionPresenter{
        void getAttention(int page);
        void getMediaAttention(String uzid, int page);
        void attention(String uzid);
    }
    interface AttentionView{
        void getAttentionFinish(List<AttentionBean> list);
        void attention();
        void showProgress();
        void hideProgress();
    }
}
