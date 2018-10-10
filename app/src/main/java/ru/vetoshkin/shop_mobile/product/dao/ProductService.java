package ru.vetoshkin.shop_mobile.product.dao;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import lombok.Getter;
import okhttp3.*;
import ru.vetoshkin.shop_mobile.category.Category;
import ru.vetoshkin.shop_mobile.category.dao.CategoryService;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.util.Json;
import ru.vetoshkin.shop_mobile.util.Util;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;





public class ProductService {
    private static final String topProductsUrl = "/product/best";
    public static final Map<String, Bitmap> previewCache = new ConcurrentHashMap<>();



    /**
     * Список продуктов заданной категории и страницы
     */
    public static void getProducts(String categoryId, int page, ProductCallback callBack) {
        if (categoryId.equals(Category.HOME.getId())) {
            loadBest(callBack);
            return;
        }

        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url(AppConfig.getServerHost() + "/product/list/" + categoryId + "/" + page)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.run(new ArrayList<>());
            }


            @Override
            public void onResponse(Call call, okhttp3.Response response) {
                try {
                    String resultString = response.body().string();
                    Log.e("LOL", resultString);
                    ProductResp resp = Json.toObject(resultString, ProductResp.class);
                    Log.e("LOL2", Json.toJson(resp));
                    callBack.run(resp.getResult());
                } catch (Throwable throwable) {
                    Log.e(throwable.getClass().getCanonicalName(), Util.getStackTrace(throwable));
                }
            }
        });
    }


    private static void loadBest(ProductCallback callBack) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url(AppConfig.getServerHost() + topProductsUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("BEST", "ERROR");
                callBack.run(new ArrayList<>());
            }


            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                ProductResp resp = Json.toObject(response.body().string(), ProductResp.class);
                callBack.run(resp.getResult());
            }
        });
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



    public static interface ProductCallback {
        public void run(List<Product> resp);
    }

}
