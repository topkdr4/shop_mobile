package ru.vetoshkin.shop_mobile.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
    /**
     * Логин / e-mail
     */
    protected final String email;

    /**
     * Пароль
     */
    protected final String password;
    protected String sessionId;
    protected String name;
    protected boolean dispatch;
}
