package com.ay.lxunhan.widget;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.FileUtils;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.SaveBitmap;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.WebViewUtils;
import com.just.agentweb.AgentWeb;

import java.io.File;
import java.util.Objects;

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
    private NestedScrollView scrollView;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ShareImgDialog(Activity context, int themeResId, String webUrl) {
        super(context, themeResId);
        this.context = context;
        this.webUrl = webUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.share_img);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout otherView = findViewById(R.id.other_view);
        scrollView = findViewById(R.id.mScroll);
        layoutWebview = findViewById(R.id.layout_webview);
        TextView tvShareAlbum = findViewById(R.id.tv_share_album);
        TextView tvShareQQ = findViewById(R.id.tv_share_qq);
        TextView tvSharewx = findViewById(R.id.tv_share_wx);
        TextView tvSharewb = findViewById(R.id.tv_share_wb);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        otherView.setOnClickListener(this);
        tvShareAlbum.setOnClickListener(this);
        tvShareQQ.setOnClickListener(this);
        tvSharewx.setOnClickListener(this);
        tvSharewb.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
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
        Bitmap bitmap = SaveBitmap.viewShot(scrollView);
        if (bitmap == null) {
            ToastUtil.makeShortText(context, "当前内容无法生成图片");
            dismiss();
            return;
        }
        switch (v.getId()) {
            case R.id.tv_cancel:
            case R.id.other_view:
                dismiss();
                break;
            case R.id.tv_share_album:
                PermissionsUtils.getInstance().chekPermissions(context, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                File file = FileUtils.saveBitmap(context, bitmap);
                                if (file != null || file.exists()) {
                                    ToastUtil.makeShortText(context, "保存成功");
                                    dismiss();
                                } else {
                                    ToastUtil.makeShortText(context, "保存失败");
                                }
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });
                break;
            case R.id.tv_share_qq:
                PermissionsUtils.getInstance().chekPermissions(context, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                String file1 = SaveBitmap.saveImageToGallery(context, bitmap);
                                if (itemClickListener != null) {
                                    itemClickListener.shareQQ(file1);
                                }
                                dismiss();
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });


                break;
            case R.id.tv_share_wx:
                if (itemClickListener!=null){
                    itemClickListener.shareWx();
                }
                ShareUtils.shareImgToWx(bitmap);
                dismiss();
                break;
            case R.id.tv_share_wb:
                if (itemClickListener != null) {
                    itemClickListener.shareWb(bitmap);
                }
                dismiss();
                break;
        }
    }

    public interface ItemClickListener {
        void shareQQ(String bitmap);
        void shareWx();
        void shareWb(Bitmap bitmap);
    }
}
