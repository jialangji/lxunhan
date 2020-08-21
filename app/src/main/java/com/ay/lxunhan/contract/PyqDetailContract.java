package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.PyqCommentBean;
import com.ay.lxunhan.bean.PyqDetailBean;
import com.ay.lxunhan.bean.model.SendCommentModel;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/11
 */
public interface PyqDetailContract {
    interface PyqDetailPresenter{
        void getPyqDetailShow(String id);
        void sendOneComment(SendCommentModel model);
        void sendTwoComment(SendCommentModel model);
        void addLike(SendCommentModel model);
        void getComment(String id,int page);
    }

    interface PyqDetailView{
        void getPyqDetailFinish(PyqDetailBean detailBean);
        void sendCommentFinish();
        void addLikeFinish();
        void sendTwoCommentFinish();
        void getCommentFinish(PyqCommentBean beans);
        void showProgress();
        void hideProgress();
    }
}
