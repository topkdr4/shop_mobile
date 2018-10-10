package ru.vetoshkin.shop_mobile.product;
import android.graphics.Bitmap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;





@Getter
@Setter
public class Product {
    /**
     * Идентификатор
     */
    private String id;

    /**
     * Название
     */
    private String title;

    /**
     * Описание
     */
    private String description;

    /**
     * Категория
     */
    private int category;

    /**
     * Изображения
     */
    private List<Integer> images;

    /**
     * Цена
     */
    private float price;

    /**
     * Избрнанное
     */
    private transient boolean isFavorite;

    /**
     * Превью
     */
    private transient Bitmap previewImage;


    @Override
    public String toString() {
        return this.title;
    }
}
