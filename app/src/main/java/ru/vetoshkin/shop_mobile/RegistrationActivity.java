package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Cookie;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.user.User;
import ru.vetoshkin.shop_mobile.util.Util;


public class RegistrationActivity extends Activity {
    @BindView(R.id.new_email_edit)
    EditText email;

    @BindView(R.id.user_name_edit)
    EditText name;

    @BindView(R.id.new_password_edit_one)
    EditText password;

    @BindView(R.id.new_password_edit_two)
    EditText password_repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
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
        String userEmail = email.getText().toString();
        String userName  = name.getText().toString();
        String passwordOne = password.getText().toString();
        String passwordTwo = password_repeat.getText().toString();

        LinkedHashSet<String> errors = new LinkedHashSet<>();

        boolean isValidEmail = Util.EMAIL_REGEXP.matcher(userEmail).matches();
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
