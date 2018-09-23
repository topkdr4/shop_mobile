package ru.vetoshkin.shop_mobile.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Category {
    public static final Category HOME = new Category("home", "Главная");

    protected final String id;
    protected final String title;

    @Override
    public String toString() {
        return title;
    }
}
