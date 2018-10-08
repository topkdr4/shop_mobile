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
import ru.vetoshkin.shop_mobile.basket.Basket;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;
import ru.vetoshkin.shop_mobile.util.Json;

import java.io.IOException;





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

            ViewPager pager = findViewById(R.id.pager);
            ProductCardPageAdapter adapter = new ProductCardPageAdapter(this);
            pager.setAdapter(adapter);

            TextView description = findViewById(R.id.product_description);
            description.setText(Html.fromHtml(description.getText().toString()));

            TextView itemPrice = findViewById(R.id.item_price);
            itemPrice.setText(Html.fromHtml(PRICE_TITLE + currentProduct.getPrice() + " р.").toString());

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(currentProduct.getTitle());
        } catch (IOException e) {
            Log.e("CARD", e.getMessage());
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
