package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.utils.StringUtil;

import java.util.Objects;

/**
 * @author ${jlj
 * @package com.ay.lxunhan.widget
 * @date 2020/8/21
 */
public class InfoInputDialog extends Dialog{
    private static final int MAX_NUM = 15;
    private ItemClickListener itemClickListener;
    public InfoInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_input_info_public);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout otherView=findViewById(R.id.other_view);
        otherView.setOnClickListener(v -> dismiss());
        EditText etInfo=findViewById(R.id.et_info);
        TextView tvCancel=findViewById(R.id.btn_start);
        TextView tvSure=findViewById(R.id.btn_end);
        tvCancel.setOnClickListener(v -> dismiss());
        tvSure.setOnClickListener(v -> {
            if (itemClickListener!=null){
                itemClickListener.sure(StringUtil.getFromEdit(etInfo));
            }
            dismiss();
        });
        etInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_NUM) {
                    editable.delete(MAX_NUM, editable.length());
                }
            }
        });
    }

    public interface ItemClickListener {

        void sure(String info);
    }
}
