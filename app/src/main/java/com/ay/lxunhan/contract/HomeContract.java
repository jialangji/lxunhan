package com.ay.lxunhan.contract;

import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.TypeBean;

import java.util.List;

public interface HomeContract {
    interface HomePresenter{
        void getHomeList(int type,String title,int page);
        void getHomeListAsk(int page);
        void getHomeListQuiz(int page);
        void getHomeListArticle(int page,String id);
        void getHomeType();
    }
    interface HomeView{
        void getHomeListFinish(List<MultiItemBaseBean> homeListBeans);
        void getHomeTypeFinish(List<TypeBean> typeBeans);
        void showProgress();
        void hideProgress();
    }
}
