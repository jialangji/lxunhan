package com.ay.lxunhan.ui.message;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.contract.MessageContract;
import com.ay.lxunhan.presenter.MessagePresenter;
import com.ay.lxunhan.ui.message.activity.AddFriendActivity;
import com.ay.lxunhan.ui.message.activity.FriendActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.IssueActivity;
import com.ay.lxunhan.ui.message.activity.PyqActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageFrgament extends BaseFragment<MessageContract.MessageView, MessagePresenter> implements MessageContract.MessageView {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<ChatListBean> chatListBeans = new ArrayList<>();
    private BaseQuickAdapter chatListAdapter;
    private int page=1;
    private boolean isRefresh = true;

    public static MessageFrgament newInstance() {
        Bundle args = new Bundle();
        MessageFrgament fragment = new MessageFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        chatListAdapter = new BaseQuickAdapter<ChatListBean, BaseViewHolder>(R.layout.item_chat_list, chatListBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ChatListBean item) {
                GlideUtil.loadCircleImgForHead(getActivity(), helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_intro, item.getBody());
                helper.setGone(R.id.tv_message_count, item.getNewMessage() > 0);
                helper.setText(R.id.tv_message_count, item.getNewMessage());
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setGone(R.id.iv_v, item.getIs_media());

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(linearLayoutManager);
        rvMessage.setAdapter(chatListAdapter);
    }

    @Override
    public MessagePresenter initPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getChatList(page);
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
                presenter.getChatList(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==chatListBeans.size()){
                    page=page+1;
                    isRefresh=false;
                    presenter.getChatList(page);
                }else{
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.iv_header, R.id.rl_search, R.id.iv_edit, R.id.rl_friend_add, R.id.rl_friend_list, R.id.rl_friend_pyq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                if (isLogin()){
                    FriendDetailActivity.startUserDetailActivity(getActivity(),"");
                }
                break;
            case R.id.rl_search:
                break;
            case R.id.iv_edit:
                PermissionsUtils.getInstance().chekPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                IssueActivity.startIssueActivity(getActivity());
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });
                break;
            case R.id.rl_friend_add:
                AddFriendActivity.startAddFriendActivity(getActivity());
                break;
            case R.id.rl_friend_list:
                FriendActivity.startFriendActivity(getActivity());
                break;
            case R.id.rl_friend_pyq:
                PyqActivity.startPyqActivity(getActivity());
                break;
//            case R.id.rl_about_my:
//                AboutMyActivity.stratAboutActivity(getActivity());
//                break;
//            case R.id.rl_comment:
//                break;
//            case R.id.rl_like:
//                break;
        }
    }

    @Override
    public void getChatListFinish(List<ChatListBean> listBeans) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            chatListBeans.clear();
            chatListBeans.addAll(listBeans);
        } else {
            swipeRefresh.finishLoadmore();
            chatListBeans.addAll(listBeans);
        }
        chatListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideUtil.loadCircleImgForHead(getActivity(), ivHeader, UserInfo.getInstance().getAvatar());

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
