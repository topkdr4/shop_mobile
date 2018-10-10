package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.category.Category;
import ru.vetoshkin.shop_mobile.category.dao.CategoryService;
import ru.vetoshkin.shop_mobile.config.AppConfig;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.ProductAdapter;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;

import java.util.ArrayList;
import java.util.List;





public class ShopActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                findViewById(R.id.drawer_layout));
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Category category = CategoryService.getCategories().get(position);
        if (category == Category.LOGOUT) {
            getSharedPreferences(AppConfig.APP_CONFIG, Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        Fragment newFragment = CategoryService.getFragment(category);
        newFragment = newFragment == null ? PlaceholderFragment.newInstance(category) : newFragment;

        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .commit();

        setTitle(category.getTitle());
    }



    @Override
    public void onBackPressed() {
        finishAffinity();
    }



    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


        static PlaceholderFragment newInstance(Category category) {
            PlaceholderFragment result = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("CATEGORY_ID", category.getId());
            result.setArguments(args);
            return result;
        }


        private RecyclerView productList;
        private int page = 1;
        private String categoryId;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            if (args != null) {
                categoryId = args.getString("CATEGORY_ID");
            }

            return inflater.inflate(R.layout.fragment_shop, container, false);
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            productList = getActivity().findViewById(R.id.list_items);
            productList.setLayoutManager(layoutManager);

            List<Product> products = new ArrayList<>();
            ProductAdapter adapter = new ProductAdapter(productList, products);
            productList.setAdapter(adapter);


            ProductService.getProducts(categoryId, page, currentProducts -> {
                products.addAll(currentProducts);
                adapter.notifyDataSetChanged();

                if (categoryId.equals(Category.HOME.getId()))
                    return;

                productList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = layoutManager.getItemCount();
                        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                        // LAST
                        if (lastVisibleItem + 1 == totalItemCount) {
                            page++;

                            ProductService.getProducts(categoryId, page, currentProducts::addAll);

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            });
        }
    }

}
