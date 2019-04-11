package com.huojumu.main.activity.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huojumu.model.AdsBean;
import com.youth.banner.loader.ImageLoader;

public class MyLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(context.getApplicationContext())
                .load(((AdsBean)path).getPath())
                .into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //圆角
//        ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        return imageView;
//    }
}