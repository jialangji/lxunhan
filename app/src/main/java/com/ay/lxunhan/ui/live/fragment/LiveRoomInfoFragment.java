package com.ay.lxunhan.ui.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.LiveDetail;
import com.ay.lxunhan.ui.live.LiveRoomActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.wyyim.chatroom.NimController;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LiveRoomInfoFragment extends BaseFragment {

    public static final String EXTRA_IS_AUDIENCE = "is_audence";
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_rd)
    TextView tvRd;
    @BindView(R.id.rv_people)
    RecyclerView rvPeople;
    @BindView(R.id.tv_people_count)
    TextView tvPeopleCount;
    boolean isAudience;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    private int onlineCount;
    private LiveRoomActivity liveActivity;
    private List<LiveDetail.UserListBean> userListBeans=new ArrayList<>();
    private BaseQuickAdapter adapter;
    private LiveDetail liveDetail;

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

    public static LiveRoomInfoFragment getInstance(boolean isAudience,String roomId) {
        LiveRoomInfoFragment fragment = new LiveRoomInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_IS_AUDIENCE, isAudience);
        bundle.putString(NimController.EXTRA_ROOM_ID,roomId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        isAudience = getArguments().getBoolean(EXTRA_IS_AUDIENCE, true);
        if (isAudience) {
            tvAttention.setVisibility(View.VISIBLE);
        } else {
            tvAttention.setVisibility(View.GONE);
        }
        adapter = new BaseQuickAdapter<LiveDetail.UserListBean,BaseViewHolder>(R.layout.item_live_img,userListBeans) {
            @Override
            protected void convert(BaseViewHolder helper, LiveDetail.UserListBean item) {
                GlideUtil.loadCircleImgForHead(getActivity(),helper.getView(R.id.iv_img),item.getAvatar());
            }
        };
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPeople.setLayoutManager(linearLayoutManager);
        rvPeople.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (liveActivity!=null)
        liveActivity.initPresenter().getLiveInfo(liveActivity.liveId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ivHeader.setOnClickListener(v -> FriendDetailActivity.startUserDetailActivity(getActivity(),liveDetail.getId()));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_live_audience_room_info;
    }


    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    public void updateMember(List<ChatRoomMember> members) {
        if (liveActivity!=null)
        liveActivity.initPresenter().getLiveInfo(liveActivity.liveId);
        onlineCount = members.size();
        tvPeopleCount.setText(onlineCount + "人");
    }

    public void refreshRoomInfo(ChatRoomInfo roomInfo) {
        if (liveActivity!=null)
        liveActivity.initPresenter().getLiveInfo(liveActivity.liveId);
        onlineCount = roomInfo.getOnlineUserCount();
        tvPeopleCount.setText(onlineCount + "人");
    }

    public void updateUserInfo(LiveDetail liveDetail){
        if (liveActivity!=null)
            this.liveDetail=liveDetail;
        GlideUtil.loadCircleImgForHead(getActivity(),ivHeader,liveDetail.getAvatar());
        tvName.setText(liveDetail.getLname());
        tvRd.setText(liveDetail.getRd()+"热度");
        if (liveDetail.getIs_fol()==0&&!isAudience){
            tvAttention.setVisibility(View.VISIBLE);
        }else {
            tvAttention.setVisibility(View.GONE);
        }
        userListBeans.clear();
        userListBeans.addAll(liveDetail.getUser_list());
        adapter.notifyDataSetChanged();
    }

    public void onReceivedNotification(ChatRoomNotificationAttachment attachment) {

        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setAccount(attachment.getTargets().get(0));
        chatRoomMember.setNick(attachment.getTargetNicks().get(0));

        if (!isAudience && chatRoomMember.getAccount().equals(UserInfo.getInstance().getWyyAccount())) {
            //主播的通知(主播进入房间,主播离开房间)不做处理,
            return;
        }

        switch (attachment.getType()) {
            case ChatRoomMemberIn:
                tvPeopleCount.setText(++onlineCount + "人");
                break;
            case ChatRoomMemberExit:
            case ChatRoomMemberKicked:
                tvPeopleCount.setText(--onlineCount + "人");
                break;
            case ChatRoomMemberMuteAdd:
                chatRoomMember.setMuted(true);
                if (liveActivity!=null)
                liveActivity.initPresenter().getLiveInfo(liveActivity.liveId);

                break;
            case ChatRoomMemberMuteRemove:
                chatRoomMember.setMuted(false);
                if (liveActivity!=null)
                liveActivity.initPresenter().getLiveInfo(liveActivity.liveId);
                break;
        }
    }

    @OnClick(R.id.tv_attention)
    public void onViewClicked() {
        if (liveActivity!=null)
            liveActivity.initPresenter().attention(liveDetail.getId());
    }
}
