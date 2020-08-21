package com.ay.lxunhan.ui.public_ac.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.SearchResultContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.SearchResultPresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
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
 * @package com.ay.lxunhan.ui.public_ac.fragment
 * @date 2020/8/14
 */
public class SearchResultFragment extends BaseFragment<SearchResultContract.SearchResultView, SearchResultPresenter> implements SearchResultContract.SearchResultView {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    @BindView(R.id.ll_nosearch)
    LinearLayout llNosearch;
    private String mContent;
    private int type;
    private int page = 1;
    private List<UserMediaListBean> listBeans = new ArrayList<>();
    private BaseQuickAdapter searchAdapter;
    private boolean isRefrensh = true;
    private int mPosition;

    public static SearchResultFragment newInstance(int type, String content) {
        Bundle args = new Bundle();
        SearchResultFragment fragment = new SearchResultFragment();
        args.putInt("id", type);
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        mContent = getArguments().getString("content");
        type = getArguments().getInt("id");

        searchAdapter = PublicAdapterUtil.getSearchAdapter(listBeans, getActivity(), false);
        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideo.setAdapter(searchAdapter);
    }

    @Override
    public SearchResultPresenter initPresenter() {
        return new SearchResultPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getSearch(page, type, mContent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                isRefrensh = true;
                presenter.getSearch(page, type, mContent);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (listBeans.size() == Contacts.LIMIT) {
                    page = page + 1;
                    isRefrensh = false;
                    presenter.getSearch(page, type, mContent);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });

        searchAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    switch (listBeans.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(getActivity(), listBeans.get(position).getType(), listBeans.get(position).getId());
                            break;
                        case 2:
                            VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(listBeans.get(position).getId()));
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(getActivity(), listBeans.get(position).getType(), listBeans.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(getActivity(), listBeans.get(position).getId());
                            break;
                        case 5:
                            SmallVideoActivity.startSmallVideoActivity(getActivity(), listBeans.get(position).getId());

                            break;
                    }
                    break;
                case R.id.tv_attention:
                    if (listBeans.get(position).getIs_fol() == 1) {
                        FriendDetailActivity.startUserDetailActivity(getActivity(), String.valueOf(listBeans.get(position).getId()));
                    } else {
                        mPosition = position;
                        presenter.attention(String.valueOf(listBeans.get(position).getId()));
                    }
                    break;
            }
        });
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
            case EventModel.SEARCH:
                page = 1;
                isRefrensh = true;
                mContent = (String) model.getData();
                presenter.getSearch(page, type, mContent);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected int getBarColor() {
        return R.color.bg_color;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @Override
    public void getSearchFinish(List<UserMediaListBean> beans) {
        if (isRefrensh) {
            swipeRefresh.finishRefreshing();
            listBeans.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        if (beans.size()>0){
            llNosearch.setVisibility(View.GONE);
        }else {
            llNosearch.setVisibility(View.VISIBLE);
        }
        for (UserMediaListBean bean : beans) {
            bean.setSearchContent(mContent);
        }
        listBeans.addAll(beans);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void attentionFinish() {

        listBeans.get(mPosition).setIs_fol(1);

        searchAdapter.notifyDataSetChanged();
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
