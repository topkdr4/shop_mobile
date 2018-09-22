package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;





public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.login_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainLoader.class);
            startActivity(intent);
        });
    }
}
