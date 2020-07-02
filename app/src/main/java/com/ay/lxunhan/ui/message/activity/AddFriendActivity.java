package com.ay.lxunhan.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.contract.AddFriendContract;
import com.ay.lxunhan.presenter.AddFriendPresneter;
import com.ay.lxunhan.ui.public_ac.activity.SearchActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendActivity extends BaseActivity<AddFriendContract.AddFriendView, AddFriendPresneter> implements AddFriendContract.AddFriendView {

    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rv_hot_people)
    RecyclerView rvHotPeople;
    @BindView(R.id.tv_attention_all)
    TextView tvAttentionAll;
    @BindView(R.id.rv_friends)
    RecyclerView rvFriends;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<AddFriendBean.HotPeopleBean> peopleBeans = new ArrayList<>();
    private List<AddFriendBean.FollowsListBean> friendsBean = new ArrayList<>();
    private BaseQuickAdapter peopleAdapter;
    private BaseQuickAdapter friendAdapter;
    private int page=1;
    private boolean isRefresh=true;
    private boolean noPeople=false;



    @Override
    protected void initView() {
        super.initView();
        peopleAdapter = new BaseQuickAdapter<AddFriendBean.HotPeopleBean, BaseViewHolder>(R.layout.item_hot_people, peopleBeans) {
            @Override
            protected void convert(BaseViewHolder helper, AddFriendBean.HotPeopleBean item) {
                GlideUtil.loadCircleImgForHead(AddFriendActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_name, item.getNickname());
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHotPeople.setLayoutManager(linearLayoutManager);
        rvHotPeople.setAdapter(peopleAdapter);
        friendAdapter = new BaseQuickAdapter<AddFriendBean.FollowsListBean, BaseViewHolder>(R.layout.item_add_friend,friendsBean) {
            @Override
            protected void convert(BaseViewHolder helper, AddFriendBean.FollowsListBean item) {
                GlideUtil.loadCircleImgForHead(AddFriendActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                helper.setText(R.id.tv_attention, item.getIs_fol() != 2 && item.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
                helper.setText(R.id.tv_signature, item.getAutograph());
                helper.addOnClickListener(R.id.tv_attention);

            }
        };
        rvFriends.setLayoutManager(new LinearLayoutManager(this));
        rvFriends.setAdapter(friendAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getAddFriend("",page);
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
                presenter.getAddFriend("",page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == friendsBean.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getAddFriend("",page);

                }else {
                    swipeRefresh.finishLoadmore();
                    ToastUtil.makeShortText(AddFriendActivity.this,"暂无更多数据");
                }
            }
        });
        friendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_attention:
                    presenter.attention(friendsBean.get(position).getUid());
                    break;
            }
        });
    }

    @Override
    public AddFriendPresneter initPresenter() {
        return new AddFriendPresneter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    public static void startAddFriendActivity(Context context) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_finish, R.id.rl_search, R.id.tv_attention_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_search:
                SearchActivity.startSearchActivity(this, Contacts.HISTORY_ADD_FRIEND);
                break;
            case R.id.tv_attention_all:
                if (noPeople){
                    List<String> str=new ArrayList<>();
                    for (AddFriendBean.HotPeopleBean peopleBean : peopleBeans) {
                        str.add(peopleBean.getUid());
                    }
                    presenter.attentions(str);
                }
                break;
        }
    }

    @Override
    public void getAddFriendFinish(AddFriendBean addFriendBean) {
        if (addFriendBean.getHot_people()!=null&&addFriendBean.getHot_people().size()>0){
            noPeople=true;
        }else {
            tvAttentionAll.setText("暂无可关注用户");
        }
        peopleBeans.clear();
        peopleBeans.addAll(addFriendBean.getHot_people());
        if (isRefresh){
            friendsBean.clear();
            swipeRefresh.finishRefreshing();
            friendsBean.addAll(addFriendBean.getFollows_list());
        }else{
            swipeRefresh.finishLoadmore();
            friendsBean.addAll(addFriendBean.getFollows_list());
        }
        peopleAdapter.notifyDataSetChanged();
        friendAdapter.notifyDataSetChanged();
    }

    @Override
    public void attentionsFinish() {
       swipeRefresh.onRefresh(swipeRefresh);
    }

    @Override
    public void attentionFinish() {
        swipeRefresh.onRefresh(swipeRefresh);
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
