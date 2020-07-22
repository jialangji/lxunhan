package com.ay.lxunhan.ui.live;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.LiveDetail;
import com.ay.lxunhan.contract.LiveContract;
import com.ay.lxunhan.presenter.LivePresenter;
import com.ay.lxunhan.ui.live.fragment.AudienceFragment;
import com.ay.lxunhan.ui.live.fragment.CaptureFragment;
import com.ay.lxunhan.ui.live.fragment.LiveRoomInfoFragment;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ReplyDialog;
import com.ay.lxunhan.wyyim.chatroom.ChatRoomMessageFragment;
import com.ay.lxunhan.wyyim.chatroom.ChatRoomMsgListPanel;
import com.ay.lxunhan.wyyim.chatroom.NimController;
import com.ay.lxunhan.wyyim.liveuser.CapturePreviewController;
import com.ay.lxunhan.wyyim.liveuser.LiveBottomBar;
import com.ay.lxunhan.wyyim.liveuser.NimContract;
import com.ay.lxunhan.wyyim.liveuser.PublishParam;
import com.netease.LSMediaCapture.util.sys.ScreenUtil;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

import butterknife.BindView;

public class LiveRoomActivity extends BaseActivity<LiveContract.LiveView, LivePresenter> implements NimContract.Ui, LiveContract.LiveView {

    @BindView(R.id.layout_chat_room)
    FrameLayout chatLayout;
    @BindView(R.id.iv_operate)
    ImageView ivOperate;
    @BindView(R.id.tv_operate_name)
    TextView tvOperateName;
    @BindView(R.id.tv_finish_tip)
    TextView tvFinishTip;
    @BindView(R.id.btn_finish_back)
    TextView btnFinishBack;
    @BindView(R.id.layout_live_root)
    ViewGroup rootView;
    @BindView(R.id.layout_room_info)
    FrameLayout roomInfoLayout;
    private NimController nimController;
    private boolean isAudience = true; //默认为观众
    private String roomId;
    public String liveId;
    private boolean isLiveStart; //是否已开启直播
    public static final String IS_AUDIENCE = "is_audience";
    LinearLayout ll_live_finish;
    private CaptureFragment captureFragment; //主播
    private AudienceFragment audienceFragment; //观众
    private ChatRoomMessageFragment chatRoomFragment;
    private LiveRoomInfoFragment liveRoomInfoFragment; //房间信息
    private LiveBottomBar liveBottomBar;
    private ReplyDialog replyDialog;

    @Override
    public LivePresenter initPresenter() {
        return new LivePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void initView() {
        super.initView();
        isAudience = getIntent().getBooleanExtra(IS_AUDIENCE, true);
        roomId = getIntent().getStringExtra(NimController.EXTRA_ROOM_ID);
        liveId=getIntent().getStringExtra("liveid");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        float screenHeight = ScreenUtil.getDisplayHeight();
        nimController = new NimController(this, this);
        nimController.onHandleIntent(getIntent());
        loadFragment(isAudience);
        initBottomBar();
        initFinishLiveLayout();
        if(isAudience){
            //观众 直接显示聊天列表与底部控制栏
            onStartLivingFinished();
        }
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
    public void onEnterChatRoomSuc(String roomId) {
        chatRoomFragment = (ChatRoomMessageFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_fragment);
        if (chatRoomFragment != null) {
            initChatRoomFragment();
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getBaseHandler().postDelayed(() -> onEnterChatRoomSuc(roomId), 50);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getGold();
    }

    /**
     * 初始化聊天室Fragment
     */
    private void initChatRoomFragment() {
        chatRoomFragment.init(roomId);
        chatRoomFragment.setMsgExtraDelegate(new ChatRoomMsgListPanel.ExtraDelegate() {

            @Override
            public void onReceivedCustomAttachment(ChatRoomMessage msg) {
                if (msg.getMsgType()==MsgTypeEnum.custom) {
                    // 收到礼物消息
                    liveBottomBar.showGiftAnimation(msg);
                } else if (msg.getAttachment() instanceof ChatRoomNotificationAttachment) {
                    liveRoomInfoFragment.onReceivedNotification((ChatRoomNotificationAttachment) msg.getAttachment());
                }
            }

            @Override
            public void onMessageClick(IMMessage imMessage) {
                if (imMessage.getMsgType() == MsgTypeEnum.text) {
                }
            }
        });
    }

    /**
     * 显示聊天输入布局 展开键盘
     */
    public void showInputPanel() {
        showReplyDialog2();
    }

    public void showReplyDialog2() {
        if (replyDialog == null) {
            replyDialog = new ReplyDialog(this);
            replyDialog.setOnShowListener(dialog -> replyDialog.showKeyboard());
        }
        if (!replyDialog.isShowing()) {
            replyDialog.show();
        }
        replyDialog.setOnReplyClickListenr(() -> {
            if (!TextUtils.isEmpty(replyDialog.getContent())) {
                chatRoomFragment.onTextMessageSendButtonPressed(replyDialog.getContent());
                replyDialog.setContext();
                replyDialog.dismiss();
            }

        });


    }


    public void refreshRoomInfo(ChatRoomInfo roomInfo) {
        liveRoomInfoFragment.refreshRoomInfo(roomInfo);
        tvOperateName.setText(roomInfo.getCreator());
    }

    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        if(result == null) return;
        liveRoomInfoFragment.updateMember(result);
    }

    @Override
    public void dismissMemberOperateLayout() {

    }

    private void loadFragment(boolean isAudience) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isAudience) {
            audienceFragment = new AudienceFragment();
            transaction.replace(R.id.layout_main_content, audienceFragment);
        } else {
            captureFragment = new CaptureFragment();
            transaction.replace(R.id.layout_main_content, captureFragment);
        }
        liveRoomInfoFragment = LiveRoomInfoFragment.getInstance(isAudience,roomId);
        transaction.replace(R.id.layout_room_info, liveRoomInfoFragment);
        transaction.commit();
    }

    @Override
    public void onChatRoomFinished(String reason) {
        ll_live_finish.setVisibility(View.VISIBLE);
        tvFinishTip.setText(reason);
        liveBottomBar.setVisibility(View.GONE);
        if (isAudience && audienceFragment != null) {
            audienceFragment.stopWatching();
        }
    }

    @Override
    public void showTextToast(String text) {
        ToastUtil.makeShortText(this, text);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        nimController.onDestroy();
        nimController.logoutChatRoom();
        if (captureFragment != null) {
            captureFragment.destroyController();
        }

    }

    private void initBottomBar() {
        liveBottomBar = new LiveBottomBar(this, isAudience, getIntent().getStringExtra(NimController.EXTRA_ROOM_ID));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rootView.addView(liveBottomBar, layoutParams);

        if (isAudience) {
            audienceFragment.attachBottomBarToFragment(liveBottomBar);
        } else {
            captureFragment.attachBottomBarToFragment(liveBottomBar);
        }

        liveBottomBar.setVisibility(View.INVISIBLE);
    }

    /**
     * 静态方法 启动主播
     *
     * @param context 上下文
     */
    public static void startLive(Context context, String roomId, PublishParam param,String liveId) {
        Intent intent = new Intent(context, LiveRoomActivity.class);
        intent.putExtra(IS_AUDIENCE, false);
        intent.putExtra("liveid",liveId);
        intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
        intent.putExtra(CapturePreviewController.EXTRA_PARAMS, param);
        context.startActivity(intent);
    }

    /**
     * 静态方法 启动观众
     *
     * @param context 上下文
     * @param url     直播地址
     */
    public static void startAudience(Context context, String roomId, String url, boolean isSoftDecode,String liveId) {
        Intent intent = new Intent();
        intent.setClass(context, LiveRoomActivity.class);
        intent.putExtra(IS_AUDIENCE, true);
        intent.putExtra("liveid",liveId);
        intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
        intent.putExtra(AudienceFragment.IS_LIVE, true); //观众默认为直播, 另一个种模式为点播.
        intent.putExtra(AudienceFragment.IS_SOFT_DECODE, isSoftDecode);
        intent.putExtra(AudienceFragment.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //未开始直播,则直接退出
        if (!isAudience && !isLiveStart) {
            super.onBackPressed();
            return;
        }
        showConfirmDialog(null, "确定结束直播?", (dialog, which) -> normalFinishLive(), (dialog, which) -> dialog.cancel());
    }


    /**
     * 直播开始完成的回调
     */
    public void onStartLivingFinished() {
        isLiveStart = true;
        presenter.openLive(liveId);
        chatLayout.setVisibility(View.VISIBLE);
        liveBottomBar.setVisibility(View.VISIBLE);
        roomInfoLayout.setVisibility(View.VISIBLE);
    }

    public void fanzhuan() {
        captureFragment.fanzhuan();
    }

    /**
     * 直播断开时的回调
     */
    public void onLiveDisconnect() {
        isLiveStart = false;
        chatLayout.setVisibility(View.GONE);
        liveBottomBar.setVisibility(View.GONE);
        roomInfoLayout.setVisibility(View.GONE);
    }

    /**
     * 初始化直播结束布局
     */
    private void initFinishLiveLayout() {
        ll_live_finish = findViewById(R.id.ll_live_finish);
        btnFinishBack.setOnClickListener(v -> finish());
        ll_live_finish.setOnClickListener(v -> {
            //空方法,拦截点击事件
        });

    }

    /**
     * 正常结束直播
     */
    public void normalFinishLive() {
        //主播发送离开房间请求
        if (!isAudience) {
            //TODO: 调用关闭直播接口
            presenter.liveClose(liveId);
        } else {
            presenter.liveSeeCount(liveId,false);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.giftList(String.valueOf(1));
    }

    @Override
    public void liveSeecountFinish(boolean isAdd) {
        if (!isAdd){
            finish();
        }
    }

    @Override
    public void giftListFinish(List<GiftBean> giftBeans) {
        liveBottomBar.setGiftList(giftBeans);
    }

    @Override
    public void liveCloseFinish() {
        finish();
    }

    @Override
    public void sendGiftFinish() {
        liveBottomBar.sendGift();
        presenter.getGold();
    }

    public void sendGift(GiftBean giftBean,String count){
        presenter.sendGift(liveId,String.valueOf(giftBean.getId()),count);
    }

    @Override
    public void getLiveInfoFinish(LiveDetail liveDetail) {
        tvOperateName.setText(liveDetail.getLname());
        GlideUtil.loadCircleImgForHead(this,ivOperate,liveDetail.getAvatar());
        liveRoomInfoFragment.updateUserInfo(liveDetail);
    }

    @Override
    public void openLiveFinish() {

    }

    @Override
    public void getShowFinish(LbBean lbBean) {
        liveBottomBar.setTvGold(lbBean.getGold());
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
