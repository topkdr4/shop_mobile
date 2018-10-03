package ru.vetoshkin.shop_mobile.product;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lombok.Getter;
import ru.vetoshkin.shop_mobile.R;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ProductFavoriteAdapter extends RecyclerView.Adapter<ProductFavoriteHolder> {
    @Getter private List<Product> dataSource;


    public ProductFavoriteAdapter(List<Product> dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public ProductFavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new ProductFavoriteHolder(view, this);
    }


    @Override
    public void onBindViewHolder(ProductFavoriteHolder holder, int position) {
        holder.setState(dataSource.get(position));
    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
