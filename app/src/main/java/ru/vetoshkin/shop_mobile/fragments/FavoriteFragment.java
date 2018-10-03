package ru.vetoshkin.shop_mobile.fragments;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.ProductFavoriteAdapter;
import ru.vetoshkin.shop_mobile.product.ProductHolder;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        List<Product> favoriteProducts = ProductService.getFavorites(getActivity().getSharedPreferences(ProductHolder.favoritePreference, Context.MODE_PRIVATE));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        RecyclerView productList = getActivity().findViewById(R.id.favorite_list);
        ProductFavoriteAdapter adapter = new ProductFavoriteAdapter(favoriteProducts);

        productList.setLayoutManager(layoutManager);
        productList.setAdapter(adapter);
    }
}

