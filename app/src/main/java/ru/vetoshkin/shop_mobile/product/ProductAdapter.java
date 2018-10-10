package ru.vetoshkin.shop_mobile.product;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.ProductCard;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.util.Json;

import java.util.List;





public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private RecyclerView recyclerView;
    private List<Product> dataSource;


    public ProductAdapter(RecyclerView recyclerView, List<Product> dataSource) {
        this.recyclerView = recyclerView;
        this.dataSource   = dataSource;
    }


    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.product_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItems, parent, false);
        view.setOnClickListener(v -> {
            try {
                int position = recyclerView.getChildLayoutPosition(v);
                Intent productInfo = new Intent(recyclerView.getContext(), ProductCard.class);
                Log.e("ON CLICK", Json.toJson(dataSource.get(position)));
                productInfo.putExtra(ProductCard.PRODUCT_ID, Json.toJson(dataSource.get(position)));
                recyclerView.getContext().startActivity(productInfo);
            } catch (Exception e) {
                Log.e("PRODUCT_ADAPTER", e.getMessage());
            }
        });
        return new ProductHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        holder.setState(dataSource.get(position));
    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
