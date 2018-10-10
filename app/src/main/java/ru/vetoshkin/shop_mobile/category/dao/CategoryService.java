package ru.vetoshkin.shop_mobile.category.dao;

import android.app.Fragment;
import android.util.Log;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import ru.vetoshkin.shop_mobile.category.Category;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.fragments.BasketFragment;
import ru.vetoshkin.shop_mobile.fragments.FavoriteFragment;
import ru.vetoshkin.shop_mobile.fragments.ProfileFragment;
import ru.vetoshkin.shop_mobile.util.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





public class CategoryService {
    private static final String categoriesListUrl = "/category/list";
    private static List<Category> categories = Collections.emptyList();




    public static Fragment getFragment(Category category) {
        if (category == Category.FAVORITE)
            return new FavoriteFragment();

        if (category == Category.BASKET)
            return new BasketFragment();

        if (category == Category.PROFILE)
            return new ProfileFragment();

        return null;
    }


    public static void loadCategories(CallBack callBack) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url(AppConfig.getServerHost() + categoriesListUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Response response = new Response();
                response.setErrorMessage(e.getMessage());
                callBack.run(response);
            }


            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                buildCategories(response.body().string());
                callBack.run(new Response());
            }
        });
    }


    private static void buildCategories(String string) {
        try {
            CategoryResp resp = Json.toObject(string, CategoryResp.class);
            List<Category> temp = new ArrayList<>();
            temp.add(Category.HOME);

            temp.addAll(resp.getResult());

            temp.add(Category.FAVORITE);
            temp.add(Category.BASKET);
            temp.add(Category.PROFILE);
            temp.add(Category.LOGOUT);

            categories = temp;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            categories = Collections.emptyList();
        }
    }


    public static List<Category> getCategories() {
        return categories;
    }


    public static interface CallBack {
        public void run(Response response);
    }


    @Getter
    @Setter
    public static class Response {
        private String errorMessage;
    }


    private CategoryService() {

    }

}
