package com.ay.lxunhan.ui.public_ac.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.HeUserListContract;
import com.ay.lxunhan.presenter.HeUserListPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.public_ac.fragment
 * @date 2020/6/5
 */
public class UserDynamicFrgament extends BaseFragment<HeUserListContract.HeUserListView, HeUserListPresenter> implements HeUserListContract.HeUserListView {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page=1;
    private boolean isRefresh=true;
    private List<PyqBean> pyqBeanList=new ArrayList<>();
    private String userID;
    private BaseQuickAdapter adapter;


    public static UserDynamicFrgament newInstance(String userid) {
        Bundle args = new Bundle();
        UserDynamicFrgament fragment = new UserDynamicFrgament();
        args.putString("id", userid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        userID = Objects.requireNonNull(getArguments()).getString("id");
        adapter = PublicAdapterUtil.getUserSelfPyqAdapter(pyqBeanList,getActivity());
        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideo.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHeUserList(userID,page);
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
                presenter.getHeUserList(userID,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==pyqBeanList.size()){
                    page=page+1;
                    isRefresh=false;
                    presenter.getHeUserList(userID,page);
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    public HeUserListPresenter initPresenter() {
        return new HeUserListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_refresh;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    public void getHeUserMeidaListFinish(List<UserMediaListBean> listBeans) {

    }

    @Override
    public void getHeUserListFinish(List<PyqBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            pyqBeanList.clear();
            pyqBeanList.addAll(list);
        }else{
            swipeRefresh.finishLoadmore();
            pyqBeanList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }
}
