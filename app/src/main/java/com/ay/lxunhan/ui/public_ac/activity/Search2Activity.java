package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.HotSearchBean;
import com.ay.lxunhan.contract.Search2Contract;
import com.ay.lxunhan.presenter.Search2Presenter;
import com.ay.lxunhan.ui.public_ac.SearchResultActivity;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.db.HistoryDao;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Search2Activity extends BaseActivity<Search2Contract.Search2View, Search2Presenter> implements Search2Contract.Search2View {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.clear_history)
    TextView clearHistory;
    private BaseQuickAdapter hotAdapter;
    private BaseQuickAdapter historyAdapter;
    private List<String> historyList = new ArrayList<>();
    private List<HotSearchBean> hotList = new ArrayList<>();
    private boolean isKeyboardShowed = true; // 是否显示键盘

    @Override
    protected void initView() {
        super.initView();
        initTextEdit();
        historyList = HistoryDao.getInstance().selectHistory();
        if (historyList.size()>0){
            clearHistory.setVisibility(View.VISIBLE);
        }
        hotAdapter = new BaseQuickAdapter<HotSearchBean,BaseViewHolder>(R.layout.item_hot,hotList) {
            @Override
            protected void convert(BaseViewHolder helper, HotSearchBean item) {
                if (helper.getAdapterPosition()==0||helper.getAdapterPosition()==1||helper.getAdapterPosition()==2){
                    helper.setTextColor(R.id.tv_item,getResources().getColor(R.color.color_fc5a8e));
                }else {
                    helper.setTextColor(R.id.tv_item,getResources().getColor(R.color.color_9));
                }
                helper.setText(R.id.tv_item,(helper.getAdapterPosition()+1)+"");
                helper.setText(R.id.tv_text,item.getName());

            }
        };
        rvHot.setLayoutManager(new GridLayoutManager(this,2));
        rvHot.setAdapter(hotAdapter);
        historyAdapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_histroy,historyList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_text,item);
            }
        };
        rvHistory.setLayoutManager(new GridLayoutManager(this,2));
        rvHistory.setAdapter(historyAdapter);
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getHot();
    }

    @Override
    protected void initListener() {
        super.initListener();
        hotAdapter.setOnItemClickListener((adapter, view, position) -> {
            HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
            etSearch.setText(hotList.get(position).getName());
            SearchResultActivity.startSearchResultActivity(Search2Activity.this,hotList.get(position).getName());
            refreshHistory();
        });
        historyAdapter.setOnItemClickListener((adapter, view, position) -> {
            HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
            etSearch.setText(historyList.get(position));
            SearchResultActivity.startSearchResultActivity(Search2Activity.this,historyList.get(position));
            refreshHistory();
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String searchName = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchName)) {
                    HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
                    SearchResultActivity.startSearchResultActivity(Search2Activity.this,StringUtil.getFromEdit(etSearch));
                    refreshHistory();
                    return true;
                }
            }
            return false;
        });

    }

    private void refreshHistory(){
        historyList.clear();
        historyList.addAll(HistoryDao.getInstance().selectHistory());
        if (historyList.size()>0){
            clearHistory.setVisibility(View.VISIBLE);
        }else{
            clearHistory.setVisibility(View.GONE);
        }
        historyAdapter.notifyDataSetChanged();
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
        editTextMessage.requestFocus();
        //如果已经显示,则继续操作时不需要把光标定位到最后
        if (!isKeyboardShowed) {
            editTextMessage.setSelection(editTextMessage.getText().length());
            isKeyboardShowed = true;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextMessage, 0);
    }

    @OnClick({R.id.rl_finish, R.id.iv_edit, R.id.clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.iv_edit:
                HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
                SearchResultActivity.startSearchResultActivity(this,StringUtil.getFromEdit(etSearch));
                break;
            case R.id.clear_history:
                HistoryDao.getInstance().deleteAll();
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
                break;
        }
    }

    public static void startSearch2Activity(Context context){
        Intent intent=new Intent(context,Search2Activity.class);
        context.startActivity(intent);
    }

    @Override
    public Search2Presenter initPresenter() {
        return new Search2Presenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search2;
    }

    @Override
    protected int getBarColor() {
        return R.color.bg_color;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @Override
    public void getHotFinish(List<HotSearchBean> beans) {
        hotList.clear();
        hotList.addAll(beans);
        hotAdapter.notifyDataSetChanged();
    }
}
