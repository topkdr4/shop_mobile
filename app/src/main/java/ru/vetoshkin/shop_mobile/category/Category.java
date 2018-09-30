package ru.vetoshkin.shop_mobile.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.vetoshkin.shop_mobile.R;





@Getter
@RequiredArgsConstructor
public class Category {
    public static final Category HOME     = new Category("home",     "Главная",   R.drawable.home);
    public static final Category FAVORITE = new Category("favorite", "Избранное", R.drawable.favorite);
    public static final Category BASKET   = new Category("basket",   "Корзина",   R.drawable.summary_cart);
    public static final Category PROFILE  = new Category("profile",  "Профиль",   R.drawable.profile);
    public static final Category LOGOUT   = new Category("exit",     "Выйти",     R.drawable.exit);


    protected final String id;
    protected final String title;
    protected int   iconResource = R.drawable.bike_menu_icon;

    public Category(String id, String title, int iconResource) {
        this.id = id;
        this.title = title;
        this.iconResource = iconResource;
    }


    @Override
    public String toString() {
        return title;
    }
}
