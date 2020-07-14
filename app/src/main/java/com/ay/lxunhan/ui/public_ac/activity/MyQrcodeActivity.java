package com.ay.lxunhan.ui.public_ac.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.contract.UserInfoContract;
import com.ay.lxunhan.presenter.UserInfoPresenter;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.FileUtils;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.QRCodeUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ay.lxunhan.utils.Contacts.DIR_NAME;

public class MyQrcodeActivity extends BaseActivity<UserInfoContract.UserInfoView, UserInfoPresenter> implements UserInfoContract.UserInfoView {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    private Bitmap bitmap;

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_qrcode;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }


    @OnClick({R.id.rl_finish, R.id.ll_save_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_save_album:
                PermissionsUtils.getInstance().chekPermissions(MyQrcodeActivity.this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                File file=FileUtils.saveBitmap(MyQrcodeActivity.this,bitmap,DIR_NAME);
                                assert file != null;
                                if (file.exists()){
                                    ToastUtil.makeShortText(MyQrcodeActivity.this,"保存成功");
                                }else{
                                    ToastUtil.makeShortText(MyQrcodeActivity.this,"保存失败");
                                }
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });

                break;
        }
    }

    public static void startMyQrcodeActivity(Context context){
        Intent intent=new Intent(context,MyQrcodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        bitmap = QRCodeUtil.createQRCodeBitmap(userInfoBean.getUid(), DisplayUtil.dip2px(this,460), DisplayUtil.dip2px(this,460));
        ivQrcode.setImageBitmap(bitmap);
        GlideUtil.loadCircleImgForHead(this,ivHeader,userInfoBean.getAvatar());
        UserInfo.getInstance().setAvatar(userInfoBean.getAvatar());
        tvName.setText(userInfoBean.getNickname());
        tvSignature.setText(userInfoBean.getAutograph());
        ivSex.setImageDrawable(getResources().getDrawable(userInfoBean.getSex()?R.drawable.ic_man:R.drawable.ic_woman));
    }

    @Override
    public void getCountryFinish(List<CountryBean> list) {

    }

    @Override
    public void updateUserInfoFinish() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
