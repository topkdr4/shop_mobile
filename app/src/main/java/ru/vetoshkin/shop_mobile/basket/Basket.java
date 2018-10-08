package ru.vetoshkin.shop_mobile.basket;
/**
 * Ветошкин А.В. РИС-16бзу
 * */


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import lombok.RequiredArgsConstructor;
import ru.vetoshkin.shop_mobile.product.Product;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Корзина
 */
public class Basket {
    private static final Map<String, Holder> basket = new ConcurrentHashMap<>();
    public static final List<Product> selectedProduct = new LinkedList<>();


    /**
     * Добавить продукт в корзину
     */
    public static void put(Product product) {
        Holder holder = basket.get(product.getId());
        if (holder == null) {
            basket.put(product.getId(), new Holder(product));
            selectedProduct.add(product);
            return;
        }

        ++holder.itemCount;
    }


    /**
     * Уменьшить кол-во продукта в корзине
     */
    public static void removePart(Product product) {
        Holder holder = basket.get(product.getId());
        if (holder == null)
            throw new IllegalArgumentException("product not found");

        // Удалялся последний
        if (--holder.itemCount == 0)
            removeProduct(product);
    }


    /**
     * Удалить продукт из корзины
     */
    public static void removeProduct(Product product) {
        basket.remove(product.getId());

        for (int i = 0; i < selectedProduct.size(); i++) {
            Product fromList = selectedProduct.get(i);
            if (fromList.getId().equals(product.getId())) {
                selectedProduct.remove(i);
                break;
            }
        }

        // Позор
        // selectedProduct.removeIf(prod -> true);
    }


    /**
     * Количество продукта в корзине
     */
    public static int getProductCount(Product product) {
        Holder holder = basket.get(product.getId());
        if (holder == null)
            return 0;

        return holder.itemCount;
    }


    /**
     * Получить сумму корзины
     */
    public static double getSum() {
        double result = 0;
        for (Holder holder : basket.values()) {
            result += (holder.itemCount * holder.product.getPrice());
        }

        return result;
    }


    public static void printBasket(Context context) {
        Toast toast = Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(toast::cancel, 500);
    }



    @RequiredArgsConstructor
    private static class Holder {
        private final Product product;
        private int itemCount = 1;


        @Override
        public String toString() {
            return product.getId() + "|" + itemCount;
        }
    }



    private Basket() {

    }

}
