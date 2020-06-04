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
import com.ay.lxunhan.bean.ComplaintBean;
import com.ay.lxunhan.bean.model.ComplaintModel;
import com.ay.lxunhan.contract.ComplaintContract;
import com.ay.lxunhan.presenter.ComplaintPresenter;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.widget.GridDividerDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ComplaintActivity extends BaseActivity<ComplaintContract.ComplaintView, ComplaintPresenter> implements ComplaintContract.ComplaintView {
    private static final int MAX_NUM = 500;
    @BindView(R.id.rv_complaint_type)
    RecyclerView rvComplaintType;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_et_num)
    TextView tvEtNum;
    private BaseQuickAdapter baseQuickAdapter;
    private List<ComplaintBean> complaintBeans = new ArrayList<>();
    private String[] str = new String[]{"涉黄信息", "不实信息", "人身攻击", "有害信息", "抄袭", "违法违规"};
    private String aid;
    private int type;

    @Override
    public ComplaintPresenter initPresenter() {
        return new ComplaintPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        aid = getIntent().getStringExtra("aid");
        type = getIntent().getIntExtra("type", -1);
        for (int i = 0; i < str.length; i++) {
            complaintBeans.add(new ComplaintBean(str[i], i + 1));
        }
        baseQuickAdapter = new BaseQuickAdapter<ComplaintBean, BaseViewHolder>(R.layout.item_complaint, complaintBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ComplaintBean item) {
                TextView tvName = helper.getView(R.id.tv_name);
                tvName.setText(item.getName());
                if (item.isSelect()) {
                    tvName.setBackground(getResources().getDrawable(R.drawable.shape_radius_pink_10));
                    tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tvName.setBackground(getResources().getDrawable(R.drawable.shape_grayb2_line));
                    tvName.setTextColor(getResources().getColor(R.color.color_b2));
                }
            }
        };
        rvComplaintType.setLayoutManager(new GridLayoutManager(this, 3));
        rvComplaintType.addItemDecoration(new GridDividerDecoration(this, 15, GridLayoutManager.VERTICAL));

        rvComplaintType.setAdapter(baseQuickAdapter);
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

        baseQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (ComplaintBean complaintBean : complaintBeans) {
                complaintBean.setSelect(false);
            }
            complaintBeans.get(position).setSelect(true);
            baseQuickAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_submit:
                boolean isSelect = false;
                int cp_type = 0;
                for (ComplaintBean complaintBean : complaintBeans) {
                    if (complaintBean.isSelect()) {
                        isSelect = true;
                        cp_type = complaintBean.getType();
                    }
                }
                if (!isSelect) {
                    ToastUtil.makeShortText(this, "请选择投诉原因");
                    return;
                }
                ComplaintModel complaintModel;
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etContent))) {
                    complaintModel = new ComplaintModel(aid, type, cp_type, StringUtil.getFromEdit(etContent));
                } else {
                    complaintModel = new ComplaintModel(aid, type, cp_type);
                }
                presenter.complaint(complaintModel);
                break;
        }
    }

    public static void startComplaintActivity(Context context, String aid, int type) {
        Intent intent = new Intent(context, ComplaintActivity.class);
        intent.putExtra("aid", aid);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void complaintFinish() {
        ToastUtil.makeShortText(this, "投诉成功");
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
