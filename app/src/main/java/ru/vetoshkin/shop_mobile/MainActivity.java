package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.user.User;
import ru.vetoshkin.shop_mobile.user.dao.UserService;
import ru.vetoshkin.shop_mobile.util.Util;





public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = this.getSharedPreferences(AppConfig.APP_CONFIG, Context.MODE_PRIVATE);

        String sessionIdFromCache = preferences.getString(AppConfig.SESSION_KEY, null);
        if (!Util.isEmpty(sessionIdFromCache)) {
            User currentUser = User.getInstance();
            currentUser.setSessionId(sessionIdFromCache);
            startActivity(new Intent(MainActivity.this, ShopActivity.class));
            return;
        }

        setContentView(R.layout.activity_main);

        findViewById(R.id.login_button).setOnClickListener(v -> {
            EditText login_edit = findViewById(R.id.login_edit);
            String login = login_edit.getText().toString();

            if (Util.isEmpty(login))
                return;

            EditText password_edit = findViewById(R.id.password_edit);
            String password = password_edit.getText().toString();

            if (Util.isEmpty(password))
                return;

            User currentUser = User.getInstance();
            currentUser.setEmail(login);
            currentUser.setPassword(password);

            String sessionId = UserService.auth(currentUser) == null ? "new_token" : null;

            if (!Util.isEmpty(sessionId)) {
                currentUser.setSessionId(sessionId);
                preferences.edit().putString(AppConfig.SESSION_KEY, sessionId).apply();
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                login_edit.setText("");
                password_edit.setText("");
                return;
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Ошибка автризации")
                    .setMessage("Неверный логин или пароль")
                    .setCancelable(false)
                    .setPositiveButton("Ок", (dialog, which) -> {

                    });

            alertDialog.show();
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    public void registration(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
