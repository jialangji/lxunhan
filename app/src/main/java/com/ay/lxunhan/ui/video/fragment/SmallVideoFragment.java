package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.SmallVideoContract;
import com.ay.lxunhan.presenter.SmallVideoPresenter;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.video.fragment
 * @date 2020/5/29
 */
public class SmallVideoFragment extends BaseFragment<SmallVideoContract.SmallVideoView, SmallVideoPresenter> implements SmallVideoContract.SmallVideoView {


    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page = 1;
    private boolean isRefresh=true;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private BaseQuickAdapter videoAdapter;
    private int id=0;

    public static SmallVideoFragment newInstance() {
        Bundle args = new Bundle();
        SmallVideoFragment fragment = new SmallVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getSmallVideo(page,id);

    }



    @Override
    public SmallVideoPresenter initPresenter() {
        return new SmallVideoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_refresh;
    }

    @Override
    protected void initView() {
        super.initView();
        videoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvVideo.addItemDecoration(new GridSpacingItemDecoration(2,10,false));
        rvVideo.setLayoutManager(gridLayoutManager);
        rvVideo.setAdapter(videoAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh=true;
                page=1;
                presenter.getSmallVideo(page,id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == videoBeanList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getSmallVideo(page,id);

                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });

        videoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    SmallVideoActivity.startSmallVideoActivity(getActivity(),videoBeanList.get(position).getId());
                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(getActivity(),videoBeanList.get(position).getUid());
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
    public void getSmallVideoFinish(List<VideoBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            videoBeanList.clear();
        }else{
            swipeRefresh.finishLoadmore();
        }
        videoBeanList.addAll(list);
        videoAdapter.setNewData(videoBeanList);
    }
}
