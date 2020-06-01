package com.ay.lxunhan.ui.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageFrgament extends BaseFragment {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    private List<ChatListBean> chatListBeans = new ArrayList<>();
    private BaseQuickAdapter chatListAdapter;

    public static MessageFrgament newInstance() {
        Bundle args = new Bundle();
        MessageFrgament fragment = new MessageFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        GlideUtil.loadCircleImgForHead(getActivity(),ivHeader, UserInfo.getInstance().getAvatar());
        for (int i = 0; i < 5; i++) {
            chatListBeans.add(new ChatListBean());
        }
        chatListAdapter = new BaseQuickAdapter<ChatListBean, BaseViewHolder>(R.layout.item_chat_list, chatListBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ChatListBean item) {

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(linearLayoutManager);
        rvMessage.setAdapter(chatListAdapter);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
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
                break;
            case R.id.rl_search:
                break;
            case R.id.iv_edit:
                break;
            case R.id.rl_friend_add:
                break;
            case R.id.rl_friend_list:
                break;
            case R.id.rl_friend_pyq:
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
}
