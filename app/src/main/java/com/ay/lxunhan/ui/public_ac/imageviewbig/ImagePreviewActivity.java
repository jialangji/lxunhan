package com.ay.lxunhan.ui.public_ac.imageviewbig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.utils.ImageUtil;
import com.ay.lxunhan.utils.glide.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.dialog.PictureDialog;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImagePreviewActivity extends BaseActivity {

    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    @BindView(R.id.viewPager)
    FixedViewPager viewPager;
    @BindView(R.id.ll_points)
    LinearLayout llPoints;
    private PictureDialog dialog;
    private List<String> images;
    private List<View> guideViewList = new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected int getBarColor() {
        return R.color.black;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();

        setStatus();
        images = (List<String>) getIntent().getSerializableExtra(IMAGE_INFO);
    }

    @Override
    protected void initData() {
        super.initData();
        PhotoAdapter photoPagerAdapter = new PhotoAdapter();
        initPointView();
        viewPager.setAdapter(photoPagerAdapter);
    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
    public static void startImageviewnActivity(Context context, List<String> imageInfo, int position) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initListener() {
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(getIntent().getIntExtra(CURRENT_ITEM, 0));
    }


    private void initPointView() {
        for (int i = 0; i < images.size(); i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.selector_guide_big);
            view.setSelected(guideViewList.isEmpty());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.dp_6),
                    getResources().getDimensionPixelSize(R.dimen.dp_6));
            layoutParams.setMargins(10, 0, 0, 0);
            llPoints.addView(view, layoutParams);
            guideViewList.add(view);
        }
    }
    private void showPleaseDialog() {
        if (!isFinishing()) {
            dismissDialog();
            dialog = new PictureDialog(this);
            dialog.show();
        }
    }

    private void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class PhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final String path = images.get(position);
            final Context context = container.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.layout_image_preview, container, false);
            final PhotoView photoView = view.findViewById(R.id.preview_image);
            final SubsamplingScaleImageView longImg = view.findViewById(com.luck.picture.lib.R.id.longImg);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (PictureMimeType.isHttp(path)) {
                showPleaseDialog();
            }
            GlideApp.with(photoView.getContext())
                    .asBitmap()
                    .load(path)
                    .error(R.mipmap.ic_launcher)
                    .override(480, 800)
                    .priority(Priority.HIGH)
                    .thumbnail(0.1f)
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            dismissDialog();
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int h = width * 3;
                            boolean isLongImage = height > h;
                            if (isLongImage) {
                                longImg.setQuickScaleEnabled(true);
                                longImg.setZoomEnabled(true);
                                longImg.setPanEnabled(true);
                                longImg.setDoubleTapZoomDuration(100);
                                longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImg.setImage(ImageSource.cachedBitmap(resource), new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                photoView.setImageBitmap(resource);
                            }
                            photoView.setVisibility(isLongImage ? View.GONE : View.VISIBLE);
                            longImg.setVisibility(isLongImage ? View.VISIBLE : View.GONE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            dismissDialog();
                            if (errorDrawable != null) {
                                if (photoView.getVisibility() == View.VISIBLE) {
                                    photoView.setImageDrawable(errorDrawable);
                                }
                                if (longImg.getVisibility() == View.VISIBLE) {
                                    longImg.setImage(ImageSource.cachedBitmap(ImageUtil.drawable2Bitmap(errorDrawable)));
                                }
                            }
                        }
                    });
            photoView.setOnViewTapListener((view1, x, y) -> {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing()) {
                        ((Activity) context).finish();
                        overridePendingTransition(0, R.anim.exit_big);
                    }
                }
            });
            photoView.setOnOutsidePhotoTapListener(imageView -> {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing()) {
                        ((Activity) context).finish();
                        overridePendingTransition(0, R.anim.exit_big);
                    }
                }
            });
            longImg.setOnClickListener(v -> {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing()) {
                        ((Activity) context).finish();
                        overridePendingTransition(0, R.anim.exit_big);
                    }
                }
            });
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            View view = (View) object;
            Glide.with(view.getContext()).clear(view);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
