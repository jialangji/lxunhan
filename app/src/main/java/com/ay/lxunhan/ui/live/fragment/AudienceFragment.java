package com.ay.lxunhan.ui.live.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.live.LiveRoomActivity;
import com.ay.lxunhan.wyyim.liveuser.LiveBottomBar;
import com.ay.lxunhan.wyyim.liveuser.LivePlayerController;
import com.ay.lxunhan.wyyim.liveuser.NEVideoControlLayout;
import com.ay.lxunhan.wyyim.liveuser.NEVideoView;
import com.ay.lxunhan.wyyim.liveuser.PlayerContract;
import com.ay.lxunhan.wyyim.liveuser.VideoConstant;

import butterknife.BindView;

/**
 * Created by zhukkun on 1/5/17.
 * 直播观众拉流Fragment
 */
public class AudienceFragment extends BaseFragment implements PlayerContract.PlayerUi {

    @BindView(R.id.video_view)
    NEVideoView mVideoView;
    @BindView(R.id.buffering_prompt)
    View mLoadingView;

    public static final String EXTRA_URL = "extra_url";
    public static final String IS_LIVE = "is_live";
    public static final String IS_SOFT_DECODE = "is_soft_decode";

    private String mUrl; // 拉流地址

    private LiveRoomActivity liveActivity;

    /**
     * 直播状态控制View
     */
    NEVideoControlLayout controlLayout;

    /**
     * 直播控制器
     */
    LivePlayerController mediaPlayController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LiveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAudienceParam();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audience;
    }
    @Override
    protected void initView() {
        super.initView();
        controlLayout = new NEVideoControlLayout(getActivity());

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
    protected void initListener() {
        super.initListener();
        controlLayout.setChangeVisibleListener(new NEVideoControlLayout.ChangeVisibleListener() {
            @Override
            public void onShown() {
                mVideoView.invalidate();
            }

            @Override
            public void onHidden() {

            }
        });
    }

    public void attachBottomBarToFragment(final LiveBottomBar liveBottomBar) {
        liveBottomBar.setMsgClickListener(v -> liveActivity.showInputPanel());

    }

    private void initAudienceParam() {
        Intent intent = getActivity().getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        boolean isLive = intent.getBooleanExtra(IS_LIVE, true);
        boolean isSoftDecode = intent.getBooleanExtra(IS_SOFT_DECODE, true);
        liveActivity.initPresenter().liveSeeCount(liveActivity.liveId,true);
        mediaPlayController = new LivePlayerController(getActivity(), this, mVideoView, null, mUrl, VideoConstant.VIDEO_SCALING_MODE_FILL_SCALE, isLive, !isSoftDecode);
        mediaPlayController.initVideo();
    }

    @Override
    public void onResume() {
        // 恢复播放
        if (mediaPlayController != null) {
            mediaPlayController.onActivityResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //暂停播放
        if (mediaPlayController != null) {
            mediaPlayController.onActivityPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 释放资源
        if (mediaPlayController != null) {
            mediaPlayController.onActivityDestroy();
        }
        super.onDestroy();
    }


    /**
     * 显示视频推流结束
     */
    @Override
    public boolean onCompletion() {
        //由于设计了客户端重连机制,故认为源站发送的直播结束信号不可靠.转由云信SDK聊天室的直播状态来判断直播是否结束
        //此处收到直播完成时,进行重启直播处理
        showLoading(true);
        mediaPlayController.restart();
        return true;
    }

    @Override
    public boolean onError(final String errorInfo) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> liveActivity.onChatRoomFinished(errorInfo));
        }
        return true;
    }

    @Override
    public void setFileName(String name) {
        if (name != null) {

        }
    }

    @Override
    public void showLoading(final boolean show) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (show) {
                    mLoadingView.setVisibility(View.VISIBLE);
                } else {
                    mLoadingView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showAudioAnimate(final boolean show) {
    }

    @Override
    public void onBufferingUpdate() {
    }

    @Override
    public void onSeekComplete() {
    }

    public void stopWatching() {
        mediaPlayController.stopPlayback();
        showLoading(false);
    }

}
