package ru.vetoshkin.shop_mobile.category;

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
        new Category("8", "Электрические")
    );


    public static List<Category> getCategories() {
        return temp;
    }



    private CategoryService() {

    }

}
