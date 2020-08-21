package com.ay.lxunhan.ui.public_ac;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.ui.public_ac.fragment.SearchResultFragment;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.db.HistoryDao;
import com.ay.lxunhan.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tl_label)
    TabLayout tlLabel;
    @BindView(R.id.view_page_search)
    NoScrollViewPager viewPage;
    private List<String> arr=new ArrayList<>();
    private FragmentPagerAdapter vpAdapter;
    private String mContent;
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        mContent=getIntent().getStringExtra("content");
        etSearch.setText(mContent);
        arr.add("综合");
        arr.add("资讯");
        arr.add("视频");
        arr.add("韩乐讯号");
        loadVp();
    }

    @Override
    protected void initListener() {
        super.initListener();
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String searchName = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchName)) {
                    HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
                    EventBus.getDefault().postSticky(new EventModel<String>(EventModel.SEARCH,StringUtil.getFromEdit(etSearch)));
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected int getBarColor() {
        return R.color.bg_color;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.iv_edit:
                HistoryDao.getInstance().updateOrder(StringUtil.getFromEdit(etSearch));
                EventBus.getDefault().postSticky(new EventModel<String>(EventModel.SEARCH,StringUtil.getFromEdit(etSearch)));
                break;
        }
    }

    public static void startSearchResultActivity(Context context,String content){
        Intent intent=new Intent(context,SearchResultActivity.class);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }

    public void loadVp() {
        vpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SearchResultFragment.newInstance(position,mContent);
            }

            @Override
            public int getCount() {
                return arr.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return arr.get(position);
            }
        };
        viewPage.setAdapter(vpAdapter);
        viewPage.setNoScroll(false);
        tlLabel.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_fc5a8e));
        tlLabel.setupWithViewPager(viewPage);
        tlLabel.setTabMode(TabLayout.MODE_FIXED);
    }
}
