package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/12
 */
public interface COrHContract {
    interface COrHPresenter{
        void collect(int page);
        void history(int page);
        void attention(String uid);
        void quiz(SendCommentModel model);
        void addLike(SendCommentModel model);
    }
    interface CorHView{
        void getListFinish(List<UserMediaListBean> beans);
        void attentionFinish();
        void quziFinish();
        void addLikeFinish();
        void showProgress();
        void hideProgress();
    }

}
