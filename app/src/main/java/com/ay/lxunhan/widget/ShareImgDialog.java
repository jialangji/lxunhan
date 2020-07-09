package com.ay.lxunhan.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.FileUtils;
import com.ay.lxunhan.utils.SaveBitmap;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.WebViewUtils;
import com.just.agentweb.AgentWeb;

import java.io.File;

import static com.ay.lxunhan.utils.Contacts.DIR_NAME;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/7/8
 */
public class ShareImgDialog extends Dialog implements View.OnClickListener {
    private String webUrl;
    private Activity context;
    private AgentWeb mAgentWeb;
    private LinearLayout layoutWebview;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ShareImgDialog(Activity context, int themeResId, String webUrl) {
        super(context, themeResId);
        this.context=context;
        this.webUrl=webUrl;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.share_img);
        LinearLayout otherView=findViewById(R.id.other_view);
        layoutWebview = findViewById(R.id.layout_webview);
        TextView tvShareAlbum=findViewById(R.id.tv_share_album);
        TextView tvShareQQ = findViewById(R.id.tv_share_qq);
        TextView tvSharewx = findViewById(R.id.tv_share_wx);
        TextView tvSharewb = findViewById(R.id.tv_share_wb);
        otherView.setOnClickListener(this);
        tvShareAlbum.setOnClickListener(this);
        tvShareQQ.setOnClickListener(this);
        tvSharewx.setOnClickListener(this);
        tvSharewb.setOnClickListener(this);
        mAgentWeb = WebViewUtils.createWebView(layoutWebview, context, webUrl);

    }


    @Override
    public void dismiss() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap= SaveBitmap.viewShot(layoutWebview);
        switch (v.getId()) {
            case R.id.other_view:
                dismiss();
                break;
            case R.id.tv_share_album:
                File file = FileUtils.saveBitmap(context,bitmap, DIR_NAME);
                if (file.exists()) {
                    ToastUtil.makeShortText(context, "保存成功");
                    dismiss();
                } else {
                    ToastUtil.makeShortText(context, "保存失败");
                }
                break;
            case R.id.tv_share_qq:
                if (itemClickListener!=null){
                    itemClickListener.shareQQ();
                }
                dismiss();
                break;
            case R.id.tv_share_wx:
                ShareUtils.shareImgToWx(context,bitmap);
                dismiss();
                break;
            case R.id.tv_share_wb:
                if (itemClickListener!=null){
                    itemClickListener.shareWb();
                }
                dismiss();
                break;
        }
    }

    public interface ItemClickListener {
        void shareQQ();

        void shareWb();
    }
}
