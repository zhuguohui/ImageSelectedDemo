package com.zhuguohui.imageselecteddemo.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.zhuguohui.imageselecteddemo.R;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 图片选择工具类
 * Created by zhuguohui on 2017/4/11.
 */

public class ImageSelectedHelper implements View.OnClickListener, LGImgCompressor.CompressListener {
    private Activity activity;
    private PopupWindow mImageChooseWindow;
    private boolean needCompress = false;
    //压缩的最大尺寸
    private int maxWidth, maxHeight;
    //压缩的最大大小，单位KB
    private int maxFileSize;

    public ImageSelectedHelper(Activity activity, ImageSelectedCallBack imageSelectedCallBack) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity must be not null");
        }
        if (imageSelectedCallBack == null) {
            throw new IllegalArgumentException("imageSelectedCallBack must be not null");
        }
        this.activity = activity;
        this.imageSelectedCallBack = imageSelectedCallBack;
    }


    public void showPopWindow() {
        if (mImageChooseWindow == null) {
            View popupView = LayoutInflater.from(activity).inflate(R.layout.pop_window_choose_image, null);
            mImageChooseWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mImageChooseWindow.setBackgroundDrawable(new BitmapDrawable());
            mImageChooseWindow.setOutsideTouchable(true);
            mImageChooseWindow.setFocusable(true);
            mImageChooseWindow.setAnimationStyle(R.style.choose_image_pop_window_anim_style);
            RelativeLayout layout_open_gallery = (RelativeLayout) popupView.findViewById(R.id.layout_open_gallery);
            RelativeLayout layout_take_photo = (RelativeLayout) popupView.findViewById(R.id.layout_take_photo);
            RelativeLayout layout_cancel = (RelativeLayout) popupView.findViewById(R.id.layout_cancel);
            layout_open_gallery.setOnClickListener(this);
            layout_take_photo.setOnClickListener(this);
            layout_cancel.setOnClickListener(this);
            mImageChooseWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setAlpha(1.0f);
                }
            });

        } else {
            if (mImageChooseWindow.isShowing()) {
                mImageChooseWindow.dismiss();
                return;
            }
        }

        setAlpha(0.5f);
        mImageChooseWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void setNeedCompress(boolean needCompress) {
        this.needCompress = needCompress;
    }

    public void setCompressInfo(int maxWidth, int maxHeight, int maxFileSize) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxFileSize = maxFileSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_open_gallery: //相册
                mImageChooseWindow.dismiss();
                openAvatarGallery();
                break;
            case R.id.layout_take_photo: //拍照
                mImageChooseWindow.dismiss();
                openAvatarCamera();
                break;
            case R.id.layout_cancel://取消按钮
                mImageChooseWindow.dismiss();
                break;
        }
    }

    private void openAvatarGallery() {
        GalleryUtil.openAvatarGallery(activity, callback);
    }

    private void openAvatarCamera() {
        GalleryUtil.openAvatarCamera(activity, callback);
    }

    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null && resultList.size() > 0) {
                if (needCompress) {
                    //压缩图片
                    LGImgCompressor.getInstance(activity).withListener(ImageSelectedHelper.this).
                            starCompress(Uri.fromFile(new File(resultList.get(0).getPhotoPath())).toString(), 1000, 1000, 200);
                } else {
                    if (imageSelectedCallBack != null) {
                        imageSelectedCallBack.onImageSelectedSuccess(resultList.get(0).getPhotoPath());
                    }
                }

            } else {
                if (imageSelectedCallBack != null) {
                    imageSelectedCallBack.onImageSelectedFailure("");
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            if (imageSelectedCallBack != null) {
                imageSelectedCallBack.onImageSelectedFailure(errorMsg);
            }
        }
    };

    private ImageSelectedCallBack imageSelectedCallBack;

    @Override
    public void onCompressStart() {

    }

    @Override
    public void onCompressEnd(LGImgCompressor.CompressResult compressResult) {
        if (compressResult.getStatus() == LGImgCompressor.CompressResult.RESULT_ERROR) {//压缩失败
            if (imageSelectedCallBack != null) {
                imageSelectedCallBack.onImageSelectedFailure("");
            }
        } else {
            if (imageSelectedCallBack != null) {
                imageSelectedCallBack.onImageSelectedSuccess(compressResult.getOutPath());
            }
        }
    }

    public interface ImageSelectedCallBack {
        void onImageSelectedSuccess(String imagePath);

        void onImageSelectedFailure(String errorMsg);
    }

    public void setAlpha(float alpha) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha = alpha;   //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
        window.setAttributes(wl);
    }
}
