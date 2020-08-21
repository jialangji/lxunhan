package com.ay.lxunhan.ui.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.COrHContract;
import com.ay.lxunhan.presenter.CorHpRresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.my.fragment
 * @date 2020/8/14
 */
public class CollectFragment extends BaseFragment<COrHContract.CorHView, CorHpRresenter> implements COrHContract.CorHView {

    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<UserMediaListBean> collectList = new ArrayList<>();
    private BaseQuickAdapter collectAdapter;
    private int page = 1;
    private boolean isRefresh = true;
    private int status;

    private int mPosition;

    public static CollectFragment newInstance(int type) {
        Bundle args = new Bundle();
        CollectFragment fragment = new CollectFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CorHpRresenter initPresenter() {
        return new CorHpRresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.collect(status,page);
    }

    @Override
    protected void initView() {
        super.initView();
        status = getArguments().getInt("type");
        collectAdapter = PublicAdapterUtil.getCollectOrHistoryAdapter2(collectList, getActivity(),true);
        rvCollect.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCollect.setAdapter(collectAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh = true;
                page = 1;
                presenter.collect(status,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == collectList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.collect(status,page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        collectAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    switch (collectList.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(getActivity(), collectList.get(position).getType(), collectList.get(position).getId());
                            break;
                        case 2:
                            VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(collectList.get(position).getId()));
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(getActivity(), collectList.get(position).getType(), collectList.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(getActivity(), collectList.get(position).getId());
                            break;
                        case 5:
                            SmallVideoActivity.startSmallVideoActivity(getActivity(),collectList.get(position).getId());
                            break;
                    }
                    break;
                case R.id.tv_del:
                    mPosition=position;
                    presenter.deleteCollect(String.valueOf(collectList.get(position).getId()), String.valueOf(collectList.get(position).getType()));
                    break;

            }
        });


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
    public void getListFinish(List<UserMediaListBean> beans) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            collectList.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        collectList.addAll(beans);
        collectAdapter.notifyDataSetChanged();
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
    public void clearFinish() {
        collectList.remove(mPosition);
        collectAdapter.notifyDataSetChanged();
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
