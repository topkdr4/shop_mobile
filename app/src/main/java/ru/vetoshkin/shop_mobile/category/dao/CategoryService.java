package ru.vetoshkin.shop_mobile.category.dao;

import android.app.Fragment;
import ru.vetoshkin.shop_mobile.category.Category;
import ru.vetoshkin.shop_mobile.fragments.BasketFragment;
import ru.vetoshkin.shop_mobile.fragments.FavoriteFragment;
import ru.vetoshkin.shop_mobile.fragments.ProfileFragment;

import java.util.Arrays;
import java.util.List;





public class CategoryService {

    private static final List<Category> temp = Arrays.asList(
            Category.HOME,
            new Category("2", "Горные"),
            new Category("4", "Городские"),
            new Category("5", "BMX"),
            new Category("6", "Складные"),
            new Category("7", "Шоссейные"),
            new Category("8", "Электрические"),
            Category.FAVORITE,
            Category.BASKET,
            Category.PROFILE,
            Category.LOGOUT
    );



    public static Fragment getFragment(Category category) {
        if (category == Category.FAVORITE)
            return new FavoriteFragment();

        if (category == Category.BASKET)
            return new BasketFragment();

        if (category == Category.PROFILE)
            return new ProfileFragment();

        return null;
    }


    private CategoryService() {

    }


    public static List<Category> getCategories() {
        return temp;
    }

}
