package com.ay.lxunhan.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatP2PActivity extends BaseActivity {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.rl_chat)
    RecyclerView rlChat;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private View rootView;
    private String sessionId;

    @Override
    protected void initView() {
        super.initView();
        rootView = View.inflate(this,R.layout.activity_chat_p2_p,null);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_p2_p;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.rl_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_more:
                break;
        }
    }

    public static void startChat(Context context, String id, SessionTypeEnum sessionType){
        Intent intent=new Intent(context,ChatP2PActivity.class);
        context.startActivity(intent);
    }
}
