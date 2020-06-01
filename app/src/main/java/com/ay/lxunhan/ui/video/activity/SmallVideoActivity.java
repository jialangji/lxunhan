package com.ay.lxunhan.ui.video.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.observer.OnViewPagerListener;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.JZVideoPlayerStandardLoopVideo;
import com.ay.lxunhan.widget.PagerLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class SmallVideoActivity extends BaseActivity {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    private int postion = 0;
    private List<VideoBean> videoBeanList = new ArrayList<>();

    private JZVideoPlayerStandardLoopVideo jzvdStd;
    private BaseQuickAdapter videoAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        postion = getIntent().getIntExtra("postion", -1);
        videoBeanList = (List<VideoBean>) getIntent().getSerializableExtra("list");
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
                GlideUtil.loadImg(SmallVideoActivity.this, jzvideo.thumbImageView, item.getCover());
                jzvideo.setUp(item.getVideo(), "", JzvdStd.SCREEN_WINDOW_NORMAL);
                jzvideo.startVideo();
                GlideUtil.loadCircleImgForHead(SmallVideoActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
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
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                playVideo(position, view);

            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                releaseVideo(view);
            }
        });
        rvVideo.scrollToPosition(postion);
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
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

    public static void startSmallVideoActivity(Context context, List<VideoBean> videoBeans, int postion) {
        Intent intent = new Intent(context, SmallVideoActivity.class);
        intent.putExtra("list", (Serializable) videoBeans);
        intent.putExtra("postion", postion);
        context.startActivity(intent);
    }

}
