package com.zhuguohui.imageselecteddemo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhuguohui.imageselecteddemo.util.ImageSelectedHelper;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ImageSelectedHelper.ImageSelectedCallBack {
    ImageView iv_head;
    TextView imageInfo;
    ImageSelectedHelper imageSelectedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        imageInfo = (TextView) findViewById(R.id.imageInfo);
        imageSelectedHelper = new ImageSelectedHelper(this, this);
        //开启压缩
        imageSelectedHelper.setNeedCompress(true);
        //设置压缩信息，最大宽度，最大高度，最大体积单位KB
        imageSelectedHelper.setCompressInfo(1000, 1000, 200);
    }

    public void selectedImage(View view) {
        imageSelectedHelper.showPopWindow();
    }

    @Override
    public void onImageSelectedSuccess(String imagePath) {

        File file = new File(imagePath);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
            iv_head.setImageBitmap(bitmap);
            float imageFileSize = file.length() / 1024f;
            imageInfo.setText("image info width:" + bitmap.getWidth() + " \nheight:" + bitmap.getHeight() +
                    " \nsize:" + imageFileSize + "kb" + "\nimagePath:" + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageSelectedFailure(String errorMsg) {
        Toast.makeText(this, "图片选择失败", Toast.LENGTH_SHORT).show();
    }
}
