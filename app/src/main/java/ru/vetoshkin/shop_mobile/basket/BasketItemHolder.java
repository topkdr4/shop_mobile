package ru.vetoshkin.shop_mobile.basket;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.fragments.BasketFragment;
import ru.vetoshkin.shop_mobile.product.Product;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class BasketItemHolder extends RecyclerView.ViewHolder {
    /**
     * Наименование продукта
     * item_title
     */
    private final TextView title;

    /**
     * Сумма товара
     * item_sum
     */
    private final TextView sum;

    /**
     * Кнопка добавления
     * add_to_basket
     */
    private final Button add;

    /**
     * Количество продукта в корзине
     * item_count
     */
    private final TextView count;

    /**
     * Кнопка Уменьшения
     * remove_from_basket
     */
    private final Button remove;

    /**
     * Контекст
     */
    private final Context context;

    /**
     * Текущий продукт
     */
    private Product product;

    /**
     * Адаптер
     */
    private final BasketAdapter adapter;

    /**
     * Корень
     */
    private final BasketFragment rootFragment;



    public BasketItemHolder(View itemView, BasketAdapter adapter, BasketFragment basketFragment) {
        super(itemView);

        this.adapter = adapter;
        this.rootFragment = basketFragment;

        this.title   = itemView.findViewById(R.id.item_title);
        this.sum     = itemView.findViewById(R.id.item_sum);
        this.add     = itemView.findViewById(R.id.add_to_basket);
        this.count   = itemView.findViewById(R.id.item_count);
        this.remove  = itemView.findViewById(R.id.remove_from_basket);
        this.context = itemView.getContext();

        add.setOnClickListener(v -> {
            if (product == null)
                return;

            Basket.put(product);
            setState(product);
            rootFragment.updateSum();
        });

        remove.setOnClickListener(v -> {
            if (product == null)
                return;

            int count = Basket.getProductCount(product);
            int position = -1;

            if (count == 1) {
                for (int i = 0; i < Basket.selectedProduct.size(); i++) {
                    Product selectedProduct = Basket.selectedProduct.get(i);
                    if (selectedProduct.getId().equals(product.getId())) {
                        position = i;
                        break;
                    }
                }
            }

            Basket.removePart(product);

            // Последний элемент
            if (count == 1 && position > -1) {
                adapter.notifyItemRemoved(position);
                rootFragment.updateSum();
                return;
            }

            setState(product);
            rootFragment.updateSum();
        });
    }


    public void setState(Product product) {
        this.product = product;

        title.setText(product.getTitle());
        sum.setText(String.valueOf(Basket.getProductCount(product) * product.getPrice()));
        count.setText(String.valueOf(Basket.getProductCount(product)));
    }
}
