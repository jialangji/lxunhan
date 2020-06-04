package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.contract.PyqContract;
import com.ay.lxunhan.presenter.PyqPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PyqActivity extends BaseActivity<PyqContract.PyqView, PyqPresenter> implements PyqContract.PyqView {

    @BindView(R.id.rv_pyq)
    RecyclerView rvPyq;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<PyqBean> pyqBeans=new ArrayList<>();
    private BaseQuickAdapter pyqAdapter;
    private int page=1;
    private boolean isRefresh=true;


    @Override
    public PyqPresenter initPresenter() {
        return new PyqPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        pyqAdapter = PublicAdapterUtil.getPyqAdpater(pyqBeans,this);
        rvPyq.setLayoutManager(new LinearLayoutManager(this));
        rvPyq.setAdapter(pyqAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page=1;
                isRefresh=true;
                presenter.getPyqList(page);
            }
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == pyqBeans.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getPyqList(page);
                }else {
                    swipeRefresh.finishLoadmore();
                    ToastUtil.makeShortText(PyqActivity.this,"暂无更多数据");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pyq;
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

    public static void startPyqActivity(Context context){
        Intent intent=new Intent(context,PyqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getPyqFinish(List<PyqBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            pyqBeans.clear();
            pyqBeans.addAll(list);
        }else {
            swipeRefresh.finishLoadmore();
            pyqBeans.addAll(list);
        }
        pyqAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }
}
