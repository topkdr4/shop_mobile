package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import ru.vetoshkin.shop_mobile.config.AppConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;





public class MainLoader extends Activity {
    private static final String propertyFile = "app.properties";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try (InputStream stream = getBaseContext().getAssets().open(propertyFile)) {
            Properties props = new Properties();
            props.load(stream);
            AppConfig.init(props);
        } catch (IOException e) {
            throw new AndroidRuntimeException(e);
        }

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
