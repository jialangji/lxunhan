package com.ay.lxunhan.ui.video.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.VideoHomeContract;
import com.ay.lxunhan.presenter.VideoHomePresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.ShareImgDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import cn.jzvd.JzvdStd;

public class VideoHomeFrgament extends BaseFragment<VideoHomeContract.VideoHomeView, VideoHomePresenter> implements VideoHomeContract.VideoHomeView {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page = 1;
    private boolean isRefresh = true;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private BaseQuickAdapter videoAdapter;
    private ShareDialog shareDialog;
    private int mPosition;
    private ShareImgDialog shareImgDialog;

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

    }

    @Override
    protected void initView() {
        super.initView();
        videoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvVideo.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
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
        if (UserInfo.getInstance().isLogin()){
            presenter.videoRecords();
        }
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
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(getActivity(),videoBeanList.get(position).getUid());
                    break;
                case R.id.ll_linear:
                    if (videoBeanList.get(position).getItemType() == 5) {
                        SmallVideoActivity.startSmallVideoActivity(getActivity(),videoBeanList.get(position).getId());
                    } else {
                        VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(videoBeanList.get(position).getId()));
                    }
                    break;
                case R.id.tv_attention:
                    if (isLogin()){
                        mPosition = position;
                        presenter.attention(videoBeanList.get(position).getUid());
                    }
                    break;
                case R.id.rl_more:
                    if (isLogin()){
                        mPosition = position;
                        shareDialog.show();
                    }
                    break;
                case R.id.ll_like:
                    if (isLogin()){
                        mPosition=position;
                        SendCommentModel sendCommentModel = new SendCommentModel(videoBeanList.get(position).getId() + "", videoBeanList.get(position).getType());
                        presenter.addLike(sendCommentModel);
                    }
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
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));
                ShareUtils.shareToQQ(getActivity(),videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareQQRoom() {
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToQQRoom(getActivity(),videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWx() {
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToWx(Objects.requireNonNull(getActivity()),videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWxPyq() {
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToWxPyq(Objects.requireNonNull(getActivity()),videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {
                showImg(videoBeanList.get(mPosition).getPiiic_url());
            }

            @Override
            public void copyUrl() {
                StringUtil.copyString(videoBeanList.get(mPosition).getShare_url());
            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(getActivity(), String.valueOf(videoBeanList.get(mPosition).getId()), 2);

            }

            @Override
            public void collect() {
                presenter.addCollect(String.valueOf(videoBeanList.get(mPosition).getId()),2);
            }

            @Override
            public void cancel() {

            }
        });
    }

    public void showImg(String url){
        if (shareImgDialog == null) {
            shareImgDialog = new ShareImgDialog(getActivity(), R.style.selectPicDialogstyle,url);
        }
        shareImgDialog.show();
        shareImgDialog.setItemClickListener(new ShareImgDialog.ItemClickListener() {
            @Override
            public void shareQQ(String bitmap) {
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));
                ShareUtils.shareToQQImg(getActivity(),bitmap);
            }

            @Override
            public void shareWx() {
                presenter.share(2, String.valueOf(videoBeanList.get(mPosition).getId()));

            }

            @Override
            public void shareWb(Bitmap bitmap) {

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
        return R.layout.fragment_video_home;
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
    public void videoRecords(List<PeopleBean> list) {
    }

    @Override
    public void addCollectFinish() {
        ToastUtil.makeShortText(getActivity(),"收藏成功");
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

    @Override
    public void addLikeFinish() {
        if (videoBeanList.get(mPosition).getIs_like()) {
            videoBeanList.get(mPosition).setIs_like(0);
            videoBeanList.get(mPosition).setLike_count(videoBeanList.get(mPosition).getLike_count()-1);
        }else {
            videoBeanList.get(mPosition).setIs_like(1);
            videoBeanList.get(mPosition).setLike_count(videoBeanList.get(mPosition).getLike_count()+1);
        }
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            JzvdStd.goOnPlayOnPause();
        } else {
            JzvdStd.goOnPlayOnResume();
        }
    }
}
