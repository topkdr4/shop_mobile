package ru.vetoshkin.shop_mobile.product;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;





public class ProductHolder extends RecyclerView.ViewHolder {

    private static final View.OnClickListener clickListener = view -> {
        ImageView current = (ImageView) view;

        Integer imageTag = null;
        try {
            imageTag = Integer.parseInt(current.getTag().toString());
        } catch (Exception e) {
            Log.e("F", e.getMessage());
        }

        if (imageTag == null)
            return;

        switch (imageTag) {
            case 1:
                current.setTag(0);
                current.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                break;

            case 0:
                current.setTag(1);
                current.setImageResource(R.drawable.ic_baseline_favorite_24px);
                break;
        }
    };

    private Product  currentProduct;
    private Button   bikePrice;
    private TextView bikeName;

    public ProductHolder(View itemView) {
        super(itemView);

        ImageView imageView = itemView.findViewById(R.id.bike_favorite);
        imageView.setOnClickListener(clickListener);

        this.bikePrice = itemView.findViewById(R.id.bike_price);
        this.bikeName  = itemView.findViewById(R.id.bike_name);
    }


    public void setState(Product product) {
        this.currentProduct = product;

        this.bikePrice.setText(String.valueOf(currentProduct.getPrice()));
        this.bikeName.setText(currentProduct.getTitle());
    }
}
