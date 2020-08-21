package com.ay.lxunhan.ui.video.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.SmallVideoListContract;
import com.ay.lxunhan.observer.OnViewPagerListener;
import com.ay.lxunhan.presenter.SmallVideoListPresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.HomeCommentPopWindow;
import com.ay.lxunhan.widget.JZVideoPlayerStandardLoopVideo;
import com.ay.lxunhan.widget.PagerLayoutManager;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.ShareImgDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class SmallVideoActivity extends BaseActivity<SmallVideoListContract.SmallVideoListView, SmallVideoListPresenter> implements SmallVideoListContract.SmallVideoListView {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private JZVideoPlayerStandardLoopVideo jzvdStd;
    private BaseQuickAdapter videoAdapter;
    private int videoId;
    private int page = 1;
    private boolean isRefresh = true;
    private int mPosition;
    private ShareDialog shareDialog;
    private ShareImgDialog shareImgDialog;
    private int commentPage = 1;
    private boolean commentIsRefresh = true;
    private HomeCommentPopWindow popWindow;

    @Override
    public SmallVideoListPresenter initPresenter() {
        return new SmallVideoListPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        videoId = getIntent().getIntExtra("id", 0);
        videoAdapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_small_video, videoBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoBean item) {
                JZVideoPlayerStandardLoopVideo jzvideo = helper.getView(R.id.jzvideo);
                JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
                jzvideo.backButton.setVisibility(View.GONE);
                JzvdStd.SAVE_PROGRESS = false;
                jzvideo.backButton.setVisibility(View.GONE);//返回按钮
                jzvideo.batteryTimeLayout.setVisibility(View.GONE);//时间和电量
                jzvideo.fullscreenButton.setVisibility(View.GONE);//全屏按钮
                jzvideo.progressBar.setVisibility(View.GONE);//底部进度条
                jzvideo.currentTimeTextView.setVisibility(View.GONE);//观看进度
                jzvideo.totalTimeTextView.setVisibility(View.GONE);//视频总进度
                //设置全屏播放
                Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
                Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
                GlideUtil.loadImg(SmallVideoActivity.this, jzvideo.thumbImageView, item.getCover());
                jzvideo.setUp(item.getVideo(), "", JzvdStd.SCREEN_WINDOW_NORMAL);
                jzvideo.startVideo();
                GlideUtil.loadCircleImgForHead(SmallVideoActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_title, item.getDesc());
                if (item.getIs_fol() == 2) {
                    helper.setGone(R.id.tv_attention, false);
                } else {
                    helper.setGone(R.id.tv_attention, true);
                    helper.setText(R.id.tv_attention, item.getIs_fol() != 2 && item.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
                }
                helper.setImageResource(R.id.iv_like, item.getIs_like() ? R.drawable.ic_like_hand : R.drawable.ic_white_like);
                helper.setText(R.id.tv_like_count, item.getLike_count() + "");
                helper.setText(R.id.tv_comment_count, item.getComment_count() + "");
                helper.addOnClickListener(R.id.tv_attention);
                helper.addOnClickListener(R.id.ll_like);
                helper.addOnClickListener(R.id.ll_comment);
                helper.addOnClickListener(R.id.iv_header);
                helper.addOnClickListener(R.id.ll_share);
                helper.addOnClickListener(R.id.ll_wx_share);

            }
        };
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(videoAdapter);
        PagerLayoutManager mLayoutManager = new PagerLayoutManager(this, OrientationHelper.VERTICAL);
        rvVideo.setLayoutManager(mLayoutManager);
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                playVideo(0, view);
                if (UserInfo.getInstance().isLogin()){
                    presenter.getSmallWatch(videoBeanList.get(0).getId());
                }

            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                playVideo(position, view);
                commentPage = 1;
                commentIsRefresh = true;
                if (UserInfo.getInstance().isLogin()){
                    presenter.getSmallWatch(videoBeanList.get(position).getId());
                }


            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                releaseVideo(view);
            }
        });
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
                presenter.getSmallVideo(page, 0);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == videoBeanList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getSmallVideo(page, 0);

                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        videoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mPosition = position;
            switch (view.getId()) {
                case R.id.tv_attention:
                    presenter.attention(videoBeanList.get(mPosition).getUid());
                    break;
                case R.id.ll_like:
                    SendCommentModel sendCommentModel = new SendCommentModel(String.valueOf(videoBeanList.get(mPosition).getId()), 5);
                    presenter.addLike(sendCommentModel);
                    break;
                case R.id.ll_comment:
                    presenter.getOneComment(String.valueOf(videoBeanList.get(mPosition).getId()), 5, commentPage);
                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(SmallVideoActivity.this, videoBeanList.get(position).getUid());
                    break;
                case R.id.ll_share:
                    showDialog();
                    break;
                case R.id.ll_wx_share:
                    ShareUtils.shareToWx(this, videoBeanList.get(mPosition).getShare_url());
                    break;
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getSmallVideo(page, videoId);
    }

    public void showDialog() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(this, R.style.selectPicDialogstyle);
        }
        shareDialog.show();
        shareDialog.setItemClickListener(new ShareDialog.ItemClickListener() {
            @Override
            public void shareFriends() {

            }

            @Override
            public void shareQQ() {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));
                ShareUtils.shareToQQ(SmallVideoActivity.this, videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareQQRoom() {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToQQRoom(SmallVideoActivity.this, videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWx() {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToWx(SmallVideoActivity.this, videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWxPyq() {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToWxPyq(SmallVideoActivity.this, videoBeanList.get(mPosition).getShare_url());

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
                ComplaintActivity.startComplaintActivity(SmallVideoActivity.this, String.valueOf(videoBeanList.get(mPosition).getId()), 5);

            }

            @Override
            public void collect() {
                presenter.addCollect(String.valueOf(videoBeanList.get(mPosition).getId()), 5);
            }

            @Override
            public void cancel() {

            }
        });
    }

    public void showImg(String url) {
        if (shareImgDialog == null) {
            shareImgDialog = new ShareImgDialog(this, R.style.selectPicDialogstyle, url);
        }
        shareImgDialog.show();
        shareImgDialog.setItemClickListener(new ShareImgDialog.ItemClickListener() {
            @Override
            public void shareQQ(String bitmap) {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));

                ShareUtils.shareToQQImg(SmallVideoActivity.this, bitmap);

            }

            @Override
            public void shareWx() {
                presenter.share(5, String.valueOf(videoBeanList.get(mPosition).getId()));

            }

            @Override
            public void shareWb(Bitmap bitmap) {

            }
        });
    }

    @Override
    public boolean isKeyboardEnable() {
        return false;
    }

    /**
     * 播放视频
     */
    private void playVideo(int position, View view) {
        if (view != null) {
            jzvdStd = view.findViewById(R.id.jzvideo);
            jzvdStd.startVideo();
        }
    }

    /**
     * 停止播放
     */
    private void releaseVideo(View view) {
        if (view != null) {
            jzvdStd = view.findViewById(R.id.jzvideo);
            JzvdStd.goOnPlayOnPause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JzvdStd.goOnPlayOnResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JzvdStd.releaseAllVideos();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_small_video;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    public static void startSmallVideoActivity(Context context, int id) {
        Intent intent = new Intent(context, SmallVideoActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void getSmallWatchFinish() {

    }

    @Override
    public void getSmallVideoFinish(List<VideoBean> list) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            videoBeanList.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        videoBeanList.addAll(list);
        videoAdapter.setNewData(videoBeanList);
        if (videoBeanList.size() > 0) {
            showPopWindow();
        }

    }

    @Override
    public void getOneCommentFinsh(CommentBean commentBeans) {
        if (commentIsRefresh) {
            popWindow.showAtLocation(this.findViewById(R.id.view_title), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        popWindow.updateData(commentBeans.getComment_list(), commentPage, commentIsRefresh);
        popWindow.setCount(String.valueOf(videoBeanList.get(mPosition).getComment_count()));

    }

    @Override
    public void addCollectFinish() {
        ToastUtil.makeShortText(this, "收藏成功");
    }

    @Override
    public void sendCommentFinish() {
        videoBeanList.get(mPosition).setComment_count(videoBeanList.get(mPosition).getComment_count() + 1);
        popWindow.sendSuccess();
        commentPage = 1;
        commentIsRefresh = true;
        presenter.getOneComment(String.valueOf(videoBeanList.get(mPosition).getId()), 5, commentPage);
    }

    public void showPopWindow() {
        popWindow = new HomeCommentPopWindow(this, page);
        popWindow.setOnSendCommentListener(new HomeCommentPopWindow.onSendCommentListener() {
            @Override
            public void onSend(String comment) {
                SendCommentModel sendCommentModel = new SendCommentModel(UserInfo.getInstance().getUserId(), String.valueOf(videoBeanList.get(mPosition).getId()), videoBeanList.get(mPosition).getUid(), 5, comment);
                presenter.sendOneComment(sendCommentModel);
            }

            @Override
            public void loadMore() {
                commentPage = commentPage + 1;
                commentIsRefresh = false;
                presenter.getOneComment(String.valueOf(videoBeanList.get(mPosition).getId()), 5, commentPage);

            }

            @Override
            public void commentLike(String commentID) {
                SendCommentModel sendCommentModel = new SendCommentModel(commentID);
                presenter.commentLike(sendCommentModel);
            }
        });


    }

    @Override
    public void addLikeFinish() {
        if (videoBeanList.get(mPosition).getIs_like()) {
            videoBeanList.get(mPosition).setIs_like(0);
            videoBeanList.get(mPosition).setLike_count(videoBeanList.get(mPosition).getLike_count() - 1);
        } else {
            videoBeanList.get(mPosition).setIs_like(1);
            videoBeanList.get(mPosition).setLike_count(videoBeanList.get(mPosition).getLike_count() + 1);
        }
        videoAdapter.notifyItemChanged(mPosition, videoBeanList.get(mPosition));
    }

    @Override
    public void commentLikeFinish() {
        popWindow.updateLike();
    }

    @Override
    public void attentionFinish() {
        if (videoBeanList.get(mPosition).getIs_fol() == 1) {
            videoBeanList.get(mPosition).setIs_fol(0);
        } else {
            videoBeanList.get(mPosition).setIs_fol(1);
        }
        videoAdapter.notifyItemChanged(mPosition, videoBeanList.get(mPosition));
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
