package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.contract.LbContract;
import com.ay.lxunhan.presenter.LbPresenter;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LCoinActivity extends BaseActivity<LbContract.LbView, LbPresenter>implements LbContract.LbView {


    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    private List<Integer> imgList = new ArrayList<>();
    private List<TaskBean> taskBeanList = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter taskAdapter;

    @Override
    public LbPresenter initPresenter() {
        return new LbPresenter(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getShow();
        presenter.getLbTask();
    }

    @Override
    protected void initView() {
        super.initView();
        imgList.add(R.drawable.ic_weal);
        imgList.add(R.drawable.ic_user_task);
        imgList.add(R.drawable.ic_coming_soon);
        adapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_lb_img, imgList) {
            @Override
            protected void convert(BaseViewHolder helper, Integer item) {
                helper.setImageResource(R.id.iv_lb_img, item);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImg.setLayoutManager(linearLayoutManager);
        rvImg.setAdapter(adapter);
        taskAdapter = new BaseQuickAdapter<TaskBean, BaseViewHolder>(R.layout.item_task, taskBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, TaskBean item) {
                GlideUtil.loadCircleImg(LCoinActivity.this,helper.getView(R.id.iv_type_img),item.getIcon());
                helper.setText(R.id.tv_type_title,item.getName());
                helper.setText(R.id.tv_type_lb,item.getGold()+"乐讯币");
                ProgressBar progressBar=helper.getView(R.id.progressBarSmall);
                progressBar.setMax(item.getNumber());
                progressBar.setProgress(item.getTaskCount());
                helper.setText(R.id.tv_success_count,item.getTaskCount()+"");
                helper.setText(R.id.tv_all_count,"/"+item.getNumber());
            }
        };
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.setAdapter(taskAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0 || position == 1) {

            }
        });
        taskAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (taskBeanList.get(position).getTaskFlg()){
                MainActivity.startMainActivity(LCoinActivity.this,0);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lcoin;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startLCoinActivity(Context context) {
        Intent intent = new Intent(context, LCoinActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_exchange, R.id.rl_finish, R.id.tv_detail, R.id.iv_shop, R.id.iv_anchor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_exchange:
                ExChangeActivity.startExChangeActivity(this);
                break;
            case R.id.tv_detail:
                LCoinDetailActivity.startLCoinDetailActivity(this);
                break;
            case R.id.iv_shop:
                break;
            case R.id.iv_anchor:
                break;
        }
    }

    @Override
    public void getShowFinish(LbBean lbBean) {
        tvCoin.setText(lbBean.getGold());
        tvCash.setText(String.format("约%s元", lbBean.getMoney()));
    }

    @Override
    public void getLbTask(List<TaskBean> list) {
        taskBeanList.clear();
        taskBeanList.addAll(list);
        taskAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
