package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.WebViewUtils;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {
    private static final String INTENT_URL = "url";

    @BindView(R.id.layout_webview)
    LinearLayout layoutWebview;

    private AgentWeb mAgentWeb;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getIntent().hasExtra(INTENT_URL)) {
            String url = getIntent().getStringExtra(INTENT_URL);
            mAgentWeb= WebViewUtils.createWebView(layoutWebview,this,url);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
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

    @Override
    protected void onPause() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null != mAgentWeb && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void startWebActivity(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(INTENT_URL, url);
        context.startActivity(intent);
    }
}
