package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.*;
import ru.vetoshkin.shop_mobile.basket.Basket;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.dao.ProductResp;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;
import ru.vetoshkin.shop_mobile.util.Json;
import ru.vetoshkin.shop_mobile.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;





public class ProductCard extends Activity {
    public static final String PRODUCT_ID   = "PRODUCT_ID";
    private static final String PRICE_TITLE = "<b>Цена: </b>";
    private Product currentProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            if (intent == null)
                return;

            currentProduct = Json.toObject(intent.getExtras().getString(PRODUCT_ID), Product.class);
            setContentView(R.layout.activity_product_card);

            TextView description = findViewById(R.id.product_description);
            String descriptionProduct = currentProduct.getDescription();
            Log.e("DESC", String.valueOf(descriptionProduct));
            description.setText(Html.fromHtml(Util.isEmpty(descriptionProduct) ? "" : descriptionProduct));

            TextView itemPrice = findViewById(R.id.item_price);
            itemPrice.setText(Html.fromHtml(PRICE_TITLE + currentProduct.getPrice() + " р.").toString());

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(currentProduct.getTitle());


            ViewPager pager = findViewById(R.id.pager);

            List<Integer> ids = new ArrayList<>(3);
            for (Integer imgId : currentProduct.getImages()) {
                if (imgId == null)
                    continue;

                ids.add(imgId);
            }

            if (ids.size() == 0)
                return;

            AtomicInteger atom = new AtomicInteger(ids.size());
            List<byte[]> imagesOfProduct = new CopyOnWriteArrayList<>();

            for (Integer imgId : ids) {
                final OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(AppConfig.getServerHost() + "/product/image/" + imgId)
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("IMG", "FAIl");
                        atom.decrementAndGet();
                    }


                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        Log.e("IMG", "SUCCESS");
                        imagesOfProduct.add(response.body().bytes());
                        atom.decrementAndGet();
                    }
                });
            }

            do {
                // wait
            } while (atom.get() != 0);

            ProductCardPageAdapter adapter = new ProductCardPageAdapter(this, currentProduct, imagesOfProduct);
            pager.setAdapter(adapter);
        } catch (Throwable e) {
            Log.e(e.getClass().getCanonicalName(), Util.getStackTrace(e));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void add(View view) {
        Basket.put(currentProduct);
        Basket.printBasket(this);
    }
}
