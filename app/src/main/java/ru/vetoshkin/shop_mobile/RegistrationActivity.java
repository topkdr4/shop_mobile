package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import ru.vetoshkin.shop_mobile.util.Util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class RegistrationActivity extends Activity {
    private static final Pattern EMAIL_REGEXP = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private EditText email;
    private EditText name;
    private EditText password;
    private EditText password_repeat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.email = findViewById(R.id.new_email_edit);
        this.name  = findViewById(R.id.user_name_edit);
        this.password = findViewById(R.id.new_password_edit_one);
        this.password_repeat = findViewById(R.id.new_password_edit_two);
    }


    public void registration(View view) {
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

        // TODO: registration
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

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage(builder.toString())
                .setCancelable(false)
                .setPositiveButton("Ок", (dialog, which) -> {

                });

        alertDialog.show();
    }
}
