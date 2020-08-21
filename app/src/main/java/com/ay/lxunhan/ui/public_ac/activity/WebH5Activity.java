package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class WebH5Activity extends BaseActivity {
    private static final String INTENT_URL = "url";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web)
    WebView webview;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getIntent().getStringExtra("title"));
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(false);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webview.setBackgroundColor(0); // 设置背景色
        webview.setWebChromeClient(new MywebChromeClient());
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setWebViewClient(new MyWebClicne());
        webview.loadDataWithBaseURL(null, Utils.getHtmlData(getIntent().getStringExtra(INTENT_URL)), "text/html", "uft-8", null);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_h5;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }


    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    class MyWebClicne extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
        }

    }

    @Override
    protected void onDestroy() {
        if (webview != null) {
            webview.clearHistory();
            webview.clearSslPreferences();
            webview.clearFormData();
            webview.clearCache(true);
            webview.clearView();
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
        super.onDestroy();
    }

    public static void startWebActivity(Context context, String url,String title) {
        Intent intent = new Intent(context, WebH5Activity.class);
        intent.putExtra(INTENT_URL, url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }


    class MywebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {

            return true;
        }
    }

}
