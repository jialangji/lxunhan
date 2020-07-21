package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/7/15
 */
public class SelectLiveTypeDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private List<LiveBean> liveBeans;
    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SelectLiveTypeDialog(Context context, int themeResId, List<LiveBean> list) {
        super(context, themeResId);
        this.context = context;
        this.liveBeans = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.select_livetype_dialog);

        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        View ontherView=findViewById(R.id.other_view);
        ontherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.cancel();
                }
            }
        });
        RecyclerView rvType = findViewById(R.id.rv_type);
        BaseQuickAdapter typeAdapter = new BaseQuickAdapter<LiveBean, BaseViewHolder>(R.layout.item_live_type,liveBeans) {
            @Override
            protected void convert(BaseViewHolder helper, LiveBean item) {
                GlideUtil.loadImg(context,helper.getView(R.id.iv_img),item.getCover());
                helper.setText(R.id.tv_title,item.getName());
                helper.setText(R.id.tv_part, StringUtil.getString(item.isSelect()?R.string.yi_part:R.string.part));
            }
        };
        rvType.setLayoutManager(new LinearLayoutManager(context));
        rvType.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (LiveBean liveBean : liveBeans) {
                liveBean.setSelect(false);
            }
            liveBeans.get(position).setSelect(true);
            typeAdapter.notifyDataSetChanged();
            if (itemClickListener != null) {
                itemClickListener.onItem(liveBeans.get(position));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public interface ItemClickListener {
        void onItem(LiveBean typeBean);
        void cancel();
    }
}
