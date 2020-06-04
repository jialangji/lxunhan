package com.ay.lxunhan.ui.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.ui.login.LoginActivity;
import com.ay.lxunhan.ui.my.activity.AttentionActivity;
import com.ay.lxunhan.ui.my.activity.CollectActivity;
import com.ay.lxunhan.ui.my.activity.FansActivity;
import com.ay.lxunhan.ui.my.activity.HistoryActivity;
import com.ay.lxunhan.ui.my.activity.LCoinActivity;
import com.ay.lxunhan.ui.my.activity.NotificationActivity;
import com.ay.lxunhan.ui.my.activity.SettingActivity;
import com.ay.lxunhan.ui.my.activity.SingInActivity;
import com.ay.lxunhan.ui.my.activity.WalletActivity;
import com.ay.lxunhan.ui.public_ac.activity.UserInfoActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.google.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseFragment {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        GlideUtil.loadCircleImgForHead(getActivity(),ivHeader, UserInfo.getInstance().getAvatar());
    }


    public void setTextSize(String str){
        String[] strings = str.split("\\.");
        SpannableString span = new SpannableString(str);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(24, false);
        span.setSpan(absoluteSizeSpan, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpanBig = new AbsoluteSizeSpan(52, false);
        span.setSpan(absoluteSizeSpanBig, 1, strings[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        span.setSpan(absoluteSizeSpan, strings[0].length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvMoney.setText(span);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.iv_header,R.id.iv_scan, R.id.ll_like, R.id.ll_fans, R.id.ll_attention, R.id.iv_setting, R.id.iv_time_change, R.id.iv_language, R.id.ll_sing, R.id.ll_invite, R.id.rl_lb, R.id.rl_wallet, R.id.rl_notification, R.id.rl_history, R.id.rl_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                UserInfoActivity.startUserInfoActivity(getActivity());
                break;
            case R.id.iv_scan://扫一扫
                startQrCode();
                break;
            case R.id.iv_setting://设置
                SettingActivity.startSettingActivity(getActivity());
                break;
            case R.id.iv_time_change://日夜间切换
                break;
            case R.id.iv_language://语言切换
                break;
            case R.id.ll_sing://签到有礼
                SingInActivity.startSingInActivity(getActivity());
                break;
            case R.id.ll_invite://邀请好友
                break;
            case R.id.rl_lb://乐币
                LCoinActivity.startLCoinActivity(getActivity());
                break;
            case R.id.rl_wallet://钱包
                WalletActivity.startWalletActivity(getActivity());
                break;
            case R.id.rl_notification://通知
                NotificationActivity.startNotificationActivity(getActivity());
                break;
            case R.id.rl_history://历史
                HistoryActivity.startHistoryActivity(getActivity());
                break;
            case R.id.rl_collect://收藏
                CollectActivity.startCollectActivity(getActivity());
                break;
            case R.id.ll_like://点赞
                break;
            case R.id.ll_fans://粉丝
                FansActivity.startFansActivity(getActivity());
                break;
            case R.id.ll_attention://关注
                AttentionActivity.startAttentionActivity(getActivity());
                break;
        }
    }

    private void startQrCode() {
        PermissionsUtils.getInstance().chekPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                // 二维码扫码
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, Contacts.REQ_QR_CODE);
            }

            @Override
            public void forbitPermissons() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Contacts.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            assert bundle != null;
            String scanResult = bundle.getString(Contacts.INTENT_EXTRA_KEY_QR_SCAN);
            ToastUtil.makeShortText(getActivity(),scanResult);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Contacts.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(getActivity(), "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case Contacts.REQ_PERM_EXTERNAL_STORAGE:
                // 文件读写权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(getActivity(), "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
