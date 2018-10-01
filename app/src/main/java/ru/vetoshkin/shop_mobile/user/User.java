package ru.vetoshkin.shop_mobile.user;


import lombok.Getter;
import lombok.Setter;





@Getter
@Setter
public class User {

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
     *
     */
    private static final User currentUser = new User();


    public static User getInstance() {
        return currentUser;
    }


    private User() {

    }
}
