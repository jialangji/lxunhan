package com.ay.lxunhan.ui.live.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.live.LiveRoomActivity;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.wyyim.chatroom.DialogMaker;
import com.ay.lxunhan.wyyim.liveuser.CapturePreviewContract;
import com.ay.lxunhan.wyyim.liveuser.CapturePreviewController;
import com.ay.lxunhan.wyyim.liveuser.LiveBottomBar;
import com.ay.lxunhan.wyyim.liveuser.NetworkUtils;
import com.ay.lxunhan.wyyim.liveuser.PublishParam;
import com.netease.vcloud.video.render.NeteaseView;

import java.util.Objects;

import butterknife.BindView;

/**
 * Created by zhukkun on 1/5/17.
 * 直播采集推流Fragment
 */
public class CaptureFragment extends BaseFragment implements CapturePreviewContract.CapturePreviewUi {
    public static final String TAG = "PreviewController";
    @BindView(R.id.live_filter_view)
    NeteaseView filterSurfaceView;
    boolean canUse4GNetwork = false; //是否能使用4G网络进行直播
    /**
     * 控制器
     */
    CapturePreviewController controller;

    LiveRoomActivity liveActivity;

    LiveBottomBar liveBottomBar;

    private long lastClickTime;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_capture;
    }


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
    public void onStart() {
        super.onStart();
        controller.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onPause();
    }

    @Override
    protected void initView() {
        super.initView();
        controller = getCaptureController();
        controller.handleIntent(Objects.requireNonNull(getActivity()).getIntent());
        if (!canUse4GNetwork && NetworkUtils.getNetworkType() == NetworkUtils.TYPE_MOBILE) {
            showConfirmDialog(null, "正在使用手机流量,是否开始直播?", (dialog, which) -> {
                controller.liveStartStop();
                controller.canUse4GNetwork(true);
                canUse4GNetwork = true;
            }, (dialog, which) -> dialog.cancel());
        } else {
            controller.liveStartStop();
        }
    }

    public void fanzhuan(){
        controller.switchCam();
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    public void attachBottomBarToFragment(final LiveBottomBar liveBottomBar) {

        this.liveBottomBar = liveBottomBar;


        liveBottomBar.setMsgClickListener(v -> liveActivity.showInputPanel());
    }

    /**
     * 设置显示的SurfaceView
     *
     * @param hasFilter 是否带滤镜功能
     */
    @Override
    public void setSurfaceView(boolean hasFilter) {
        filterSurfaceView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置预览画面 大小
     *
     * @param hasFilter 是否有滤镜
     */
    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {
//        if(hasFilter){
//            filterSurfaceView.setPreviewSize(previewWidth, previewHeight);
//        }else{
//            normalSurfaceView.setPreviewSize(previewWidth, previewHeight);
//        }
    }

    /**
     * 获取正在显示的SurfaceView
     *
     * @return
     */
    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        return filterSurfaceView;
    }

    /**
     * 设置直播开始按钮, 是否可点击
     *
     * @param clickable
     */
    @Override
    public void setStartBtnClickable(boolean clickable) {
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {
        if (liveActivity != null) {
            liveActivity.onStartLivingFinished();
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {
        //btnRestart.setVisibility(View.GONE);
    }

    /**
     * 设置audio按钮状态
     *
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {
    }

    /**
     * 设置Video按钮状态
     *
     * @param isPlay 是否正在开启
     */
    @Override
    public void setVideoBtnState(boolean isPlay) {
    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {

    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {
        if (mPublishParam.openVideo) {

        }
    }

    /**
     * 获取Ui对应的controller
     *
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }

    /**
     * 按下返回键
     */
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void showAudioAnimate(boolean b) {
    }

    @Override
    public void onDisconnect() {
        //liveActivity为空,则已关闭直播页面
        if (liveActivity != null) {
            liveActivity.onLiveDisconnect();
            controller.liveStartStop();
        }
    }

    @Override
    public void normalFinish() {
        if (liveActivity != null) {
            Log.e(TAG,"结束？？？？？");
            liveActivity.normalFinishLive();
        }
    }

    @Override
    public void onStartInit() {
    }

    @Override
    public void onCameraPermissionError() {
        ToastUtil.makeShortText(getActivity(),"无法开启相机");
        showConfirmDialog("无法打开相机", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Objects.requireNonNull(getActivity()).finish();
            }
        }, null);
    }

    @Override
    public void onAudioPermissionError() {
        showConfirmDialog("无法开启录音", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, null);
    }

    public void destroyController() {
        controller.tryToStopLivingStreaming();
        controller.onDestroy();
    }
}
