package ru.vetoshkin.shop_mobile.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.basket.Basket;
import ru.vetoshkin.shop_mobile.basket.BasketAdapter;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.ProductAdapter;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class BasketFragment extends Fragment {


    public BasketFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView basketItems = getActivity().findViewById(R.id.basket_items_list);
        basketItems.setLayoutManager(layoutManager);

        basketItems.setAdapter(new BasketAdapter(basketItems, this));
        updateSum();
    }


    public void updateSum() {
        TextView sum = getActivity().findViewById(R.id.basket_sum);
        sum.setText(Html.fromHtml("<b>Итого: </b>" + Basket.getSum() + " р."));
    }
}
