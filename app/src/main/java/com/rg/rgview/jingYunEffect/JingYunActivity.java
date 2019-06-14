package com.rg.rgview.jingYunEffect;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rg.rgview.R;

public class JingYunActivity extends AppCompatActivity {

    private DiffuseView diffuseView;
    private ImageView roundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jing_yun);

        init();
    }

    private void init() {
        roundImage =findViewById(R.id.front_image_view);
        Glide.with(this).load("http://p2.music.126.net/udVa4wtLgMA_XWnWPq3jxg==/703687441788470.jpg").into(roundImage);

        //图片裁剪和旋转
        if (Build.VERSION.SDK_INT >= 21) {
            //裁剪
            roundImage.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int width = roundImage.getWidth();
                    int height = roundImage.getHeight();
                    outline.setOval(0, 0, width, height);
                }
            });
            roundImage.setClipToOutline(true);

            //属性动画让roundImage旋转起来
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(roundImage, "rotation", 0, 360);
            objectAnimator.setDuration(15000);
            objectAnimator.setRepeatMode(ValueAnimator.RESTART);
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.setRepeatCount(-1);
            objectAnimator.start();
        }

        diffuseView = findViewById(R.id.front_diffuseview);
        diffuseView.start();
    }
}
