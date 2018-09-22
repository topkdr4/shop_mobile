package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;





public class MainLoader extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_loader);

        GifImageView preloadView = findViewById(R.id.preload_gif);
        GifDrawable drawable = (GifDrawable) preloadView.getDrawable();
        drawable.addAnimationListener(loopNumber -> {
            Intent intent = new Intent(MainLoader.this, MainActivity.class);
            startActivity(intent);
        });
    }


}
