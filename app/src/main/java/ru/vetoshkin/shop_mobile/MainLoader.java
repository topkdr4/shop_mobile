package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vetoshkin.shop_mobile.config.AppConfig;





public class MainLoader extends Activity {
    private final AlphaAnimation animation = createAnimation();

    @BindView(R.id.screen_logo)
    ImageView screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loader);
        ButterKnife.bind(this);
        screen.setAnimation(animation);
    }


    private AlphaAnimation createAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f) ;
        animation.setFillAfter(true);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainLoader.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return animation;
    }



}
