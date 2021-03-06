package com.ay.lxunhan.ui.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseFragment;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.contract.MyContract;
import com.ay.lxunhan.presenter.MyPresenter;
import com.ay.lxunhan.ui.my.activity.AttentionActivity;
import com.ay.lxunhan.ui.my.activity.Collect2Activity;
import com.ay.lxunhan.ui.my.activity.FansActivity;
import com.ay.lxunhan.ui.my.activity.HistoryActivity;
import com.ay.lxunhan.ui.my.activity.InviteFriendActivity;
import com.ay.lxunhan.ui.my.activity.LCoinActivity;
import com.ay.lxunhan.ui.my.activity.NotificationActivity;
import com.ay.lxunhan.ui.my.activity.SettingActivity;
import com.ay.lxunhan.ui.my.activity.SingInActivity;
import com.ay.lxunhan.ui.my.activity.WalletActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.UserInfoActivity;
import com.ay.lxunhan.ui.public_ac.activity.WebActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.google.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseFragment<MyContract.MyView, MyPresenter> implements MyContract.MyView {

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
    @BindView(R.id.tv_lb_bfb)
    TextView tvLbBfb;
    @BindView(R.id.rl_user)
    LinearLayout rlUser;
    @BindView(R.id.tv_lb_count)
    TextView tvLbCount;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MyPresenter initPresenter() {
        return new MyPresenter(this);
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
        GlideUtil.loadCircleImgForHead(getActivity(), ivHeader, UserInfo.getInstance().getAvatar());
        if (UserInfo.getInstance().isLogin()) {
            presenter.myinfo();
        }
    }

    @OnClick({R.id.iv_header, R.id.iv_scan,R.id.rl_user, R.id.ll_like, R.id.ll_fans, R.id.ll_attention, R.id.iv_setting, R.id.iv_time_change, R.id.iv_language, R.id.ll_sing, R.id.ll_invite, R.id.rl_lb, R.id.rl_wallet, R.id.rl_notification, R.id.rl_history, R.id.rl_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user:
                FriendDetailActivity.startUserDetailActivity(getActivity(), "");
                break;
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
//                if (UserInfo.getInstance().isNight()) {
//                    UserInfo.getInstance().setNight(false);
//                } else {
//                    UserInfo.getInstance().setNight(true);
//                }
//
//                getActivity().finish();
//                startActivity(new Intent(getActivity(), getActivity().getClass()));
//                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.iv_language://语言切换
                break;
            case R.id.ll_sing://签到有礼
                SingInActivity.startSingInActivity(getActivity());
                break;
            case R.id.ll_invite://邀请好友
                InviteFriendActivity.startInviteFriendActivity(getActivity());
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
                Collect2Activity.startCollectActivity(getActivity());
                break;
            case R.id.ll_like://点赞
                break;
            case R.id.ll_fans://粉丝
                FansActivity.startFansActivity(getActivity(), "", true);
                break;
            case R.id.ll_attention://关注
                AttentionActivity.startAttentionActivity(getActivity(), "", true);
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
            if (Utils.isUrl(scanResult)) {
                WebActivity.startWebActivity(getActivity(), scanResult);
            } else {
                presenter.userIsVail(scanResult);
            }

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

    @Override
    public void myInfoFinish(LoginBean bean) {
        GlideUtil.loadCircleImgForHead(getActivity(), ivHeader, bean.getAvatar());
        tvName.setText(bean.getNickname());
        ivSex.setImageDrawable(getActivity().getResources().getDrawable(bean.getSex() ? R.drawable.ic_man : R.drawable.ic_woman));
        tvIntro.setText("简介："+bean.getAutograph());
        tvLikeCount.setText(String.valueOf(bean.getLikeCount()));
        tvAttentionCount.setText(String.valueOf(bean.getBeFolCount()));
        tvFansCount.setText(String.valueOf(bean.getFolCount()));
        tvLbBfb.setText(String.format("%s%%", bean.getProportion()));
        tvLbCount.setText(bean.getGold()+"");
    }

    @Override
    public void userIsVailFinish(String id) {
        FriendDetailActivity.startUserDetailActivity(getActivity(), id);
    }

}
