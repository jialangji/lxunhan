package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.VideoTypeContract;
import com.ay.lxunhan.presenter.VideoTypePresenter;
import com.ay.lxunhan.widget.RecyclerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JzvdStd;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.video.fragment
 * @date 2020/5/29
 */
public class VideoTypeFragment extends BaseFragment<VideoTypeContract.VideoTypeView, VideoTypePresenter> implements VideoTypeContract.VideoTypeView {
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page = 1;
    private boolean isRefresh=true;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private BaseQuickAdapter videoAdapter;
    private String id;
    public static VideoTypeFragment newInstance(String id) {
        Bundle args = new Bundle();
        VideoTypeFragment fragment = new VideoTypeFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public VideoTypePresenter initPresenter() {
        return new VideoTypePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_refresh;
    }

    @Override
    protected void initView() {
        super.initView();
        assert getArguments() != null;
        id=getArguments().getString("id");
        videoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, getActivity());
        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideo.addItemDecoration(new RecyclerItemDecoration(10,1));
        rvVideo.setAdapter(videoAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getVideoType(id,page);

    }
    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        JzvdStd.goOnPlayOnResume();
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
                presenter.getVideoType(id,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                isRefresh=false;
                page=page+1;
                presenter.getVideoType(id,page);
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
    public void getVideoTypeFinish(List<VideoBean> list) {
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
