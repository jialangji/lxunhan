package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.LiveListBean;
import com.ay.lxunhan.contract.LiveListContract;
import com.ay.lxunhan.presenter.LiveListPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.video.fragment
 * @date 2020/7/3
 */
public class LiveFragment extends BaseFragment<LiveListContract.LiveListView, LiveListPresenter> implements LiveListContract.LiveListView {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private String id;
    private List<LiveListBean> liveListBeans=new ArrayList<>();
    private int page=1;
    private boolean isRefresh=true;
    private BaseQuickAdapter liveAdapter;

    public static LiveFragment newInstance(String typeId) {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        args.putString("id", typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        id = getArguments().getString("id");
        presenter.getLiveList(id,page);
    }

    @Override
    protected void initView() {
        super.initView();
        liveAdapter = new BaseQuickAdapter<LiveListBean,BaseViewHolder>(R.layout.item_live,liveListBeans) {

            @Override
            protected void convert(BaseViewHolder helper, LiveListBean item) {
                GlideUtil.loadRoundImg(getActivity(),helper.getView(R.id.iv_cover),item.getCover());
                helper.setText(R.id.tv_title,item.getLname());
                helper.setText(R.id.tv_see_count,item.getPeople()+"");
            }
        };
        rvVideo.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvVideo.addItemDecoration(new GridSpacingItemDecoration(2,10,false));
        rvVideo.setAdapter(liveAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page=1;
                isRefresh=true;
                presenter.getLiveList(id,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==liveListBeans.size()){
                    page=page+1;
                    isRefresh=false;
                    presenter.getLiveList(id,page);
                }else{
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    public LiveListPresenter initPresenter() {
        return new LiveListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
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
    public void getLiveListFinish(List<LiveListBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            liveListBeans.clear();
        }else{
            swipeRefresh.finishLoadmore();
        }
        liveListBeans.addAll(list);
        liveAdapter.notifyDataSetChanged();

    }
}
