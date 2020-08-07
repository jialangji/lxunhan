package com.ay.lxunhan.ui.message;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.contract.MessageContract;
import com.ay.lxunhan.presenter.MessagePresenter;
import com.ay.lxunhan.ui.message.activity.ChatP2PActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.message
 * @date 2020/8/6
 */
public class MessageListFragment extends BaseFragment<MessageContract.MessageView, MessagePresenter> implements MessageContract.MessageView {
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<ChatListBean> chatListBeans = new ArrayList<>();
    private BaseQuickAdapter chatListAdapter;
    private int page=1;
    private boolean isRefresh = true;
    private Handler uiHandle;

    @Override
    public MessagePresenter initPresenter() {
        return new MessagePresenter(this);
    }

    public static MessageListFragment newInstance() {
        Bundle args = new Bundle();
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected void initView() {
        super.initView();
        uiHandle=new Handler();
        chatListAdapter = new BaseQuickAdapter<ChatListBean, BaseViewHolder>(R.layout.item_chat_list, chatListBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ChatListBean item) {
                GlideUtil.loadCircleImgForHead(getActivity(), helper.getView(R.id.iv_header), item.getAvatar());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_time, item.getTimeText());
                helper.setText(R.id.tv_intro, item.getBody());
                helper.setGone(R.id.tv_message_count, item.getNewMessage() > 0);
                helper.setText(R.id.tv_message_count, item.getNewMessage()+"");
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setGone(R.id.iv_v, item.getIs_media());

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(linearLayoutManager);
        rvMessage.setAdapter(chatListAdapter);


        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    private Observer<List<IMMessage>> incomingMessageObserver = (Observer<List<IMMessage>>) messages -> {
        uiHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=1;
                presenter.getChatList(page);
            }
        },500);

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
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
        chatListAdapter.setOnItemClickListener((adapter, view, position) -> ChatP2PActivity.startChat(getActivity(),chatListBeans.get(position).getToAccount()));
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
    public void onResume() {
        super.onResume();
        if (UserInfo.getInstance().isLogin()){
            isRefresh=true;
            presenter.getChatList(page);
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
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }
}
