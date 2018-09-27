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



    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.product_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItems, parent, false);
        return new ProductHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        Log.i("ADAPTER: ", "" + ProductService.getTop_products().size());
        return ProductService.getTop_products().size();
    }
}
