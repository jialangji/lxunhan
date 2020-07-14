package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ay.lxunhan.R;

import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/7/9
 */
public class ShareFriendDialog extends Dialog implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    public ShareFriendDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_share_friend);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout otherView=findViewById(R.id.other_view);
        ImageView ivCancel=findViewById(R.id.iv_cancel);
        LinearLayout llQQ=findViewById(R.id.ll_qq);
        LinearLayout llWx=findViewById(R.id.ll_wx);
        otherView.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        llQQ.setOnClickListener(this);
        llWx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.other_view:
                case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.ll_qq:
                if (itemClickListener!=null){
                    itemClickListener.shareQQ();
                }
                dismiss();
                break;
            case R.id.ll_wx:
                if (itemClickListener!=null){
                    itemClickListener.shareWx();
                }
                dismiss();
                break;
        }
    }


    public interface ItemClickListener {
        void shareQQ();

        void shareWx();
    }
}
