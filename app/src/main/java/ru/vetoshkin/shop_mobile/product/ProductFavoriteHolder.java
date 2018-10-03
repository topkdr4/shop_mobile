package ru.vetoshkin.shop_mobile.product;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ProductFavoriteHolder extends RecyclerView.ViewHolder {
    private TextView favoriteTitle;
    private ProductFavoriteAdapter adapter;
    private Product currentProduct;


    public ProductFavoriteHolder(View itemView, ProductFavoriteAdapter adapter) {
        super(itemView);

        this.favoriteTitle = itemView.findViewById(R.id.favorite_title);
        this.adapter = adapter;

        itemView.findViewById(R.id.is_favorite_favorite).setOnClickListener(v -> {
            int position = -1;
            for (int i = 0; i < adapter.getDataSource().size(); i++) {
                Product temp = adapter.getDataSource().get(i);
                if (currentProduct.getId().equals(temp.getId())) {
                    position = i;
                    break;
                }
            }

            if (position > -1) {
                adapter.getDataSource().remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }


    public void setState(Product product) {
        this.currentProduct = product;
        favoriteTitle.setText(product.getTitle());
    }
}
