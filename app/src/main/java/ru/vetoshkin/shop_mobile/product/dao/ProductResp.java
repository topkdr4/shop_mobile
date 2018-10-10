package ru.vetoshkin.shop_mobile.product.dao;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.shop_mobile.product.Product;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class ProductResp {
    private List<Product> result;
}
