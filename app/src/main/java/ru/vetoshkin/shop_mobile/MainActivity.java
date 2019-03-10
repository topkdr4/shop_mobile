package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vetoshkin.shop_mobile.category.dao.CategoryService;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.user.User;
import ru.vetoshkin.shop_mobile.util.Util;


public class MainActivity extends Activity {

    @BindView(R.id.login_button)
    MaterialButton button;

    @BindView(R.id.login_edit)
    EditText login_edit;

    @BindView(R.id.password_edit)
    EditText password_edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = this.getSharedPreferences(AppConfig.APP_CONFIG, Context.MODE_PRIVATE);

        /*
        String sessionIdFromCache = preferences.getString(AppConfig.SESSION_KEY, null);
        if (!Util.isEmpty(sessionIdFromCache)) {
            User currentUser = User.getInstance();
            currentUser.setSessionId(sessionIdFromCache);
            CategoryService.loadCategories(response -> {
               if (response.getErrorMessage() == null)
                   startActivity(new Intent(MainActivity.this, ShopActivity.class));
            });
            return;
        }
*/

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        button.setOnClickListener(v -> {
            String login = login_edit.getText().toString();

            if (Util.isEmpty(login))
                return;

            String password = password_edit.getText().toString();

            if (Util.isEmpty(password))
                return;

            User currentUser = User.getInstance();
            currentUser.setEmail(login);
            currentUser.setPassword(password);

            try {
                currentUser.auth(response -> {
                    if (response.getErrorMessage() != null) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                                .setTitle("Ошибка автризации")
                                .setMessage("Неверный логин или пароль")
                                .setCancelable(false)
                                .setPositiveButton("Ок", (dialog, which) -> {

                                });

                        alertDialog.show();
                        return;
                    }

                    String sessionId = response.getCookie().value();
                    currentUser.setSessionId(sessionId);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(AppConfig.SESSION_KEY, sessionId);
                    editor.putString("COOKIE", response.getCookie().toString());
                    editor.apply();

                    CategoryService.loadCategories(respCategory -> {
                        if (respCategory.getErrorMessage() != null) {
                            return;
                        }

                        Intent intent = new Intent(this, ShopActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });
                });
            } catch (Exception e) {
                Log.e("AUTH", e.getMessage());
            }
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
