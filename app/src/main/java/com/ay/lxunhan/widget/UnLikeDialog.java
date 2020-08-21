package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;

import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/8/21
 */
public class UnLikeDialog extends Dialog implements View.OnClickListener {

    private CheckBox checkAuthor;
    private CheckBox checkText;
    private ItemClickListener itemClickListener;
    private int type=0;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public UnLikeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_unlike);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        View other_view = findViewById(R.id.other_view);
        RelativeLayout rlAuthor=findViewById(R.id.rl_author);
        RelativeLayout rlText=findViewById(R.id.rl_text);
        checkAuthor = findViewById(R.id.check_author);
        checkText = findViewById(R.id.check_text);
        TextView tvSuccess=findViewById(R.id.tv_success);
        other_view.setOnClickListener(this);
        rlAuthor.setOnClickListener(this);
        rlText.setOnClickListener(this);
        tvSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_view:
                dismiss();
                break;
            case R.id.rl_author:
                checkAuthor.setChecked(true);
                checkText.setChecked(false);
                type=1;
                break;
            case R.id.rl_text:
                checkAuthor.setChecked(false);
                checkText.setChecked(true);
                type=2;
                break;
            case R.id.tv_success:
                if (itemClickListener!=null){
                    itemClickListener.success(type);
                }
                dismiss();
                break;
        }
    }

    public interface ItemClickListener {

        void success(int type);
    }
}
