package ru.vetoshkin.shop_mobile.product;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;





public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private static int holdersCount = 0;



    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.product_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItems, parent, false);
        ProductHolder viewHolder = new ProductHolder(view);
        viewHolder.holder.setText("View holder index: " + holdersCount);
        holdersCount++;

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Log.i("BINDER: ", "" + ProductService.getTop_products().size() + " | " + position);
        holder.bind(position);
        if (position + 4 == ProductService.getTop_products().size()) {
            for (int i = 0; i < 14; i++) {
                ProductService.getTop_products().add(new Product());
            }


            //notifyDataSetChanged();
        }

    }


    @Override
    public int getItemCount() {
        Log.i("ADAPTER: ", "" + ProductService.getTop_products().size());
        return ProductService.getTop_products().size();
    }
}
