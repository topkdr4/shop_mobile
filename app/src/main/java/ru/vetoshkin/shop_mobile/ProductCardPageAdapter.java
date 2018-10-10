package ru.vetoshkin.shop_mobile;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import lombok.RequiredArgsConstructor;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.util.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RequiredArgsConstructor
public class ProductCardPageAdapter extends PagerAdapter {
    private final Context context;
    private final Product currentProduct;
    private final List<byte[]> productImages;
    private final Map<Integer, Bitmap> cache = new ConcurrentHashMap<>();


    @Override
    public int getCount() {
        return productImages.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        try {
            byte[] img = productImages.get(position);

            if (img.length == 0) {
                imageView.setImageResource(R.drawable.bike_menu_icon);
            } else {
                Bitmap bmp = cache.get(position);
                if (bmp == null) {
                    bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                    cache.put(position, bmp);
                }

                imageView.setImageBitmap(bmp);
            }

            if (position == 0) {
                if (currentProduct.getPreviewImage() == null) {
                    currentProduct.setPreviewImage(((BitmapDrawable)imageView.getDrawable()).getBitmap());
                }
            }

            container.addView(imageView, 0);
        } catch (Throwable throwable) {
            Log.e(throwable.getClass().getCanonicalName(), Util.getStackTrace(throwable));
        }

        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        container.removeView((ImageView) obj);
    }
}
