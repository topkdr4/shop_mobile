package ru.vetoshkin.shop_mobile.user;


import lombok.Setter;

public class User {
    protected String login;
    protected String password;
    @Setter
    protected String sessionId;
}
