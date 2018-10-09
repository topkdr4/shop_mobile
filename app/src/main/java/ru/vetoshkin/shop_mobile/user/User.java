package ru.vetoshkin.shop_mobile.user;


import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import ru.vetoshkin.shop_mobile.RegistrationActivity;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.util.Json;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.function.Consumer;





@Getter
@Setter
public class User {
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String registrationUrl = "/user/registr";
    private static final String authUrl = "/user/auth";

    /**
     * Логин / e-mail
     */
    protected String email;

    /**
     * Пароль
     */
    protected String password;

    /**
     * Ид сессии
     */
    protected String sessionId;

    /**
     * Имя
     */
    protected String name;

    /**
     * Участие в почтовой рассылке
     */
    protected boolean dispatch;

    /**
     * Текущий пользователь
     */
    private static final User currentUser = new User();


    public static User getInstance() {
        return currentUser;
    }


    public void auth(RegistrationActivity.CallbackAuth runnableFuture) throws Exception {
        User temp = new User();
        temp.email = email;
        temp.password = password;


        RequestBody body = RequestBody.create(JSON, Json.toJson(temp));

        Request authRequest = new Request.Builder()
                .url(AppConfig.getServerHost() + authUrl)
                .post(body)
                .build();


        RegistrationActivity.UserResponse resp = new RegistrationActivity.UserResponse();

        client.newCall(authRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                resp.setErrorMessage(e.getMessage());
                runnableFuture.authorized(resp);
            }


            @Override
            public void onResponse(Call call, Response response) {
                List<String> cookies = response.headers("Set-Cookie");
                Cookie resultCookie = null;
                for (String cookieString : cookies) {
                    Cookie cookie = Cookie.parse(HttpUrl.parse(AppConfig.getServerHost() + authUrl), cookieString);
                    if (cookie == null)
                        continue;

                    String name = cookie.name();

                    if (!"sessionId".equals(name))
                        continue;

                    resultCookie = cookie;
                    break;
                }


                if (resultCookie == null)
                     resp.setErrorMessage("Ошибка авторизации");
                else resp.setCookie(resultCookie);

                runnableFuture.authorized(resp);
            }
        });
    }


    public void registration(RegistrationActivity.CallbackAuth runnableFuture) throws Exception {
        User temp = new User();
        temp.email = email;
        temp.password = password;
        temp.name = name;

        RequestBody body = RequestBody.create(JSON, Json.toJson(temp));

        Request request = new Request.Builder()
                .url(AppConfig.getServerHost() + registrationUrl)
                .post(body)
                .build();


        RegistrationActivity.UserResponse errorResp = new RegistrationActivity.UserResponse();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                errorResp.setErrorMessage(e.getMessage());
                runnableFuture.authorized(errorResp);
            }


            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (!response.isSuccessful()) {
                        errorResp.setErrorMessage("Пользователь уже существует");
                        runnableFuture.authorized(errorResp);
                    } else {
                        auth(runnableFuture);
                    }
                } catch (Exception e) {
                    errorResp.setErrorMessage(e.getMessage());
                    runnableFuture.authorized(errorResp);
                    Log.e("AUTH", e.getMessage());
                }
            }
        });
    }


    private User() {

    }
}
