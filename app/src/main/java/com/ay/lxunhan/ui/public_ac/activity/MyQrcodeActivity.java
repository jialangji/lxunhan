package com.ay.lxunhan.ui.public_ac.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.DisplayUtil;
import com.ay.lxunhan.utils.FileUtils;
import com.ay.lxunhan.utils.QRCodeUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MyQrcodeActivity extends BaseActivity {
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
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        bitmap = QRCodeUtil.createQRCodeBitmap(UserInfo.getInstance().getUserId(), DisplayUtil.dip2px(this,460), DisplayUtil.dip2px(this,460));
        ivQrcode.setImageBitmap(bitmap);
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
                File file=FileUtils.saveBitmap(this,bitmap);
                if (file.exists()){
                    ToastUtil.makeShortText(this,"保存成功");
                }else{
                    ToastUtil.makeShortText(this,"保存失败");
                }
                break;
        }
    }

    public static void startMyQrcodeActivity(Context context){
        Intent intent=new Intent(context,MyQrcodeActivity.class);
        context.startActivity(intent);
    }
}
