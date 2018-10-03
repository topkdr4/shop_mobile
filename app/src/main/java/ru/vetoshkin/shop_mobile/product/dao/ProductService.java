package ru.vetoshkin.shop_mobile.product.dao;
import android.content.SharedPreferences;
import lombok.Getter;
import ru.vetoshkin.shop_mobile.product.Product;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;





public class ProductService {

    @Getter
    private static final List<Product> top_products = new ArrayList<>();

    static {
        Product best1 = new Product();
        best1.setId("A340");
        best1.setTitle("СуперВелос1");
        best1.setCategory("2");
        best1.setDescription("Самый лучший велос!!!");
        best1.setPrice(345.56f);
        best1.setImages(Arrays.asList(3, 4, 5));

        top_products.add(best1);


        Product best2 = new Product();
        best2.setId("A341");
        best2.setTitle("СуперВелос2");
        best2.setCategory("2");
        best2.setDescription("Самый лучший велос!!!");
        best2.setPrice(345.56f);
        best2.setImages(Arrays.asList(3, 4, 5));

        top_products.add(best2);


        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 9; i++) {
            Product best = new Product();
            best.setId("A" + (350 + i));
            best.setTitle("СуперВелос" + (i + 3));
            best.setCategory("2");
            best.setDescription("Самый лучший велос!!!");
            best.setPrice(random.nextInt(5000, 100000));
            best.setImages(Arrays.asList(3, 4, 5));
            best.setFavorite(random.nextBoolean());

            top_products.add(best);
        }
    }


    /**
     * Список продуктов заданной категории и страницы
     */
    public static List<Product> getProducts(String categoryId, int page) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        List<Product> result = new ArrayList<>(9);

        for (int i = 0; i < 9; i++) {
            Product best = new Product();
            best.setId("BIKE_" + (page * 9 + i));
            best.setTitle("Велосипед " + categoryId + " [" + page + " " + i + "]");
            best.setCategory(categoryId);
            best.setDescription("Самый лучший велос!!!");
            best.setPrice(random.nextInt(5000, 100000));
            best.setImages(Arrays.asList(3, 4, 5));

            result.add(best);
        }

        return result;
    }


    /**
     * Получить информацию по продуктам
     */
    public static List<Product> getProducts(List<String> productsId) {
        List<Product> result = new ArrayList<>();

        for (String s : productsId) {
            Product product = new Product();
            product.setTitle(s);
            product.setId(s);
            result.add(product);
        }

        return result;
    }


    /**
     * Получить список избранных товаров
     */
    public static List<Product> getFavorites(SharedPreferences preferences) {
        Map<String, ?> favorites = preferences.getAll();
        List<String> favoriteProductId = new ArrayList<>(favorites.size());

        for (Object productId : favorites.keySet()) {
            favoriteProductId.add(String.valueOf(productId));
        }

        return getProducts(favoriteProductId);
    }
}
