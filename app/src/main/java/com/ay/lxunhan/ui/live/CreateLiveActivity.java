package com.ay.lxunhan.ui.live;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CreateBean;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.bean.LiveDetailBean;
import com.ay.lxunhan.contract.CreateLiveContract;
import com.ay.lxunhan.presenter.CreateLivePresenter;
import com.ay.lxunhan.utils.FileProvider7;
import com.ay.lxunhan.utils.ImageUtil;
import com.ay.lxunhan.utils.PermissionsUtils;
import com.ay.lxunhan.utils.StringUtil;
import com.ay.lxunhan.utils.ToastUtil;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.SelectImageDialog;
import com.ay.lxunhan.widget.SelectLiveTypeDialog;
import com.ay.lxunhan.wyyim.liveuser.PublishParam;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.vcloud.video.render.NeteaseView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateLiveActivity extends BaseActivity<CreateLiveContract.CreateLiveView, CreateLivePresenter> implements CreateLiveContract.CreateLiveView,lsMessageHandler {
    public static final int INTENT_REQUEST_CODE_PICK = 0; // 相册选择标记
    public static final int INTENT_REQUEST_CODE_TAKE = 1; // 相机拍照标记
    public static final int WRITE_PERMISSION_REQ_CODE = 100;
    public static final String IMAGE_FILE_NAME = System.currentTimeMillis() + "lxunhan.png";//封面图文件名称
    public static final String TAG = "韩乐讯：网易云直播：";
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.live_filter_view)
    NeteaseView filterSurfaceView;
    private lsMediaCapture mLSMediaCapture;
    Handler mainHandler;
    private File file;
    /**
     * 6.0权限处理
     **/
    private boolean bPermission = false;
    private SelectImageDialog selectImageDialog;
    private Bitmap bitmap;
    private SelectLiveTypeDialog selectTypeDialog;
    private String typeId;
    private List<LiveBean> liveBeans=new ArrayList<>();


    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public CreateLivePresenter initPresenter() {
        return new CreateLivePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_live;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLSMediaCapture.stopVideoPreview();
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getLiveType();
        presenter.getLiveDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (bPermission){
//            mLSMediaCapture.startVideoPreview(filterSurfaceView, true, true, lsMediaCapture.VideoQuality.HIGH, true);
//        }
    }

    @Override
    protected void initView() {
        super.initView();
        bPermission = checkPublishPermission();
        mainHandler = new Handler(Looper.getMainLooper());
//        lsMediaCapture.LsMediaCapturePara lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
//        lsMediaCapturePara.setContext(getApplicationContext()); //设置SDK上下文（建议使用ApplicationContext）
//        lsMediaCapturePara.setMessageHandler(this); //设置SDK消息回调
//        lsMediaCapturePara.setLogLevel(lsLogUtil.LogLevel.INFO); //日志级别
//        lsMediaCapturePara.setUploadLog(true);//是否上传SDK日志
//        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara);
//        if (bPermission){
//            mLSMediaCapture.startVideoPreview(filterSurfaceView, true, true, lsMediaCapture.VideoQuality.HIGH, true);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLSMediaCapture.stopVideoPreview();
//        mLSMediaCapture.destroyVideoPreview();
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish, R.id.tv_select, R.id.rl_fanzhuan, R.id.tv_open_live, R.id.tv_change_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
//                if (mLSMediaCapture != null) {
//                    mLSMediaCapture.stopVideoPreview();
//                    mLSMediaCapture.destroyVideoPreview();
//                }
                finish();
                break;
            case R.id.tv_select:
                if (liveBeans.size()>0){

                    showDialog(liveBeans);

                }
                break;
            case R.id.rl_fanzhuan:
//                if (mLSMediaCapture != null) {
//                    mLSMediaCapture.switchCamera();
//                }
                break;
            case R.id.tv_open_live:
                if (!bPermission) {
                    ToastUtil.makeShortText(this, "请先允许app所需要的权限");
                    AskForPermission();
                    return;
                }
                if (TextUtils.isEmpty(typeId)){
                    ToastUtil.makeShortText(this, "请选择话题");
                    return;
                }
                if (bitmap!=null){
                    try {
                        file = ImageUtil.saveBitmap(this, bitmap,IMAGE_FILE_NAME);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("cover", file.getName(), requestBody);
                        presenter.createLive(StringUtil.getFromEdit(etTitle),typeId,part);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    presenter.createLive(StringUtil.getFromEdit(etTitle),typeId,null);
                }
                break;
            case R.id.tv_change_img:
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
        }
    }

    public void showDialog(List<LiveBean> liveBeans){
        if (selectTypeDialog==null){
            selectTypeDialog = new SelectLiveTypeDialog(this, R.style.selectPicDialogstyle,liveBeans);
        }
        selectTypeDialog.show();
        selectTypeDialog.setItemClickListener(new SelectLiveTypeDialog.ItemClickListener() {
            @Override
            public void onItem(LiveBean typeBean) {
                typeId= String.valueOf(typeBean.getId());
                tvSelect.setText(typeBean.getName());
            }

            @Override
            public void cancel() {
                selectTypeDialog.dismiss();
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            do something.
//            if (mLSMediaCapture != null) {
//                mLSMediaCapture.stopVideoPreview();
//                mLSMediaCapture.destroyVideoPreview();
//            }
            finish();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    // 弹出选择图片弹窗
    public void showTakePhotoDialog() {
        if (selectImageDialog == null) {
            selectImageDialog = new SelectImageDialog(this, R.style.selectPicDialogstyle);
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
                    Uri uri = FileProvider7.getUriForFile(CreateLiveActivity.this, file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, INTENT_REQUEST_CODE_TAKE);
                }

            }

            @Override
            public void cancel() {

            }
        });
    }


    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限设置");
        builder.setNegativeButton("取消", (dialog, which) -> {

        });
        builder.setPositiveButton("设置", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
            startActivity(intent);
        });
        builder.create().show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //用户可能刚设置完权限回到Activity,  此时再次检查权限
        if (!bPermission) {
            bPermission = checkPublishPermission();
        }
    }


    @Override
    public void handleMessage(int i, Object o) {
        mainHandler.post(() -> handleMessageInMainThread(i));
    }


    private void handleMessageInMainThread(int msg) {
        switch (msg) {
            case MSG_START_PREVIEW_ERROR://视频预览出错，可能是获取不到camera的使用权限
            {
                Log.e(TAG, "test: in handleMessage, MSG_START_PREVIEW_ERROR");
                break;
            }
            case MSG_AUDIO_RECORD_ERROR://音频采集出错，获取不到麦克风的使用权限
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_RECORD_ERROR");
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE_PICK && data != null) {// 直接从相册获取
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Objects.requireNonNull(data.getData())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            GlideUtil.loadRoundImg(this, ivHeader, bitmap);
        } else if (requestCode == INTENT_REQUEST_CODE_TAKE && file != null) {// 调用相机拍照
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(FileProvider7.getUriForFile(this, file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            GlideUtil.loadRoundImg(this, ivHeader, bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }

    public static void startCreateLiveActivity(Context context) {
        Intent intent = new Intent(context, CreateLiveActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getLiveTypeFinish(List<LiveBean> beans) {
        liveBeans.clear();
        for (LiveBean bean : beans) {
            if (bean.getId()==1&&bean.getId()==2){
                return;
            }else{
                liveBeans.add(bean);
            }
        }

    }

    @Override
    public void getLiveDetailFinish(LiveDetailBean bean) {
        GlideUtil.loadRoundImg(this,ivHeader,bean.getCover());
        typeId= String.valueOf(bean.getLtype());
        tvSelect.setText(bean.getLtypenm());
        etTitle.setText(bean.getLname());
    }
    public static final String QUALITY_HD = "HD";
    public static final String QUALITY_SD = "SD";
    public static final String QUALITY_LD = "LD";
    @Override
    public void createLiveFinish(CreateBean createBean) {
        PublishParam publishParam = new PublishParam();
        publishParam.pushUrl = createBean.getPushurl();
        publishParam.definition = QUALITY_SD;
        publishParam.openVideo = true;
        publishParam.openAudio = true;
        publishParam.useFilter = true;
        LiveRoomActivity.startLive(this,createBean.getRoomcode(),publishParam,createBean.getLid());
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
