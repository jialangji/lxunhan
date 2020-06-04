package com.ay.lxunhan.ui.video.fragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.VideoHomeContract;
import com.ay.lxunhan.presenter.VideoHomePresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ShareDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.content.Context.SENSOR_SERVICE;

public class VideoHomeFrgament extends BaseFragment<VideoHomeContract.VideoHomeView, VideoHomePresenter> implements VideoHomeContract.VideoHomeView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_people)
    RecyclerView rvPeople;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<PeopleBean> peopleBeans = new ArrayList<>();
    private BaseQuickAdapter peopleAdapter;
    private int page = 1;
    private boolean isRefresh = true;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private BaseQuickAdapter videoAdapter;
    private ShareDialog shareDialog;
    private int mPosition;

    public static VideoHomeFrgament newInstance() {
        Bundle args = new Bundle();
        VideoHomeFrgament fragment = new VideoHomeFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getVideoHomeList("", page);
        presenter.videoRecords();
    }

    @Override
    protected void initView() {
        super.initView();
        peopleAdapter = new BaseQuickAdapter<PeopleBean, BaseViewHolder>(R.layout.item_people, peopleBeans) {
            @Override
            protected void convert(BaseViewHolder helper, PeopleBean item) {
                GlideUtil.loadCircleImgForHead(getActivity(), helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPeople.setLayoutManager(linearLayoutManager);
        rvPeople.setAdapter(peopleAdapter);
        videoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvVideo.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
        rvVideo.setLayoutManager(gridLayoutManager);
        rvVideo.setAdapter(videoAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (videoBeanList.get(i).getItemType() == 5) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
        showDialog();
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
                isRefresh = true;
                page = 1;
                presenter.getVideoHomeList("", page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == videoBeanList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getVideoHomeList("", page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        videoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_line:
                    if (videoBeanList.get(position).getItemType() == 5) {
                        List<VideoBean> videoBeans = new ArrayList<>();
                        for (VideoBean videoBean : videoBeanList) {
                            if (videoBean.getItemType() == 5) {
                                videoBeans.add(videoBean);
                            }
                        }
                        int postion = -1;
                        for (int i = 0; i < videoBeans.size(); i++) {
                            if (videoBeanList.get(i).getId() == videoBeans.get(i).getId()) {
                                postion = i;
                            }
                        }
                        SmallVideoActivity.startSmallVideoActivity(getActivity(), videoBeans, postion);
                    } else {
                        VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(videoBeanList.get(position).getId()));
                    }
                    break;
                case R.id.tv_attention:
                    mPosition = position;
                    presenter.attention(videoBeanList.get(position).getUid());
                    break;
                case R.id.rl_more:
                    mPosition = position;
                    shareDialog.show();
                    break;
            }
        });
    }

    public void showDialog() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(getActivity(), R.style.selectPicDialogstyle);
        }

        shareDialog.setItemClickListener(new ShareDialog.ItemClickListener() {
            @Override
            public void shareFriends() {

            }

            @Override
            public void shareQQ() {

            }

            @Override
            public void shareQQRoom() {

            }

            @Override
            public void shareWx() {

            }

            @Override
            public void shareWxPyq() {

            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {

            }

            @Override
            public void copyUrl() {

            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(getActivity(), String.valueOf(videoBeanList.get(mPosition).getId()), 2);

            }

            @Override
            public void collect() {

            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public VideoHomePresenter initPresenter() {
        return new VideoHomePresenter(this);
    }

    @Override
    protected boolean isSetStatusBarAlpha() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frgament_video_type;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.tv_look_more)
    public void onViewClicked() {
        tvTitle.setText(getString(R.string.now_live));
    }

    @Override
    public void getVideoHomeListFinish(List<VideoBean> list) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            videoBeanList.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        videoBeanList.addAll(list);
        videoAdapter.setNewData(videoBeanList);
    }

    @Override
    public void videoRecords(List<PeopleBean> peopleBeans) {
        peopleBeans.clear();
        peopleBeans.addAll(peopleBeans);
        peopleAdapter.setNewData(peopleBeans);
    }

    @Override
    public void attentionFinish() {
        if (videoBeanList.get(mPosition).getIs_fol() == 1) {
            videoBeanList.get(mPosition).setIs_fol(0);
        } else {
            videoBeanList.get(mPosition).setIs_fol(1);
        }
        videoAdapter.notifyDataSetChanged();

    }
}
