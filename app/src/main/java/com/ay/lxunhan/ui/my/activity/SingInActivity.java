package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.SingBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.contract.SingContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.SingPresenter;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.GetDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class SingInActivity extends BaseActivity<SingContract.SingView, SingPresenter> implements SingContract.SingView {

    @BindView(R.id.tv_lb)
    TextView tvLb;
    @BindView(R.id.tv_sing_get)
    TextView tvSingGet;
    @BindView(R.id.tv_sing_count)
    TextView tvSingCount;
    @BindView(R.id.rv_sing)
    RecyclerView rvSing;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    private List<TaskBean> taskBeanList = new ArrayList<>();
    private List<SingBean.SignsBean> signsBeans=new ArrayList<>();
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter taskAdapter;
    private SingBean singBean;
    private GetDialog getDialog;
    private int mPosition;

    @Override
    public SingPresenter initPresenter() {
        return new SingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sing_in;
    }

    @Override
    protected void initView() {
        super.initView();

        adapter=new BaseQuickAdapter<SingBean.SignsBean,BaseViewHolder>(R.layout.item_sing,signsBeans) {
            @Override
            protected void convert(BaseViewHolder helper, SingBean.SignsBean item) {
                helper.setText(R.id.tv_count,"+"+item.getGold());
                helper.setImageDrawable(R.id.iv_sing,getResources().getDrawable(singBean.getSignflg()&&(singBean.getCount()-1)==helper.getAdapterPosition()?R.drawable.ic_lb_sing:R.drawable.ic_lb_nosing));
                helper.setText(R.id.tv_time,singBean.getSignflg()&&(singBean.getCount()-1)==helper.getAdapterPosition()?"今天":(item.getCday()+"天"));
            }
        };
        rvSing.setLayoutManager(new GridLayoutManager(this,7));
        rvSing.addItemDecoration(new GridSpacingItemDecoration(7,20,false));
        rvSing.setAdapter(adapter);
        taskAdapter = new BaseQuickAdapter<TaskBean, BaseViewHolder>(R.layout.item_task, taskBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, TaskBean item) {
                GlideUtil.loadImg(SingInActivity.this,helper.getView(R.id.iv_type_img),item.getIcon());
                helper.setText(R.id.tv_type_title,item.getName());
                helper.setText(R.id.tv_type_lb,item.getGold()+"乐讯币");
                ProgressBar progressBar=helper.getView(R.id.progressBarSmall);
                progressBar.setMax(item.getNumber());
                progressBar.setProgress(item.getNum());
                helper.setText(R.id.tv_success_count,item.getNum()+"");
                helper.setText(R.id.tv_all_count,"/"+item.getNumber());
                TextView tvGet=helper.getView(R.id.tv_get);
                if(item.getIs_complete()==0||item.getIs_complete()==4){
                    tvGet.setText("未完成");
                    tvGet.setBackground(getResources().getDrawable(R.drawable.shape_radiu_ff9813));
                }else if (item.getIs_complete()==1||item.getIs_complete()==3){
                    tvGet.setText("领取");
                    tvGet.setBackground(getResources().getDrawable(R.drawable.shape_radiu_ff9813));
                }else if(item.getIs_complete()==2){
                    tvGet.setText("已领取");
                    tvGet.setBackground(getResources().getDrawable(R.drawable.shape_gray_bg_15));
                }
            }
        };
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.setAdapter(taskAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.singInfo();
        presenter.getLbTask();
    }

    public void showDialog(boolean isSing,String coin){
        getDialog = new GetDialog(this, R.style.selectPicDialogstyle,isSing,coin);
        getDialog.show();
    }

    @Override
    protected void initListener() {
        super.initListener();
        taskAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (taskBeanList.get(position).getIs_complete()==0||taskBeanList.get(position).getIs_complete()==4){
                switch (taskBeanList.get(position).getId()){
                    case 1:
                        EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN_OUT));
                        MainActivity.startMainActivity(SingInActivity.this);
                        break;
                    case 2:
                        EventBus.getDefault().postSticky(new EventModel<>(EventModel.OPEN_Video));
                        MainActivity.startMainActivity(SingInActivity.this);
                        break;
                    case 3:
                        EventBus.getDefault().postSticky(new EventModel<>(EventModel.OPEN_LIVE));
                        MainActivity.startMainActivity(SingInActivity.this);
                        break;
                    case 4:
                        EventBus.getDefault().postSticky(new EventModel<>(EventModel.OPEN_Video));
                        MainActivity.startMainActivity(SingInActivity.this);
                        break;
                    case 5:
                        EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN_OUT));
                        MainActivity.startMainActivity(SingInActivity.this);
                        break;
                }
            }else if (taskBeanList.get(position).getIs_complete()==1||taskBeanList.get(position).getIs_complete()==3){
                mPosition=position;
                presenter.lbComplete(String.valueOf(taskBeanList.get(position).getId()));
            }
        });
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    public static void startSingInActivity(Context context) {
        Intent intent = new Intent(context, SingInActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_finish, R.id.tv_sing_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_sing_get:
                if (!singBean.getSignflg()){
                    presenter.userSing();
                }
                break;
        }
    }

    @Override
    public void singInfoFinish(SingBean singBean) {
        this.singBean=singBean;
        tvLb.setText(singBean.getGold());
        tvSingGet.setText(singBean.getSignflg()?"已签到": StringUtil.getString(R.string.sing_get));
        String str="已连续签到"+singBean.getSigncount()+"天";
        SpannableString spann=new SpannableString(str);
        spann.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_fc5a8e)),5,singBean.getSigncount().length()+5,SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSingCount.setText(spann);
        signsBeans.clear();
        signsBeans.addAll(singBean.getSigns());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void userInfo(String str) {
        showDialog(true,str);
        presenter.singInfo();
    }

    @Override
    public void getLbTask(List<TaskBean> list) {
        taskBeanList.clear();

        taskBeanList.addAll(list);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void lbCompleteFinish() {
        showDialog(false,String.valueOf(taskBeanList.get(mPosition).getGold()));
        presenter.getLbTask();
        presenter.singInfo();
    }
}
