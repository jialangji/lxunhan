package com.ay.lxunhan.ui.video.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.contract.VideoTypeContract;
import com.ay.lxunhan.presenter.VideoTypePresenter;
import com.ay.lxunhan.ui.public_ac.activity.ComplaintActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.widget.RecyclerItemDecoration;
import com.ay.lxunhan.widget.ShareDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JzvdStd;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.video.fragment
 * @date 2020/5/29
 */
public class VideoTypeFragment extends BaseFragment<VideoTypeContract.VideoTypeView, VideoTypePresenter> implements VideoTypeContract.VideoTypeView {
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page = 1;
    private boolean isRefresh=true;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private BaseQuickAdapter videoAdapter;
    private int mPosition;
    private ShareDialog shareDialog;
    private String id;
    public static VideoTypeFragment newInstance(String id) {
        Bundle args = new Bundle();
        VideoTypeFragment fragment = new VideoTypeFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public VideoTypePresenter initPresenter() {
        return new VideoTypePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_refresh;
    }

    @Override
    protected void initView() {
        super.initView();
        assert getArguments() != null;
        id=getArguments().getString("id");
        videoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, getActivity());
        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideo.addItemDecoration(new RecyclerItemDecoration(10,1));
        rvVideo.setAdapter(videoAdapter);
        showDialog();
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getVideoType(id,page);

    }
    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        JzvdStd.goOnPlayOnResume();
    }
    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh=true;
                page=1;
                presenter.getVideoType(id,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == videoBeanList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getVideoType(id,page);

                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        videoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_line:
                    VideoDetailActivity.startVideoDetailActivity(getActivity(), String.valueOf(videoBeanList.get(position).getId()));

                    break;
                case R.id.tv_attention:
                    if (isLogin()){
                        mPosition=position;
                        presenter.attention(videoBeanList.get(position).getUid());
                    }

                    break;
                case R.id.rl_more:
                    if (isLogin()){
                        mPosition=position;
                        shareDialog.show();
                    }

                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(getActivity(),videoBeanList.get(position).getUid());
                    break;
            }
        });
    }
    public void showDialog() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(getActivity(), R.style.selectPicDialogstyle);
        }

        shareDialog.setItemClickListener(new ShareDialog.ItemClickListener() {
            @Override
            public void shareFriends() {

            }

            @Override
            public void shareQQ() {

            }

            @Override
            public void shareQQRoom() {

            }

            @Override
            public void shareWx() {

            }

            @Override
            public void shareWxPyq() {

            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {

            }

            @Override
            public void copyUrl() {

            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(getActivity(), String.valueOf(videoBeanList.get(mPosition).getId()), 2);

            }

            @Override
            public void collect() {

            }

            @Override
            public void cancel() {

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

    @Override
    public void getVideoTypeFinish(List<VideoBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            videoBeanList.clear();
        }else{
            swipeRefresh.finishLoadmore();
        }
        videoBeanList.addAll(list);
        videoAdapter.setNewData(videoBeanList);
    }

    @Override
    public void attentionFinish() {
        if (videoBeanList.get(mPosition).getIs_fol()==1){
            videoBeanList.get(mPosition).setIs_fol(0);
        }else {
            videoBeanList.get(mPosition).setIs_fol(1);
        }
        videoAdapter.notifyDataSetChanged();
    }
}
