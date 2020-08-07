package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.contract.IssueContract;
import com.ay.lxunhan.http.HttpMethods;
import com.ay.lxunhan.presenter.IssuePresenter;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.SelectPicUtil;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.SelectGridPopItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IssueActivity extends BaseActivity<IssueContract.IssueView, IssuePresenter> implements IssueContract.IssueView {

    private static final int MAX_NUM = 500;
    private static final int CHOSE_IMG = -101;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_et_num)
    TextView tvEtNum;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    private BaseQuickAdapter imgAdapter;
    private List<String> pathList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public IssuePresenter initPresenter() {
        return new IssuePresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        pathList.add("0");
        imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_issue_img, pathList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (item.equals("0")) {
                    helper.setGone(R.id.iv_del, false);
                    GlideUtil.loadImgLocal(IssueActivity.this, helper.getView(R.id.iv_img), R.drawable.ic_add_img);
                } else {
                    helper.setGone(R.id.iv_del, true);
                    GlideUtil.loadImg(IssueActivity.this, helper.getView(R.id.iv_img), item);
                }
                helper.addOnClickListener(R.id.iv_del);
                helper.addOnClickListener(R.id.iv_img);
            }
        };
        rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        rvImg.addItemDecoration(new SelectGridPopItemDecoration(DisplayUtil.dip2px(this, 10), DisplayUtil.dip2px(this, 10), DisplayUtil.dip2px(this, 10), DisplayUtil.dip2px(this, 10)));
        rvImg.setAdapter(imgAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_NUM) {
                    editable.delete(MAX_NUM, editable.length());
                }
                tvEtNum.setText(String.format("%s/%d", String.valueOf(editable.length()), MAX_NUM));
            }
        });

        imgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_img:
                    if (pathList.get(position).equals("0")) {
                        SelectPicUtil.selectPic(IssueActivity.this, PictureConfig.CHOOSE_REQUEST, selectList);
                    }
                    break;
                case R.id.iv_del:
                    if (!pathList.get(position).equals("0")) {
                        selectList.remove(position);
                    }
                    pathList.clear();
                    for (LocalMedia localMedia : selectList) {
                        pathList.add(localMedia.getCompressPath());
                    }
                    pathList.size();
                    if (pathList.size() < 9) {
                        pathList.add("0");
                    }
                    imgAdapter.setNewData(pathList);
                    break;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    protected int getBarColor() {
        return R.color.bg_color;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startIssueActivity(Context context) {
        Intent intent = new Intent(context, IssueActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_issue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_issue:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etContent))) {
                    if (!TextUtils.isEmpty(StringUtil.getFromEdit(etContent))) {
                        if (selectList.size() > 0) {
                            for (String s : pathList) {
                                if (s.equals("0")) {
                                    pathList.remove(s);
                                }
                            }
                            presenter.issue(StringUtil.getFromEdit(etContent), HttpMethods.getInstance().batchUploadFiles(pathList));
                        } else {
                            presenter.issue(StringUtil.getFromEdit(etContent), null);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                if (resultCode == RESULT_OK) {
                    pathList.clear();
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia localMedia : selectList) {
                        pathList.add(localMedia.getCompressPath());
                    }
                    if (pathList.size() > 0 && pathList.size() < 9) {
                        pathList.add("0");
                    }
                    imgAdapter.setNewData(pathList);
                } else {
                    pathList.add("0");
                }
                break;

        }
    }

    @Override
    public void issueFinish() {
        ToastUtil.makeShortText(this, "发表成功");
        finish();
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
