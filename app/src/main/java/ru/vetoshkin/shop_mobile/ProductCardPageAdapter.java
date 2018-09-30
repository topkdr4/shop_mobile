package ru.vetoshkin.shop_mobile;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RequiredArgsConstructor
public class ProductCardPageAdapter extends PagerAdapter {

    private final Context context;

    private List<Integer> images = Arrays.asList(
            R.drawable.bike_menu_icon,
            R.drawable.bike2,
            R.drawable.bike3
    );



    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images.get(position));
        container.addView(imageView, 0);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        container.removeView((ImageView) obj);
    }
}
