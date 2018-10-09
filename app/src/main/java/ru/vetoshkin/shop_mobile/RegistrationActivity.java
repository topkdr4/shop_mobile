package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.*;
import org.json.JSONObject;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.user.User;
import ru.vetoshkin.shop_mobile.util.Util;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;


public class RegistrationActivity extends Activity {
    private static final Pattern EMAIL_REGEXP = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }



    private void showError(Set<String> errors) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String errorMessage : errors) {
            if (i > 0)
                builder.append('\n');

            builder.append(i + 1).append(". ").append(errorMessage);
            i++;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrationActivity.this)
                .setTitle("Ошибка")
                .setMessage(builder.toString())
                .setCancelable(false)
                .setPositiveButton("Ок", (dialog, which) -> {

                });

        alertDialog.show();
    }


    public void valideForm(View view) {
        EditText email = findViewById(R.id.new_email_edit);
        EditText name = findViewById(R.id.user_name_edit);
        EditText password = findViewById(R.id.new_password_edit_one);
        EditText password_repeat = findViewById(R.id.new_password_edit_two);

        String userEmail = email.getText().toString();
        String userName  = name.getText().toString();
        String passwordOne = password.getText().toString();
        String passwordTwo = password_repeat.getText().toString();

        LinkedHashSet<String> errors = new LinkedHashSet<>();

        boolean isValidEmail = EMAIL_REGEXP.matcher(userEmail).matches();
        if (!isValidEmail)
            errors.add("Указан неверный e-mail адрес");

        if (Util.isEmpty(passwordOne)) {
            errors.add("Пароль не может быть пустым");
        } else {
            if (passwordOne.length() < 6)
                errors.add("Пароль должен быть не менее 6 символов");
        }

        if (Util.isEmpty(passwordTwo)) {
            errors.add("Пароль не может быть пустым");
        } else {
            if (passwordTwo.length() < 6)
                errors.add("Пароль должен быть не менее 6 символов");
        }

        if (!passwordOne.equals(passwordTwo))
            errors.add("Пароль не совпадает");

        if (errors.size() > 0) {
            showError(errors);
            return;
        }

        User user = User.getInstance();
        user.setEmail(userEmail);
        user.setName(userName);
        user.setPassword(passwordOne);

        try {
            user.registration(response -> {
                if (response.errorMessage != null) {
                    showError(Collections.singleton(response.errorMessage));
                } else {
                    Cookie cookie = response.cookie;
                    user.setSessionId(cookie.value());

                    SharedPreferences.Editor editor = getSharedPreferences(AppConfig.APP_CONFIG, Context.MODE_PRIVATE).edit();
                    editor.putString(AppConfig.SESSION_KEY, user.getSessionId());
                    editor.putString("COOKIE", cookie.toString());
                    editor.apply();

                    Intent intent = new Intent(RegistrationActivity.this, ShopActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e("REGISTRATION", e.getMessage());
        }
    }



    public static interface CallbackAuth {
        public void authorized(UserResponse response);
    }


    @Getter
    @Setter
    public static class UserResponse {
        private Cookie cookie;
        private String errorMessage;
    }
}
