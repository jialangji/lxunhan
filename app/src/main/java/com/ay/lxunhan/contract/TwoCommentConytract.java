package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/5/28
 */
public interface TwoCommentConytract {
    interface TwoCommentPresenter {
        void getTwoComment(String id,int page);
        void commentLike(SendCommentModel model,boolean isTwo);
        void sendTwoComment(SendCommentModel model);
    }

    interface TwoCommentView {
        void getTwoCommentFinish(TwoCommentBean twoCommentBean);
        void addLikeFinish(boolean isTwo);
        void sendTwoCommentFinish();
        void showProgress();
        void hideProgress();
    }
}
