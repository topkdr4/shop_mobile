package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;





public class ProductCard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_card);

        ViewPager pager = findViewById(R.id.pager);
        ProductCardPageAdapter adapter = new ProductCardPageAdapter(this);
        pager.setAdapter(adapter);

        TextView description = findViewById(R.id.product_description);
        description.setText(Html.fromHtml(description.getText().toString()));

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Басятский велик");
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

}
