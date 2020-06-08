package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.bean.SelectTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/6/8
 */
public class SelectTypeDialog extends Dialog implements View.OnClickListener {

    private List<SelectTypeBean> list=new ArrayList<>();
    private BaseQuickAdapter typeAdapter;
    private Context context;
    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SelectTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.select_type_dialog);
        list.add(new SelectTypeBean("全部",0));
        list.add(new SelectTypeBean("文章",1));
        list.add(new SelectTypeBean("视频",2));
        list.add(new SelectTypeBean("小视频",5));
        list.add(new SelectTypeBean("问答",3));
        list.add(new SelectTypeBean("投票",4));
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        RecyclerView rvType=findViewById(R.id.rv_type);
        typeAdapter = new BaseQuickAdapter<SelectTypeBean,BaseViewHolder>(R.layout.item_select_type,list) {
            @Override
            protected void convert(BaseViewHolder helper, SelectTypeBean item) {
                helper.setText(R.id.tv_name,item.getName());
                helper.setTextColor(R.id.tv_name,context.getResources().getColor(item.isSelect()?R.color.white:R.color.color_86));
                TextView tvname=helper.getView(R.id.tv_name);
                tvname.setBackground(context.getResources().getDrawable(item.isSelect()?R.drawable.shape_radius_pink_10:R.drawable.shape_white_bg_10));
            }
        };
        rvType.setLayoutManager(new GridLayoutManager(context,3));
        rvType.addItemDecoration(new GridDividerDecoration(context,10,GridLayoutManager.VERTICAL));
        rvType.setAdapter(typeAdapter);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        typeAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < list.size(); i++) {
                if (position==i){
                    list.get(position).setSelect(true);
                }else{
                    list.get(position).setSelect(false);
                }
            }
            typeAdapter.notifyDataSetChanged();
            if (itemClickListener!=null){
                itemClickListener.onItem(position,list.get(position));
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (itemClickListener!=null){
                    dismiss();
                }
                break;
        }
    }

    public interface ItemClickListener {
        void onItem(int position,SelectTypeBean typeBean);
    }
}
