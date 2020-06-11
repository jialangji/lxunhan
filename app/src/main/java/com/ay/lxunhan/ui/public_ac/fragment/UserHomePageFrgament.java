package com.ay.lxunhan.ui.public_ac.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.HeUserListContract;
import com.ay.lxunhan.presenter.HeUserListPresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.widget.SelectTypeDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.public_ac.fragment
 * @date 2020/6/5
 */
public class UserHomePageFrgament extends BaseFragment<HeUserListContract.HeUserListView, HeUserListPresenter> implements HeUserListContract.HeUserListView {

    @BindView(R.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R.id.rv_user_media)
    RecyclerView rvUserMedia;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private SelectTypeDialog selectTypeDialog;
    private int type=0;
    private int page=1;
    private boolean isRefresh=true;
    private List<UserMediaListBean> userMediaListBeans=new ArrayList<>();
    private String userID;
    private BaseQuickAdapter mediaAdapter;

    public static UserHomePageFrgament newInstance(String userid) {
        Bundle args = new Bundle();
        UserHomePageFrgament fragment = new UserHomePageFrgament();
        args.putString("id", userid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        userID=getArguments().getString("id");
        mediaAdapter = PublicAdapterUtil.getUserMediaListAdapter(userMediaListBeans,getActivity());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        rvUserMedia.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
        rvUserMedia.setLayoutManager(gridLayoutManager);
        rvUserMedia.setAdapter(mediaAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (userMediaListBeans.get(i).getItemType() == 5) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHeUserMeidaList(userID,page,type);
    }

    @Override
    public HeUserListPresenter initPresenter() {
        return new HeUserListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_home_page;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    @OnClick(R.id.tv_select_type)
    public void onViewClicked() {
        showDialog();

    }

    @Override
    protected void initListener() {
        super.initListener();
        mediaAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    switch (userMediaListBeans.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(getActivity(),userMediaListBeans.get(position).getType(),userMediaListBeans.get(position).getId());
                            break;
                        case 2:
                            VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(userMediaListBeans.get(position).getId()));
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(getActivity(), userMediaListBeans.get(position).getType(), userMediaListBeans.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(getActivity(), userMediaListBeans.get(position).getId());
                            break;
                        case 5:

                            break;
                    }
                    break;
                case R.id.tv_quiz:
                    break;
            }
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh=true;
                page=1;
                presenter.getHeUserMeidaList(userID,page,type);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==userMediaListBeans.size()){
                    isRefresh=false;
                    page=page+1;
                    presenter.getHeUserMeidaList(userID,page,type);
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    public void showDialog(){
        if (selectTypeDialog==null){
            selectTypeDialog = new SelectTypeDialog(getActivity(), R.style.selectPicDialogstyle);
        }
        selectTypeDialog.show();
        selectTypeDialog.setItemClickListener((position, typeBean) -> {
            type=typeBean.getType();
            tvSelectType.setText(typeBean.getName());
            page=1;
            presenter.getHeUserMeidaList(userID,page,type);
            selectTypeDialog.dismiss();
        });

    }

    @Override
    public void getHeUserMeidaListFinish(List<UserMediaListBean> listBeans) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            userMediaListBeans.clear();
            userMediaListBeans.addAll(listBeans);
        }else{
            swipeRefresh.finishRefreshing();
            userMediaListBeans.addAll(listBeans);
        }
        mediaAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHeUserListFinish(List<PyqBean> list) {

    }

    @Override
    public void pyqDeleteFinish() {
        ToastUtil.makeShortText(getActivity(),"删除成功");
    }
}
