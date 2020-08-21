package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;

import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/8/8
 */
public class GetDialog extends Dialog {
    private Context context;
    private TextView tvType;
    private TextView tvCoin;
    private boolean isSing;
    private String coin;

    public GetDialog(Context context, int themeResId,boolean isSing,String coin) {
        super(context, themeResId);
        this.context=context;
        this.isSing=isSing;
        this.coin=coin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.get_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        tvType = findViewById(R.id.tv_type);
        tvCoin = findViewById(R.id.tv_coin);
        RelativeLayout otherView=findViewById(R.id.other_view);
        otherView.setOnClickListener(v -> dismiss());
        if (isSing){
            tvType.setText("签到成功");
        }else{
            tvType.setText("领取成功");
        }
        tvCoin.setText(coin+"乐讯币");

    }

}
