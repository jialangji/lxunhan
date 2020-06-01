package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AttentionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AttentionActivity extends BaseActivity {

    @BindView(R.id.rl_attention)
    RecyclerView rlAttention;
    private List<AttentionBean> attentionBeans=new ArrayList<>();
    private BaseQuickAdapter attentionAdapter;
    private int page=1;


    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 5; i++) {
            attentionBeans.add(new AttentionBean());
        }
        attentionAdapter = new BaseQuickAdapter<AttentionBean,BaseViewHolder>(R.layout.item_attention,attentionBeans) {
            @Override
            protected void convert(BaseViewHolder helper, AttentionBean item) {

            }
        };
        rlAttention.setLayoutManager(new LinearLayoutManager(this));
        rlAttention.setAdapter(attentionAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        attentionAdapter.setOnLoadMoreListener(() -> {
            if (attentionBeans.size()==(page*20)){

            }else{
                attentionAdapter.loadMoreEnd();
            }

        },rlAttention);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    public static void startAttentionActivity(Context context){
        Intent intent=new Intent(context,AttentionActivity.class);
        context.startActivity(intent);
    }
}
