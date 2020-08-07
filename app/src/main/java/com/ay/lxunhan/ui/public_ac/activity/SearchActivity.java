package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.LiveListBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.SearchContract;
import com.ay.lxunhan.presenter.SearchPresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.live.LiveRoomActivity;
import com.ay.lxunhan.ui.message.activity.ChatP2PActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.ShareUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.db.HistoryDao;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.ShareDialog;
import com.ay.lxunhan.widget.ShareImgDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchContract.SearchView, SearchPresenter> implements SearchContract.SearchView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_linear_history)
    LinearLayout llLinearHistory;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.fl_history)
    FlowLayout flHistory;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private String type;
    private boolean isKeyboardShowed = true; // 是否显示键盘
    private List<String> historyList = new ArrayList<>();
    private FlowLayoutAdapter histroyAdpater;
    private List<MultiItemBaseBean> homeList = new ArrayList<>();
    private List<AddFriendBean.FollowsListBean> addFriendsBean = new ArrayList<>();
    private List<FriendBean> friendBeans=new ArrayList<>();
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private List<LiveListBean> liveListBeans = new ArrayList<>();
    private BaseQuickAdapter searchHomeAdapter;
    private BaseQuickAdapter searchVideoAdapter;
    private BaseQuickAdapter searchLiveAdapter;
    private BaseQuickAdapter searchFriendAdapter;
    private BaseQuickAdapter searchAddFriendAdapter;
    private int mPosition;
    private ShareDialog shareDialog;
    private int page=1;
    private boolean isRefresh=true;
    private String content;
    private ShareImgDialog shareImgDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getStringExtra(Contacts.HISTORY);
        initTextEdit();
        historyList = HistoryDao.getInstance().selectHistory(type);
        histroyAdpater = new FlowLayoutAdapter<String>(historyList) {
            @Override
            public void bindDataToView(ViewHolder viewHolder, int i, String s) {
                viewHolder.setText(R.id.tv_text, s);
            }

            @Override
            public void onItemClick(int i, String s) {
                content=s;
                setRefresh(content);
                etSearch.setText(content);
                HistoryDao.getInstance().updateOrder(content,type);
            }

            @Override
            public int getItemLayoutID(int i, String s) {
                return R.layout.item_histroy;
            }
        };
        flHistory.setAdapter(histroyAdpater);
        initAdapter();
        showDialog();
    }

    private void setRefresh(String content){
        page=1;
        isRefresh=true;
        switch (type) {
            case Contacts.HISTORY_HOME:
                 presenter.getHomeSearch(content,page);
                break;
            case Contacts.HISTORY_VIDEO:
                presenter.getVideoSearch(content,page);
                break;
            case Contacts.HISTORY_LIVE:
                presenter.getLiveSearch("0",page,content);
                break;
            case Contacts.HISTORY_FRIEND:
                presenter.getFriendSearch(content);
                break;
            case Contacts.HISTORY_ADD_FRIEND:
                presenter.getAddFriendSearch(content,page);
                break;
        }

    }

    private void refreshHistory(){
        historyList = HistoryDao.getInstance().selectHistory(type);
        histroyAdpater.clear();
        histroyAdpater.addAll(historyList);
        histroyAdpater.notifyDataSetChanged();
    }

    private void initAdapter() {
        switch (type) {
            case Contacts.HISTORY_HOME:
                searchHomeAdapter = PublicAdapterUtil.getAdapter(homeList, this,true);
                rvSearch.setLayoutManager(new LinearLayoutManager(this));
                rvSearch.setAdapter(searchHomeAdapter);
                break;
            case Contacts.HISTORY_VIDEO:
                searchVideoAdapter = PublicAdapterUtil.getVideoAdapter(videoBeanList, this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                rvSearch.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
                rvSearch.setLayoutManager(gridLayoutManager);
                rvSearch.setAdapter(searchVideoAdapter);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int i) {
                        if (videoBeanList.get(i).getItemType() == 5) {
                            return 1;
                        } else {
                            return 2;
                        }
                    }
                });
                break;
            case Contacts.HISTORY_LIVE:
                searchLiveAdapter = new BaseQuickAdapter<LiveListBean,BaseViewHolder>(R.layout.item_live,liveListBeans) {

                    @Override
                    protected void convert(BaseViewHolder helper, LiveListBean item) {
                        GlideUtil.loadRoundImg(SearchActivity.this,helper.getView(R.id.iv_cover),item.getCover());
                        helper.setText(R.id.tv_title,item.getLname());
                        helper.setText(R.id.tv_see_count,item.getPeople()+"");
                    }
                };
                rvSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
                rvSearch.addItemDecoration(new GridSpacingItemDecoration(2,10,false));
                rvSearch.setAdapter(searchLiveAdapter);
                break;
            case Contacts.HISTORY_FRIEND:
                searchFriendAdapter =new BaseQuickAdapter<FriendBean,BaseViewHolder>(R.layout.item_friend_list,friendBeans) {
                    @Override
                    protected void convert(BaseViewHolder helper, FriendBean item) {
                        GlideUtil.loadCircleImgForHead(SearchActivity.this,helper.getView(R.id.iv_header),item.getAvatar());
                        helper.setText(R.id.tv_name,item.getNickname());
                        helper.setVisible(R.id.iv_v,item.getIs_media());
                        helper.setImageDrawable(R.id.iv_sex,getResources().getDrawable(item.getSex()?R.drawable.ic_man:R.drawable.ic_woman));
                    }
                };
                rvSearch.setLayoutManager(new LinearLayoutManager(this));
                rvSearch.setAdapter(searchFriendAdapter);
                break;
            case Contacts.HISTORY_ADD_FRIEND:
                searchAddFriendAdapter = new BaseQuickAdapter<AddFriendBean.FollowsListBean, BaseViewHolder>(R.layout.item_add_friend,addFriendsBean) {
                    @Override
                    protected void convert(BaseViewHolder helper, AddFriendBean.FollowsListBean item) {
                        GlideUtil.loadCircleImgForHead(SearchActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                        helper.setGone(R.id.iv_v, item.getIs_media());
                        helper.setText(R.id.tv_name, item.getNickname());
                        helper.setImageResource(R.id.iv_sex, item.getSex() ? R.drawable.ic_man : R.drawable.ic_woman);
                        helper.setText(R.id.tv_attention, item.getIs_fol() != 2 && item.getIs_fol() == 1 ? StringUtil.getString(R.string.attention_to) : StringUtil.getString(R.string.add_attention));
                        helper.setText(R.id.tv_signature, item.getAutograph());
                        helper.addOnClickListener(R.id.tv_attention);

                    }
                };
                rvSearch.setLayoutManager(new LinearLayoutManager(this));
                rvSearch.setAdapter(searchAddFriendAdapter);
                break;
        }


    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (!TextUtils.isEmpty(content)){
                    setRefresh(content);
                }
                if (type.equals(Contacts.HISTORY_FRIEND)){
                    swipeRefresh.finishRefreshing();
                }

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                switch (type){
                    case Contacts.HISTORY_HOME:
                        if (Contacts.LIMIT*page==homeList.size()){
                            page=page++;
                            isRefresh=false;
                            presenter.getHomeSearch(content,page);
                        }
                        break;
                    case Contacts.HISTORY_VIDEO:
                        if (Contacts.LIMIT*page==videoBeanList.size()){
                            page=page++;
                            isRefresh=false;
                            presenter.getVideoSearch(content,page);
                        }

                        break;
                    case Contacts.HISTORY_LIVE:

                        break;
                    case Contacts.HISTORY_FRIEND:
                        swipeRefresh.finishLoadmore();
                        break;
                    case Contacts.HISTORY_ADD_FRIEND:
                        if (Contacts.LIMIT*page==addFriendsBean.size()){
                            page=page++;
                            isRefresh=false;
                            presenter.getAddFriendSearch(content,page);
                        }
                        break;
                }
            }
        });

        switch (type){
            case Contacts.HISTORY_HOME:
                searchHomeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.iv_header:
                            FriendDetailActivity.startUserDetailActivity(this, homeList.get(position).getUid());
                            break;
                        case R.id.ll_linear:
                            switch (homeList.get(position).getType()) {
                                case 1:
                                    HomeDetailActivity.startHomeDetailActivity(this, homeList.get(position).getType(), homeList.get(position).getId());
                                    break;
                                case 3:
                                    HomeAskDetailActivity.startHomeAskDetailActivity(this, homeList.get(position).getType(), homeList.get(position).getId());
                                    break;
                                case 4:
                                    HomeQuziDetailActivity.startHomeQuizDetailActivity(this, homeList.get(position).getId());
                                    break;
                            }
                            break;
                        case R.id.tv_attention:
                            if (isLogin()) {
                                mPosition = position;
                                presenter.attention(homeList.get(mPosition).getUid());
                            }
                            break;
                        case R.id.tv_quiz://投票
                            if (isLogin()) {
                                if (!homeList.get(position).getIs_pate()) {
                                    mPosition = position;
                                    int oid = -1;
                                    for (MultiItemBaseBean.OptionListBean optionListBean : homeList.get(position).getOption_list()) {
                                        if (optionListBean.isUserIsSelect()) {
                                            oid = optionListBean.getId();
                                        }
                                    }
                                    SendCommentModel quizModel = new SendCommentModel(homeList.get(position).getId(), oid);
                                    presenter.quiz(quizModel);
                                }
                            }


                            break;
                    }
                });
                break;
            case Contacts.HISTORY_VIDEO:
                searchVideoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.iv_header:
                            FriendDetailActivity.startUserDetailActivity(this,videoBeanList.get(position).getUid());
                            break;
                        case R.id.ll_linear:
                            if (videoBeanList.get(position).getItemType() == 5) {
                                SmallVideoActivity.startSmallVideoActivity(this,videoBeanList.get(position).getId());
                            } else {
                                VideoDetailActivity.startVideoDetailActivity(this, String.valueOf(videoBeanList.get(position).getId()));
                            }
                            break;
                        case R.id.tv_attention:
                            if (isLogin()){
                                mPosition = position;
                                presenter.attention(videoBeanList.get(position).getUid());
                            }
                            break;
                        case R.id.rl_more:
                            if (isLogin()){
                                mPosition = position;
                                shareDialog.show();
                            }

                            break;
                    }
                });
                break;
            case Contacts.HISTORY_LIVE:
                searchLiveAdapter.setOnItemClickListener((adapter, view, position) -> LiveRoomActivity.startAudience(SearchActivity.this,liveListBeans.get(position).getRoomcode(),liveListBeans.get(position).getHttpPullUrl(),true,liveListBeans.get(position).getLid()));

                break;
            case Contacts.HISTORY_FRIEND:
                searchFriendAdapter.setOnItemClickListener((adapter, view, position) -> ChatP2PActivity.startChat(SearchActivity.this,friendBeans.get(position).getUqid()));
                break;
            case Contacts.HISTORY_ADD_FRIEND:
                searchAddFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.tv_attention:
                            mPosition=position;
                            presenter.attention(addFriendsBean.get(position).getUid());
                            break;
                    }
                });
                break;
        }
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String searchName = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchName)) {
                    content=searchName;
                    HistoryDao.getInstance().updateOrder(content,type);
                    setRefresh(content);
                    return true;
                }
            }
            return false;
        });
    }

    public void showDialog() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(this, R.style.selectPicDialogstyle);
        }

        shareDialog.setItemClickListener(new ShareDialog.ItemClickListener() {
            @Override
            public void shareFriends() {

            }

            @Override
            public void shareQQ() {
                ShareUtils.shareToQQ(SearchActivity.this,videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareQQRoom() {
                ShareUtils.shareToQQRoom(SearchActivity.this,videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWx() {
                ShareUtils.shareToWx(SearchActivity.this,videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWxPyq() {
                ShareUtils.shareToWxPyq(SearchActivity.this,videoBeanList.get(mPosition).getShare_url());

            }

            @Override
            public void shareWb() {

            }

            @Override
            public void shareImg() {
                showImg(videoBeanList.get(mPosition).getPiiic_url());
            }

            @Override
            public void copyUrl() {
                StringUtil.copyString(videoBeanList.get(mPosition).getShare_url());
            }

            @Override
            public void complaint() {
                ComplaintActivity.startComplaintActivity(SearchActivity.this, String.valueOf(videoBeanList.get(mPosition).getId()), 2);

            }

            @Override
            public void collect() {
                presenter.addCollect(String.valueOf(videoBeanList.get(mPosition).getId()),2);
            }

            @Override
            public void cancel() {

            }
        });
    }

    public void showImg(String url){
        if (shareImgDialog == null) {
            shareImgDialog = new ShareImgDialog(this, R.style.selectPicDialogstyle,url);
        }
        shareImgDialog.show();
        shareImgDialog.setItemClickListener(new ShareImgDialog.ItemClickListener() {
            @Override
            public void shareQQ(String bitmap) {
                ShareUtils.shareToQQImg(SearchActivity.this,bitmap);

            }

            @Override
            public void shareWx() {

            }

            @Override
            public void shareWb(Bitmap bitmap) {

            }
        });
    }

    private void initTextEdit() {
        etSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showInputMethod(etSearch);
            }
            return false;
        });

    }

    // 显示键盘布局
    private void showInputMethod(EditText editTextMessage) {
        swipeRefresh.setVisibility(View.GONE);
        llLinearHistory.setVisibility(View.VISIBLE);
        editTextMessage.requestFocus();
        //如果已经显示,则继续操作时不需要把光标定位到最后
        if (!isKeyboardShowed) {
            editTextMessage.setSelection(editTextMessage.getText().length());
            isKeyboardShowed = true;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextMessage, 0);
    }


    @Override
    protected int getBarColor() {
        return R.color.bg_color;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick(R.id.iv_edit)
    public void onViewClicked() {
        finish();
    }

    public static void startSearchActivity(Context context, String type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(Contacts.HISTORY, type);
        context.startActivity(intent);
    }

    @Override
    public void getHomeSearchFinish(List<MultiItemBaseBean> bean) {
        llLinearHistory.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            homeList.clear();
            homeList.addAll(bean);
            searchHomeAdapter.setNewData(homeList);
        } else {
            swipeRefresh.finishLoadmore();
            homeList.addAll(bean);
            searchHomeAdapter.setNewData(homeList);
        }
        refreshHistory();
    }

    @Override
    public void getVideoHomeListFinish(List<VideoBean> list) {
        llLinearHistory.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            videoBeanList.clear();
            videoBeanList.addAll(list);
            searchVideoAdapter.setNewData(videoBeanList);
        } else {
            swipeRefresh.finishLoadmore();
            videoBeanList.addAll(list);
            searchVideoAdapter.setNewData(videoBeanList);
        }
        refreshHistory();
    }

    @Override
    public void getAddFriendFinish(AddFriendBean list) {
        llLinearHistory.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            addFriendsBean.clear();
            addFriendsBean.addAll(list.getFollows_list());
            searchAddFriendAdapter.setNewData(addFriendsBean);
        } else {
            swipeRefresh.finishLoadmore();
            addFriendsBean.addAll(list.getFollows_list());
            searchAddFriendAdapter.setNewData(addFriendsBean);
        }
        refreshHistory();
    }

    @Override
    public void getFriendsFinish(List<FriendBean> list) {
        llLinearHistory.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        friendBeans.clear();
        friendBeans.addAll(list);
        searchFriendAdapter.notifyDataSetChanged();
        refreshHistory();
    }

    @Override
    public void getLiveSearchFinish(List<LiveListBean> beans) {
        llLinearHistory.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            liveListBeans.clear();
            liveListBeans.addAll(beans);
            searchLiveAdapter.setNewData(addFriendsBean);
        } else {
            swipeRefresh.finishLoadmore();
            liveListBeans.addAll(beans);
            searchLiveAdapter.setNewData(addFriendsBean);
        }
        refreshHistory();

    }

    @Override
    public void addCollectFinish() {
        ToastUtil.makeShortText(this,"收藏成功");
    }


    @Override
    public void attentionFinish() {
        setRefresh(content);
    }

    @Override
    public void quziFinish() {
        setRefresh(content);
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
