package com.ay.lxunhan.ui.public_ac.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.model.CompleteInfoModel;
import com.ay.lxunhan.contract.UserInfoContract;
import com.ay.lxunhan.presenter.UserInfoPresenter;
import com.ay.lxunhan.utils.FileProvider7;
import com.ay.lxunhan.utils.ImageUtil;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.UserInfo;
import com.ay.lxunhan.utils.Utils;
import com.ay.lxunhan.utils.datepicker.DateTimePickerListener;
import com.ay.lxunhan.utils.datepicker.DateTimePickerUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.InfoInputDialog;
import com.ay.lxunhan.widget.InputDialog;
import com.ay.lxunhan.widget.SelectImageDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.ay.lxunhan.widget.InputDialog.INPUTDIALOG_BTNTYPE_EDITUSERNAME;

public class  UserInfoActivity extends BaseActivity<UserInfoContract.UserInfoView, UserInfoPresenter> implements DateTimePickerListener,UserInfoContract.UserInfoView {


    public static final int INTENT_REQUEST_CODE_PICK = 0; // 相册选择标记
    public static final int INTENT_REQUEST_CODE_TAKE = 1; // 相机拍照标记
    public static final int INTENT_REQUEST_CODE_CUTTING = 2; // 图片裁切标记
    public static final String IMAGE_FILE_NAME = "lxunhan.png";// 头像文件名称
    private List<CountryBean> countryBeans=new ArrayList<>();
    private List<List<CountryBean.ListBean>> countryBeans2=new ArrayList<>();

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_app_id)
    TextView tvAppId;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    private String province_id;
    private String city_id;
    private int sex_id;
    private String name;
    private String intro;
    private File file;
    private String date;
    private Uri uritempFile;
    private SelectImageDialog selectImageDialog;
    private InputDialog inputDialog;
    private InputDialog.InputDialogBuilder builder;
    private InputDialog inputDialog2;
    private InputDialog.InputDialogBuilder builder2;
    private com.ay.lxunhan.utils.datepicker.OptionsPickerView pvBirthday;

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        loadBirthdatTime();
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getUserInfo();
        presenter.getCountry();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.rl_header, R.id.rl_qrcode, R.id.tv_submit
    ,R.id.rl_address,R.id.rl_sex,R.id.rl_name,R.id.rl_age,R.id.rl_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.rl_sex:
                showSelectSex();
                break;
            case R.id.rl_address:
                if (countryBeans.size()>0){
                    showPickerView();
                }
                break;
            case R.id.rl_info:
                showInputDialog2();
                break;
            case R.id.rl_age:
                pvBirthday.show();
                break;
            case R.id.rl_name:
                showInputDialog();
                break;
            case R.id.rl_header://编辑头像
                PermissionsUtils.getInstance().chekPermissions(this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsUtils.IPermissionsResult() {
                            @Override
                            public void passPermissons() {
                                showTakePhotoDialog();
                            }

                            @Override
                            public void forbitPermissons() {

                            }
                        });
                break;
            case R.id.rl_qrcode://查看二维码
                MyQrcodeActivity.startMyQrcodeActivity(this);
                break;
            case R.id.tv_submit://提交个人信息

                CompleteInfoModel completeInfoModel=new CompleteInfoModel(name,date,String.valueOf(sex_id),city_id,intro,province_id);
                presenter.completeInfo(completeInfoModel);
                break;
        }
    }
    public static void startUserInfoActivity(Context context){
        Intent intent=new Intent(context,UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getUserInfoFinish(UserInfoBean userInfoBean) {
        UserInfo.getInstance().setUid(userInfoBean.getUid());
        GlideUtil.loadCircleImgForHead(this,ivHeader,userInfoBean.getAvatar());
        UserInfo.getInstance().setAvatar(userInfoBean.getAvatar());
        UserInfo.getInstance().setUserName(userInfoBean.getNickname());
        tvName.setText(userInfoBean.getNickname());
        if (userInfoBean.getSex()){
            tvSex.setText("男");
            sex_id=1;
        }else{
            tvSex.setText("女");
            sex_id=2;
        }
        province_id=userInfoBean.getProvince_id();
        city_id=userInfoBean.getCity_id();
        name=userInfoBean.getNickname();
        intro=userInfoBean.getAutograph();
        date=userInfoBean.getDate_birth();
        tvAppId.setText(userInfoBean.getSignal());
        tvAge.setText(userInfoBean.getAge());
        tvAddress.setText(userInfoBean.getAddress());
        tvInfo.setText(userInfoBean.getAutograph());
    }

    @Override
    public void getCountryFinish(List<CountryBean> list) {
        countryBeans=list;
        for (CountryBean countryBean : list) {
            countryBeans2.add(countryBean.getList());
        }
    }

    @Override
    public void updateUserInfoFinish() {
        presenter.getUserInfo();
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String opt1tx = countryBeans.size() > 0 ?
                    countryBeans.get(options1).getPickerViewText() : "";
            String opt2tx = countryBeans2.size() > 0
                    && countryBeans2.get(options1).size() > 0 ?
                    countryBeans2.get(options1).get(options2).getPickerViewText(): "";
            province_id= String.valueOf(countryBeans.get(options1).getRegion_id());
            city_id= String.valueOf(countryBeans2.get(options1).get(options2).getRegion_id());
            tvAddress.setText(opt1tx+" "+opt2tx);
        })
                .build();
        pvOptions.setPicker(countryBeans, countryBeans2);//三级选择器
        pvOptions.show();
    }

    /**
     * 选择日期
     */
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

    // 弹出选择图片弹窗
    public void showTakePhotoDialog() {
        if (selectImageDialog == null) {
            selectImageDialog = new SelectImageDialog(this, R.style.selectPicDialogstyle,StringUtil.getString(R.string.photo),StringUtil.getString(R.string.gallery));
        }
        selectImageDialog.show();
        selectImageDialog.setItemClickListener(new SelectImageDialog.ItemClickListener() {
            @Override
            public void album() {
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, INTENT_REQUEST_CODE_PICK);

            }

            @Override
            public void takephoto() {
                file = new File(Environment.getExternalStorageDirectory(),
                        IMAGE_FILE_NAME);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Uri uri = FileProvider7.getUriForFile(UserInfoActivity.this, file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, INTENT_REQUEST_CODE_TAKE);
                }

            }

            @Override
            public void cancel() {

            }
        });
    }

    private void showInputDialog2(){
        InfoInputDialog infoInputDialog=new InfoInputDialog(this,R.style.PublicDialog);
        infoInputDialog.show();
        infoInputDialog.setItemClickListener(info -> {
            if (!TextUtils.isEmpty(info)){
                intro=info;
                tvInfo.setText(intro);
            }
        });
    }

    private void showInputDialog() {
        if (builder == null) {
            builder = new InputDialog.InputDialogBuilder(mContext).setBtnType(INPUTDIALOG_BTNTYPE_EDITUSERNAME).setCancel(true).setShowEt1(true).setLeftText(getString(R.string.cancel)).setRightText(getString(R.string.sure))
                    .setTitle(getString(R.string.update_nick_name)).setHinit1(getString(R.string.please_enter_name2)).setNegativeButtonListener(v -> inputDialog.dismiss()
                    ).setPositiveButtonListener(v -> {
                        name = StringUtil.getFromEdit(builder.viewHolder.etDialogContent);
                        if (StringUtils.isEmpty(name)) {
                            ToastUtil.makeShortText(mContext, getString(R.string.please_enter_name2));
                            return;
                        } else if (StringUtils.isEmpty(name.trim())) {
                            inputDialog.dismiss();
                            return;
                        }
                        tvName.setText(name);
                        inputDialog.dismiss();
                    });
            inputDialog = builder.create();
        }
        inputDialog.show();
    }



    private void showSelectSex() {// 弹出选择器
        List<String> sexList=new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            tvSex.setText(sexList.get(options1));
            if (options1==0){
                sex_id=1;
            }else {
                sex_id=2;
            }
        })
                .build();
        pvOptions.setPicker(sexList);//三级选择器
        pvOptions.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE_PICK && data != null) {// 直接从相册获取
            startPhotoZoom(data.getData());
        } else if (requestCode == INTENT_REQUEST_CODE_TAKE && file != null) {// 调用相机拍照
            startPhotoZoom(FileProvider7.getUriForFile(this, file));
        } else if (requestCode == INTENT_REQUEST_CODE_CUTTING && data != null) {// 取得裁剪后的图片
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                GlideUtil.loadCircleImgForHead(this, ivHeader, bitmap);
                upload(bitmap);
            } catch (FileNotFoundException e) {
                ToastUtils.showShort("未找到裁剪图片");
            }
        }

    }
    public void upload(Bitmap bitmap){
        try {
            file = ImageUtil.saveBitmap(this, bitmap,IMAGE_FILE_NAME);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
            presenter.completeInfoImg(part);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 裁剪图片方法实现
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        FileProvider7.grantPermissions(this, intent, uri, true);
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        this.startActivityForResult(intent, INTENT_REQUEST_CODE_CUTTING);
    }

    @Override
    public boolean isKeyboardEnable() {
        return true;
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    @Override
    public void dateTimeResult(boolean dateOrTime, int type, String result, String result1) {
        date=result1;
        tvAge.setText(Utils.getAgeFromBirthTime(Utils.getDate(date))+"");
    }
}
