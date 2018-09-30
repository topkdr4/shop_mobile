package ru.vetoshkin.shop_mobile.product;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.basket.Basket;





public class ProductHolder extends RecyclerView.ViewHolder {
    private Product  currentProduct;
    private Context  context;
    private Button   bikePrice;
    private TextView bikeName;
    private ImageView imageView;


    public ProductHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();

        this.bikePrice = itemView.findViewById(R.id.bike_price);
        this.bikeName  = itemView.findViewById(R.id.bike_name);

        this.imageView = itemView.findViewById(R.id.bike_favorite);
    }


    public void setState(Product product) {
        this.currentProduct = product;

        this.bikeName.setText(currentProduct.getTitle());

        this.bikePrice.setText(String.valueOf(currentProduct.getPrice()));
        this.bikePrice.setOnClickListener(view -> {
            Basket.put(currentProduct);
            Basket.printBasket();
        });

        this.imageView.setOnClickListener(view -> {
            ImageView current = (ImageView) view;
            boolean newFavorite = "0".equals(String.valueOf(current.getTag()));
            product.setFavorite(newFavorite);
            setFavoriteIcon(current, newFavorite, product.getId());
        });

        boolean isFavorite = context.getSharedPreferences(favoritePreference, Context.MODE_PRIVATE).getBoolean(product.getId(), false) || product.isFavorite();
        product.setFavorite(isFavorite);
        setImage(imageView, isFavorite);
    }


    private static final int notFavoriteTag = 0;
    private static final int favoriteTag = 1;

    private static final int notFavoriteImage = R.drawable.not_favorite;
    private static final int favoriteImage = R.drawable.favorite;

    private static final String favoritePreference = "favorite";

    private static void setFavoriteIcon(ImageView imageView, boolean isFavorite, String productId) {
        Context context = imageView.getContext();
        SharedPreferences prefs = context.getSharedPreferences(favoritePreference, Context.MODE_PRIVATE);

        setImage(imageView, isFavorite);

        SharedPreferences.Editor editor = prefs.edit();
        if (!isFavorite) {
            editor.remove(productId);
        } else {
            editor.putBoolean(productId, true);
        }

        editor.commit();
    }

    private static void setImage(ImageView imageView, boolean isFavorite) {
        imageView.setTag(isFavorite ? favoriteTag : notFavoriteTag);
        imageView.setImageResource(isFavorite ? favoriteImage : notFavoriteImage);
    }
}
