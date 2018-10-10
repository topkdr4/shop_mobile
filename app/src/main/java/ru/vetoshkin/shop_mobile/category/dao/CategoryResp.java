package ru.vetoshkin.shop_mobile.category.dao;

import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.shop_mobile.category.Category;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class CategoryResp {
    private List<Category> result;
}
