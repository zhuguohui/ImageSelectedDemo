package com.zhuguohui.imageselecteddemo.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;


import com.zhuguohui.imageselecteddemo.R;

import java.io.File;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by Vincent Woo
 * Date: 2016/8/3
 * Time: 9:48
 */
public class GalleryUtil {
    private static final int REQUEST_CODE_CAMERA = 1000;
    private static final int REQUEST_CODE_GALLERY = 1001;
    private static final int REQUEST_CODE_CROP = 1002;
    private static final int REQUEST_CODE_EDIT = 1003;

    private static ThemeConfig.Builder themeConfigBuilder = new ThemeConfig.Builder();
    private static FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
    private static ImageLoader imageLoader = new GlideImageLoader();
    private static PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

    public static void openAvatarGallery(Context ctx, GalleryFinal.OnHanlderResultCallback callback) {
        themeConfigBuilder.setEditPhotoBgTexture(new ColorDrawable(ctx.getResources().getColor(R.color.normal_background)))
                .setTitleBarBgColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabNornalColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabPressedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckSelectedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckNornalColor(Color.WHITE)
                .setCropControlColor(Color.WHITE);

        functionConfigBuilder.setMutiSelectMaxSize(1);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableRotate(false);
        functionConfigBuilder.setEnableCrop(false);
        functionConfigBuilder.setCropSquare(false);
        functionConfigBuilder.setCropReplaceSource(false);
        functionConfigBuilder.setForceCrop(false);
        functionConfigBuilder.setForceCropEdit(false);
        functionConfigBuilder.setEnableCamera(false);
//        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        FunctionConfig functionConfig = functionConfigBuilder.build();

        configCore(ctx, functionConfig);
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, callback);
    }

    public static void openGallery(Context ctx, GalleryFinal.OnHanlderResultCallback callback) {
        themeConfigBuilder.setEditPhotoBgTexture(new ColorDrawable(ctx.getResources().getColor(R.color.normal_background)))
                .setTitleBarBgColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabNornalColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabPressedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckSelectedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckNornalColor(Color.WHITE)
                .setCropControlColor(Color.WHITE);

        functionConfigBuilder.setMutiSelectMaxSize(1);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableRotate(false);
        functionConfigBuilder.setEnableCrop(false);
        functionConfigBuilder.setCropSquare(false);
        functionConfigBuilder.setCropReplaceSource(false);
        functionConfigBuilder.setForceCrop(false);
        functionConfigBuilder.setForceCropEdit(false);
        functionConfigBuilder.setEnableCamera(false);
//        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        FunctionConfig functionConfig = functionConfigBuilder.build();

        configCore(ctx, functionConfig);
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, callback);
    }

    public static void openAvatarCamera(Context ctx, GalleryFinal.OnHanlderResultCallback callback) {
        themeConfigBuilder.setEditPhotoBgTexture(new ColorDrawable(ctx.getResources().getColor(R.color.normal_background)))
                .setTitleBarBgColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabNornalColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setFabPressedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckSelectedColor(ctx.getResources().getColor(R.color.colorPrimary))
                .setCheckNornalColor(Color.WHITE)
                .setCropControlColor(Color.WHITE);

        functionConfigBuilder.setMutiSelectMaxSize(1);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableRotate(false);
        functionConfigBuilder.setEnableCrop(false);
        functionConfigBuilder.setCropSquare(true);
        functionConfigBuilder.setCropReplaceSource(false);
        functionConfigBuilder.setForceCrop(true);
        functionConfigBuilder.setForceCropEdit(false);
        functionConfigBuilder.setEnableCamera(false);
//        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        FunctionConfig functionConfig = functionConfigBuilder.build();

        configCore(ctx, functionConfig);
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, callback);
    }

    private static void configCore(Context ctx, FunctionConfig functionConfig) {
        CoreConfig coreConfig = new CoreConfig.Builder(ctx, imageLoader, themeConfigBuilder.build())
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .setEditPhotoCacheFolder(new File(ctx.getCacheDir().getAbsolutePath() + "/GalleryFinal"))
                .setTakePhotoFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))
                .build();
        GalleryFinal.init(coreConfig);
    }
}
