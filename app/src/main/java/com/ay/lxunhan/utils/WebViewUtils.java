package com.ay.lxunhan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.LogUtils;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListener;
import com.just.agentweb.download.Extra;


public class WebViewUtils {


    private static final String TAG = WebViewUtils.class.getSimpleName();


    public static void toWeb(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

    public static AgentWeb createWebView(LinearLayout layoutWebview, Activity context, String url) {
        AgentWeb mAgentWeb = AgentWeb.with(context)
                .setAgentWebParent(layoutWebview, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()// 使用默认进度条
                .setMainFrameErrorView(R.layout.webview_error, R.id.btn_click_refresh)
                .setAgentWebWebSettings(new AbsAgentWebSettings() {
                    private AgentWeb mAgentWeb;

                    @Override
                    protected void bindAgentWebSupport(AgentWeb agentWeb) {
                        this.mAgentWeb = agentWeb;
                    }

                    /**
                     * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
                     * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
                     * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
                     * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
                     * @param webView  控件
                     * @param downloadListener 监听
                     * @return WebListenerManager
                     */
                    @Override
                    public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
                        return super.setDownloader(webView,
                                DefaultDownloadImpl
                                        .create((Activity) webView.getContext(),
                                                webView,
                                                new DownloadListener() {

                                                    /**
                                                     *
                                                     * @param url                下载链接
                                                     * @param userAgent          UserAgent
                                                     * @param contentDisposition ContentDisposition
                                                     * @param mimetype           资源的媒体类型
                                                     * @param contentLength      文件长度
                                                     * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
                                                     * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
                                                     */
                                                    @Override
                                                    public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                                                        LogUtils.i(TAG, "onStart:" + url);
                                                        extra.setBreakPointDownload(true) // 是否开启断点续传
                                                                .setConnectTimeOut(6000) // 连接最大时长
                                                                .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                                                                .setDownloadTimeOut(Long.MAX_VALUE) // 下载最大时长
                                                                .setParallelDownload(true)  // 串行下载更节省资源哦
                                                                .setEnableIndicator(true)  // false 关闭进度通知
                                                                .addHeader("Cookie", "xx") // 自定义请求头
                                                                .setAutoOpen(true) // 下载完成自动打开
                                                                .setForceDownload(true); // 强制下载，不管网络网络类型
                                                        return false;
                                                    }


                                                    /**
                                                     *
                                                     * @param url  下载链接
                                                     * @param loaded  已经下载的长度
                                                     * @param length    文件的总大小
                                                     * @param usedTime   耗时 ，单位ms
                                                     * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
                                                     */
                                                    @Override
                                                    public void onProgress(String url, long loaded, long length, long usedTime) {
                                                        int mProgress = (int) ((loaded) / (float) length * 100);
                                                        LogUtils.i(TAG, "onProgress:" + mProgress);
                                                        super.onProgress(url, loaded, length, usedTime);
                                                    }

                                                    /**
                                                     *
                                                     * @param path 文件的绝对路径
                                                     * @param url  下载地址
                                                     * @param throwable    如果异常，返回给用户异常
                                                     * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
                                                     */
                                                    @Override
                                                    public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                                                        if (null == throwable) { //下载成功
                                                            //do you work
                                                            LogUtils.i(TAG, "download success");
                                                        } else {//下载失败
                                                            LogUtils.i(TAG, "download error");
                                                        }
                                                        return false; // true  不会发出下载完成的通知 , 或者打开文件
                                                    }
                                                },
                                                this.mAgentWeb.getPermissionInterceptor()));
                    }
                })//设置 IAgentWebSettings。
                .setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
        //自适应屏幕
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
        //支持缩放
        mAgentWeb.getAgentWebSettings().getWebSettings().setBuiltInZoomControls(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setSupportZoom(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        return mAgentWeb;
    }


}
