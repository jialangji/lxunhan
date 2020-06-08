package com.ay.lxunhan.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.HomeContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.HomePresenter;
import com.ay.lxunhan.ui.home.activity.ChannelManageActivity;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.login.LoginActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.IssueActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<TypeBean> typeBeanList = new ArrayList<>();
    private List<MultiItemBaseBean> homeList = new ArrayList<>();
    private BaseQuickAdapter typeAdapter;
    private BaseQuickAdapter homeAdapter;
    private int page = 1;
    private boolean isRefresh;
    private int typeId = 1;
    private int mPosition;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        typeAdapter = new BaseQuickAdapter<TypeBean, BaseViewHolder>(R.layout.item_home_type, typeBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, TypeBean item) {
                GlideUtil.loadImg(getActivity(), helper.getView(R.id.iv_bg), item.getBackground());
                GlideUtil.loadImg(getActivity(), helper.getView(R.id.iv_type), item.getIcon());
                helper.setText(R.id.tv_type, item.getName());
                helper.setTextColor(R.id.tv_type, getResources().getColor(typeId == item.getId() ? R.color.color_fc5a8e : R.color.color_303133));
                helper.setText(R.id.tv_intro, item.getDesc());
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(linearLayoutManager);
        rvType.setAdapter(typeAdapter);
        homeAdapter = PublicAdapterUtil.getAdapter(homeList, getActivity());
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(homeAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        GlideUtil.loadCircleImgForHead(getActivity(), ivHeader, UserInfo.getInstance().getAvatar());

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHomeType();
        presenter.getHomeList(1, "", page);
    }

    @Override
    protected void initListener() {
        super.initListener();
        typeAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (typeId == typeBeanList.get(position).getId()) {
                return;
            }
            if (typeBeanList.get(position).getId() == 5) {
                if (isLogin()){
                    ChannelManageActivity.stratChannelManageActivity(getActivity(), false);
                }
                return;
            }
            typeId = typeBeanList.get(position).getId();
            setRefresh();
            typeAdapter.notifyDataSetChanged();
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                setRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == homeList.size()) {
                    loadMore();
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
                case R.id.tv_attention:
                    if (isLogin()) {
                        mPosition = position;
                        presenter.attention(homeList.get(mPosition).getUid());
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

    public void loadMore() {
        isRefresh = false;
        page = page + 1;
        switch (typeId) {
            case 1:
                presenter.getHomeList(1, "", page);
                break;
            case 2:
                presenter.getHomeListArticle(page, "");
                break;
            case 3:
                presenter.getHomeListAsk(page);
                break;
            case 4:
                presenter.getHomeListQuiz(page);
                break;
            default:
                presenter.getHomeListArticle(page, String.valueOf(typeId));
                break;
        }
    }

    public void setRefresh() {
        isRefresh = true;
        page = 1;
        switch (typeId) {
            case 1:
                presenter.getHomeList(1, "", page);
                break;
            case 2:
                presenter.getHomeListArticle(page, "");
                break;
            case 3:
                presenter.getHomeListAsk(page);
                break;
            case 4:
                presenter.getHomeListQuiz(page);
                break;
            default:
                presenter.getHomeListArticle(page, String.valueOf(typeId));
                break;
        }
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    @OnClick({R.id.iv_header, R.id.rl_search, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                if (isLogin()) {
                    FriendDetailActivity.startUserDetailActivity(getActivity(), "");
                }
                break;
            case R.id.rl_search://搜索界面
                break;
            case R.id.iv_edit://编辑界面
                PermissionsUtils.getInstance().chekPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                IssueActivity.startIssueActivity(getActivity());
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });

                break;
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
            case EventModel.CHANGECHANNEL:
                presenter.getHomeType();
                break;
            case EventModel.ARTICLELIKE:
            case EventModel.SENDCOMMENT:
                setRefresh();
                break;
            case EventModel.LOGIN:
            case EventModel.LOGIN_OUT:
                presenter.getHomeType();
                setRefresh();
                break;


        }
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
    public void getHomeTypeFinish(List<TypeBean> typeBeans) {
        typeBeanList.clear();
        for (TypeBean typeBean : typeBeans) {
            if (typeBean.getId() != 5) {
                typeBeanList.add(typeBean);
            }
        }
        typeBeanList.add(typeBeans.get(4));
        typeAdapter.setNewData(typeBeanList);
    }

    @Override
    public void attentionFinish() {
        if (homeList.get(mPosition).getIs_fol() == 1) {
            if (typeId == 1) {
                setRefresh();
                return;
            } else {
                homeList.get(mPosition).setIs_fol(0);
            }
        } else {
            homeList.get(mPosition).setIs_fol(1);
        }
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void quziFinish() {
        setRefresh();
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
