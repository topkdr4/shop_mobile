package ru.vetoshkin.shop_mobile.category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.category.dao.CategoryService;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class CategoryAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return CategoryService.getCategories().size();
    }


    @Override
    public Object getItem(int position) {
        return CategoryService.getCategories().get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.menu_list_item, null);

        Category category = (Category) getItem(position);

        TextView text = vi.findViewById(R.id.text);
        text.setText(category.getTitle());

        ImageView icon = vi.findViewById(R.id.menu_icon);
        icon.setImageResource(category.getIconResource());

        return vi;
    }
}
