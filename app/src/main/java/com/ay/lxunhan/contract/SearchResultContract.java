package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.UserMediaListBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/8/14
 */
public interface SearchResultContract {
    interface SearchResultPresenter{
        void getSearch(int page,int type,String title);
        void attention(String uid);
    }

    interface SearchResultView{
        void getSearchFinish(List<UserMediaListBean> beans);
        void attentionFinish();
        void showProgress();
        void hideProgress();
    }
}
