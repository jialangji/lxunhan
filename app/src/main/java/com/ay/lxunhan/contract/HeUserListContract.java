package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.UserMediaListBean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.contract
 * @date 2020/6/8
 */
public interface HeUserListContract {
    interface HeUserListPresenter{
        void getHeUserMeidaList(String uzid,int page,int type);
        void getHeUserList(String uzid,int page);
    }

    interface HeUserListView{
        void getHeUserMeidaListFinish(List<UserMediaListBean> listBeans);
        void getHeUserListFinish(List<PyqBean> list);
    }
}
