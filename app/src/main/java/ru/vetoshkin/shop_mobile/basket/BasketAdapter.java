package ru.vetoshkin.shop_mobile.basket;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.fragments.BasketFragment;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class BasketAdapter extends RecyclerView.Adapter<BasketItemHolder> {
    private final RecyclerView basketItems;
    private final BasketFragment fragment;

    public BasketAdapter(RecyclerView basketItems, BasketFragment basketFragment) {
        this.basketItems = basketItems;
        this.fragment = basketFragment;
    }


    @Override
    public BasketItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.basket_list_item, parent, false);
        return new BasketItemHolder(view, this, fragment);
    }


    @Override
    public void onBindViewHolder(BasketItemHolder holder, int position) {
        holder.setState(Basket.selectedProduct.get(position));
    }


    @Override
    public int getItemCount() {
        return Basket.selectedProduct.size();
    }
}
