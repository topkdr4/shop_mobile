package ru.vetoshkin.shop_mobile.product;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;





public class ProductHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public TextView holder;


    public ProductHolder(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.ebota_item);
        holder = itemView.findViewById(R.id.holder_item);
    }


    public void bind(int index) {
        textView.setText(String.valueOf(index));
    }
}
