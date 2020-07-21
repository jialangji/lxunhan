package com.ay.lxunhan.wyyim.liveuser;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.ui.live.LiveRoomActivity;
import com.ay.lxunhan.ui.my.activity.BuyCoinActivity;
import com.ay.lxunhan.utils.BigDecimalUtils;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.CountPop;
import com.ay.lxunhan.wyyim.chatroom.ChatRoomMemberCache;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveBottomBar extends RelativeLayout {

    private View btn_msg;
    private View btn_gift;
    private View btn_fanzhuan;
    private View btn_close;

    private View btn_send_gift;

    private ViewGroup giftLayout; // 礼物布局
    private RecyclerView giftView; // 礼物列表
    private LinearLayout giftAnimationViewDown; // 礼物动画布局1
    private LinearLayout giftAnimationViewUp; // 礼物动画布局2
    private GiftAnimation giftAnimation; // 礼物动画
    private List<GiftBean> giftList = new ArrayList<>(); // 礼物列表数据
    private LiveRoomActivity liveActivity;
    // 发送礼物频率控制使用
    private long lastClickTime = 0;
    boolean isAudience;
    private String roomId;
    private int giftPosition = -1;
    private BaseQuickAdapter adapter;
    private int count=1;
    private ImageView ivClose;
    private TextView tvRecharge;
    private TextView tvGold;
    private LinearLayout llselect;
    private TextView tvSelectCount;
    private ImageView ivTable;
    private boolean isShowPop=false;
    private CountPop countPop;
    private String gold;


    public LiveBottomBar(LiveRoomActivity context, boolean isAudience, String roomId) {
        super(context);
        this.isAudience = isAudience;
        this.roomId = roomId;
        int resourceId = R.layout.layout_live_audience_bottom_bar;
        this.liveActivity=context;
        LayoutInflater.from(context).inflate(resourceId, this, true);
        initView();
    }

    private void initView() {
        bindView();
        initGiftLayout();
        showPop();
        clickView();
    }

    private void bindView() {

        btn_msg = findView(R.id.audience_message);
        btn_gift = findView(R.id.audience_gift);
        btn_fanzhuan = findView(R.id.audience_fanzhuan);
        btn_close = findView(R.id.audience_close);
        // 点赞的爱心布局
        if (isAudience) {
            btn_fanzhuan.setVisibility(GONE);
        }
    }

    public void showPop(){
        countPop = new CountPop(liveActivity);
        countPop.setOnItemClick(str -> {
            isShowPop=false;
            ivTable.setImageResource(R.drawable.ic_live_up);
            tvSelectCount.setText(str);
            count= Integer.parseInt(str);
            countPop.dismiss();
        });
        countPop.setOnDismissListener(() -> {
            isShowPop=false;
            ivTable.setImageResource(R.drawable.ic_live_up);
            countPop.dismiss();
        });
    }

    // 初始化礼物布局
    protected void initGiftLayout() {
        tvGold = findView(R.id.tv_gold);
        tvRecharge = findView(R.id.tv_recharge);
        ivClose = findView(R.id.iv_close);
        giftLayout = findView(R.id.gift_layout);
        giftView = findView(R.id.gift_grid_view);
        llselect = findView(R.id.ll_select);
        tvSelectCount = findView(R.id.tv_select_count);
        ivTable = findView(R.id.iv_table);
        llselect.setOnClickListener(v -> {
            if (!isShowPop){
                isShowPop=true;
                ivTable.setImageResource(R.drawable.ic_live_down);
                countPop.showPopupWindow(llselect);
            }
        });

        giftAnimationViewDown = findView(R.id.gift_animation_view);
        giftAnimationViewUp = findView(R.id.gift_animation_view_up);
        giftAnimation = new GiftAnimation(giftAnimationViewDown, giftAnimationViewUp);
        giftLayout.setOnClickListener(v -> {
            giftLayout.setVisibility(View.GONE);
            giftPosition = -1;
        });
        tvRecharge.setOnClickListener(v -> BuyCoinActivity.startBuyCoinActivity(liveActivity));
        ivClose.setOnClickListener(v -> {
            giftLayout.setVisibility(View.GONE);
            giftPosition = -1;
        });
        btn_send_gift = findView(R.id.send_gift_btn);
        btn_send_gift.setOnClickListener(v -> {
            if (giftPosition == -1) {
                Toast.makeText(getContext(), "请选择礼物", Toast.LENGTH_SHORT).show();
                return;
            }
            String giftGold=BigDecimalUtils.multiplyToString(String.valueOf(count), String.valueOf(giftList.get(giftPosition).getGold()));
            if(!BigDecimalUtils.compareIsBig(giftGold,gold)){
                liveActivity.sendGift(giftList.get(giftPosition), String.valueOf(count));
            }else {
                Toast.makeText(getContext(), "当前乐讯币不足，请充值", Toast.LENGTH_SHORT).show();
            }

        });
        adapter = new BaseQuickAdapter<GiftBean, BaseViewHolder>(R.layout.item_gift, giftList) {
            @Override
            protected void convert(BaseViewHolder helper, GiftBean item) {
                GlideUtil.loadImg(getContext(),helper.getView(R.id.iv_gift),item.getCover());
                helper.setText(R.id.tv_gift_name,item.getName());
                helper.setText(R.id.tv_gift_coin,item.getGold()+"乐币");
                RelativeLayout llyout=helper.getView(R.id.ll_layout);
                llyout.setBackground(getResources().getDrawable(item.isSelect()?R.drawable.item_border_selected:R.drawable.item_border_unselected));

            }
        };
        giftView.setLayoutManager(new GridLayoutManager(getContext(),4));
        giftView.addItemDecoration(new GridSpacingItemDecoration(4,10,true));
        giftView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            giftPosition=position;
            for (GiftBean bean : giftList) {
                bean.setSelect(false);
            }
            giftList.get(position).setSelect(true);
            adapter.notifyDataSetChanged();
        });

    }

    private void clickView() {
        btn_gift.setOnClickListener(v -> showGiftLayout());
        btn_close.setOnClickListener(v -> liveActivity.onBackPressed());
        btn_fanzhuan.setOnClickListener(v -> liveActivity.fanzhuan());
    }
    public void setGiftList(List<GiftBean> beans){
        giftList.clear();
        giftList.addAll(beans);
        adapter.notifyDataSetChanged();
    }

    public void setTvGold(String str) {
        gold = str;
        tvGold.setText(str);
    }

    // 显示礼物列表
    private void showGiftLayout() {
        giftLayout.setVisibility(View.VISIBLE);
    }

    // 发送礼物
    public void sendGift() {
        giftLayout.setVisibility(View.GONE);
        GiftBean bean=giftList.get(giftPosition);
        bean.setHeader(UserInfo.getInstance().getAvatar());
        bean.setUserName(UserInfo.getInstance().getUserName());
        bean.setCount(count);
        GiftAttachment attachment = new GiftAttachment(bean, count);
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomCustomMessage(roomId, attachment);
        setMemberType(message);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(AppContext.instance, "用户被禁言,无法发送礼物", Toast.LENGTH_SHORT).show();
                        } else if (code == ResponseCode.RES_CHATROOM_ROOM_MUTED) {
                            Toast.makeText(AppContext.instance, "全体禁言,无法发送礼物", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AppContext.instance, "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(AppContext.instance, "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });
        giftAnimation.showGiftAnimation(message);
        for (GiftBean giftBean : giftList) {
            giftBean.setSelect(false);
        }
        adapter.notifyDataSetChanged();
        count=1;
        giftPosition = -1; // 发送完毕，置空
        tvSelectCount.setText(count+"");

    }

    // 显示礼物动画
    public void showGiftAnimation(ChatRoomMessage msg) {
        giftAnimation.showGiftAnimation(msg);
    }

    public void setMsgClickListener(OnClickListener onClickListener) {
        btn_msg.setOnClickListener(onClickListener);
    }


    private void setMemberType(ChatRoomMessage message) {
        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, UserInfo.getInstance().getWyyAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}