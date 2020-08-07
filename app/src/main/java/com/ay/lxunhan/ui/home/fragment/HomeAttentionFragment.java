package com.ay.lxunhan.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomePresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.home.fragment
 * @date 2020/8/5
 */
public class HomeAttentionFragment extends BaseFragment<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView {
    @BindView(R.id.rv_video)
    RecyclerView rvList;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<MultiItemBaseBean> homeList = new ArrayList<>();
    private BaseQuickAdapter homeAdapter;
    private int page = 1;
    private boolean isRefresh;
    private int mPosition;

    public static HomeAttentionFragment newInstance() {
        Bundle args = new Bundle();
        HomeAttentionFragment fragment = new HomeAttentionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        if (UserInfo.getInstance().isLogin()){
            presenter.getHomeList(1, "", page);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        homeAdapter = PublicAdapterUtil.getAdapter(homeList, getActivity(), true);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(homeAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (UserInfo.getInstance().isLogin()){
                    isRefresh = true;
                    page = 1;
                    presenter.getHomeList(1, "", page);
                }

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == homeList.size()) {
                    if (UserInfo.getInstance().isLogin()) {
                        isRefresh = false;
                        page = page + 1;
                        presenter.getHomeList(1, "", page);
                    }
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        homeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(getActivity(), homeList.get(position).getUid());
                    break;
                case R.id.ll_linear:
                    switch (homeList.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(getActivity(), homeList.get(position).getType(), homeList.get(position).getId());
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(getActivity(), homeList.get(position).getType(), homeList.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(getActivity(), homeList.get(position).getId());
                            break;
                    }
                    break;
                case R.id.tv_quiz://投票
                    if (isLogin()) {
                        if (!homeList.get(position).getIs_pate()) {
                            mPosition = position;
                            int oid = -1;
                            for (MultiItemBaseBean.OptionListBean optionListBean : homeList.get(position).getOption_list()) {
                                if (optionListBean.isUserIsSelect()) {
                                    oid = optionListBean.getId();
                                }
                            }
                            SendCommentModel quizModel = new SendCommentModel(homeList.get(position).getId(), oid);
                            presenter.quiz(quizModel);
                        }
                    }


                    break;
            }
        });
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_attention;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    public void getHomeListFinish(List<MultiItemBaseBean> homeListBeans) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            homeList.clear();
            homeList.addAll(homeListBeans);
            homeAdapter.setNewData(homeList);
        } else {
            swipeRefresh.finishLoadmore();
            homeList.addAll(homeListBeans);
            homeAdapter.setNewData(homeList);
        }
    }
    @Override
    public boolean isUserEvent() {
        return true;
    }

    @Override
    protected void getStickyEvent(Object eventModel) {
        super.getStickyEvent(eventModel);
        EventModel model = (EventModel) eventModel;
        switch (model.getMessageType()) {
            case EventModel.ARTICLELIKE:
            case EventModel.SENDCOMMENT:
                if (UserInfo.getInstance().isLogin()){
                    isRefresh=true;
                    presenter.getHomeList(1, "", page);
                }
                break;
        }
    }

    @Override
    public void getHomeTypeFinish(List<TypeBean> typeBeans) {

    }

    @Override
    public void attentionFinish() {

    }

    @Override
    public void quziFinish() {

    }

    @Override
    public void addLikeFinish() {

    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }
}
