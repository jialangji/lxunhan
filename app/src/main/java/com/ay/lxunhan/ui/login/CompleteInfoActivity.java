package com.ay.lxunhan.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ay.lxunhan.MainActivity;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.model.CompleteInfoModel;
import com.ay.lxunhan.contract.CompleteInfoContract;
import com.ay.lxunhan.observer.EventModel;
import com.ay.lxunhan.presenter.CompleteInfoPresenter;
import com.ay.lxunhan.utils.AppManager;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.datepicker.DateTimePickerListener;
import com.ay.lxunhan.utils.datepicker.DateTimePickerUtil;
import com.ay.lxunhan.utils.datepicker.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompleteInfoActivity extends BaseActivity<CompleteInfoContract.CompleteInfoView, CompleteInfoPresenter> implements DateTimePickerListener, CompleteInfoContract.CompleteInfoView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_date)
    TextView etDate;
    @BindView(R.id.check_girl)
    CheckBox checkGirl;
    @BindView(R.id.check_boy)
    CheckBox checkBoy;
    private OptionsPickerView pvBirthday;
    private String date;
    private int sexType=0;

    @Override
    public CompleteInfoPresenter initPresenter() {
        return new CompleteInfoPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        loadBirthdatTime();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_info;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.et_date, R.id.tv_sure,R.id.ll_boy,R.id.ll_girl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_boy:
                if (!checkBoy.isChecked()){
                    checkBoy.setChecked(true);
                    sexType=1;
                    checkGirl.setChecked(false);
                }
                break;
            case R.id.ll_girl:
                if (!checkGirl.isChecked()){
                    checkBoy.setChecked(false);
                    sexType=2;
                    checkGirl.setChecked(true);
                }
                break;
            case R.id.et_date:
                pvBirthday.show();
                break;
            case R.id.tv_sure:
                if (!TextUtils.isEmpty(StringUtil.getFromEdit(etName)))
                if (!TextUtils.isEmpty(date)){
                    if (checkBoy.isChecked()||checkGirl.isChecked()){
                        CompleteInfoModel completeInfoModel=new CompleteInfoModel(StringUtil.getFromEdit(etName),date,sexType);
                        presenter.completeInfo(completeInfoModel);
                    }
                }
                break;
        }
    }

    public void loadBirthdatTime() {
        pvBirthday = DateTimePickerUtil.getAllDatePicker(this, 0, this);
        Dialog mDialog = pvBirthday.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvBirthday.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }

    @Override
    public void dateTimeResult(boolean dateOrTime, int type, String result, String result1) {
        etDate.setText(result1);
        date=result1;
    }

    public static void startCompleteInfoActivity(Context context){
        Intent intent=new Intent(context,CompleteInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void completeInfoFinish() {
        MainActivity.startMainActivity(this);
        UserInfo.getInstance().setLogin(true);
        EventBus.getDefault().postSticky(new EventModel<>(EventModel.LOGIN));
        AppManager.getAppManager().finishActivity(BootPageActivity.class);
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
