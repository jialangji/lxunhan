package com.ay.lxunhan.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.contract.FriendContract;
import com.ay.lxunhan.presenter.FriendsPresenter;
import com.ay.lxunhan.ui.message.adapter.SelectFriendAdapter;
import com.ay.lxunhan.ui.public_ac.activity.SearchActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.widget.azlist.AZItemEntity;
import com.ay.lxunhan.widget.azlist.AZSideBarView;
import com.ay.lxunhan.widget.azlist.AZTitleDecoration;
import com.ay.lxunhan.widget.azlist.DataUtils;
import com.ay.lxunhan.widget.azlist.LettersComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendActivity extends BaseActivity<FriendContract.FriendsView, FriendsPresenter> implements FriendContract.FriendsView {

    @BindView(R.id.recyler)
    RecyclerView recyler;
    @BindView(R.id.bar_list)
    AZSideBarView barList;

    private List<AZItemEntity<FriendBean>> data=new ArrayList<>();
    private SelectFriendAdapter selectFriendAdapter;

    @Override
    public FriendsPresenter initPresenter() {
        return new FriendsPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        selectFriendAdapter = new SelectFriendAdapter(this,data);
        recyler.setLayoutManager(new LinearLayoutManager(this));
        recyler.addItemDecoration(new AZTitleDecoration(new AZTitleDecoration.TitleAttributes(this)));
        recyler.setAdapter(selectFriendAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        barList.setOnLetterChangeListener(letter -> {
            int position = selectFriendAdapter.getSortLettersFirstPosition(letter);
            if (position != -1 && null != recyler.getLayoutManager()) {
                if (recyler.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyler.getLayoutManager();
                    manager.scrollToPositionWithOffset(position, 0);
                } else {
                    recyler.getLayoutManager().scrollToPosition(position);
                }
            }
        });
        selectFriendAdapter.setOnItemClickListener(countryCode -> {
            ChatP2PActivity.startChat(this,countryCode.getUqid());
        });
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getFriendsList("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_search:
                SearchActivity.startSearchActivity(this, Contacts.HISTORY_FRIEND);
                break;
        }
    }

    @Override
    public void getFriendsFinish(List<FriendBean> friendBeans) {
        data.clear();
        data.addAll(DataUtils.fillData(friendBeans));
        Collections.sort(data, new LettersComparator());
        selectFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void startFriendActivity(Context context){
        Intent intent=new Intent(context,FriendActivity.class);
        context.startActivity(intent);
    }
}
