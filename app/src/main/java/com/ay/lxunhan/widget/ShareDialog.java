package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.ToastUtil;

import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/5/29
 */
public class ShareDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private ItemClickListener itemClickListener;


    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.share_dialog);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        View other_view = findViewById(R.id.other_view);
        TextView tvShareFriend = findViewById(R.id.tv_share_friend);
        TextView tvShareQQ = findViewById(R.id.tv_share_qq);
        TextView tvShareQQroom = findViewById(R.id.tv_share_qq_room);
        TextView tvSharewx = findViewById(R.id.tv_share_wx);
        TextView tvSharewxpyq = findViewById(R.id.tv_share_wx_pyq);
        TextView tvSharewb = findViewById(R.id.tv_share_wb);
        TextView tvShareImg = findViewById(R.id.tv_share_img);
        TextView tvcopyurl = findViewById(R.id.tv_copy_url);
        TextView tvComplaint = findViewById(R.id.tv_complaint);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvCollect=findViewById(R.id.tv_collect);
        tvCollect.setOnClickListener(this);
        tvShareFriend.setOnClickListener(this);
        tvShareQQ.setOnClickListener(this);
        tvShareQQroom.setOnClickListener(this);
        tvSharewx.setOnClickListener(this);
        tvSharewxpyq.setOnClickListener(this);
        tvSharewb.setOnClickListener(this);
        tvShareImg.setOnClickListener(this);
        tvcopyurl.setOnClickListener(this);
        tvComplaint.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        other_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_view:
                dismiss();
                break;
            case R.id.tv_share_friend:
                if (itemClickListener!=null){
                    itemClickListener.shareFriends();
                }
                dismiss();
                break;
            case R.id.tv_share_qq:
                if (itemClickListener!=null){
                    itemClickListener.shareQQ();
                }
                dismiss();
                break;
            case R.id.tv_share_qq_room:
                if (itemClickListener!=null){
                    itemClickListener.shareQQRoom();
                }
                dismiss();
                break;
            case R.id.tv_share_wx:
                if (itemClickListener!=null){
                    itemClickListener.shareWx();
                }
                dismiss();
                break;
            case R.id.tv_share_wx_pyq:
                if (itemClickListener!=null){
                    itemClickListener.shareWxPyq();
                }
                dismiss();
                break;
            case R.id.tv_share_wb:
                if (itemClickListener!=null){
                    itemClickListener.shareWb();
                }
                dismiss();
                break;
            case R.id.tv_copy_url:
                if (itemClickListener!=null){
                    itemClickListener.copyUrl();
                }
                ToastUtil.makeShortText(context,"链接已复制");
                dismiss();
                break;
            case R.id.tv_share_img:
                if (itemClickListener!=null){
                    itemClickListener.shareImg();
                }
                dismiss();
                break;
            case R.id.tv_complaint:
                if (itemClickListener!=null){
                    itemClickListener.complaint();
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                if (itemClickListener!=null){
                    itemClickListener.cancel();
                }
                dismiss();
                break;
            case R.id.tv_collect:
                if (itemClickListener!=null){
                    itemClickListener.collect();
                }
                dismiss();
                break;

        }
    }


    public interface ItemClickListener {
        void shareFriends();

        void shareQQ();

        void shareQQRoom();

        void shareWx();

        void shareWxPyq();

        void shareWb();

        void shareImg();

        void copyUrl();

        void complaint();

        void collect();

        void cancel();
    }
}
