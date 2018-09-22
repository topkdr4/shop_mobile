package ru.vetoshkin.shop_mobile.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class User {
    protected final String login;
    protected final String password;
    @Setter
    protected String sessionId;
}
