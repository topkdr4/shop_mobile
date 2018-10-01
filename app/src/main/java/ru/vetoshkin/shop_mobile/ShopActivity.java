package ru.vetoshkin.shop_mobile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.category.Category;
import ru.vetoshkin.shop_mobile.category.dao.CategoryService;
import ru.vetoshkin.shop_mobile.product.Product;
import ru.vetoshkin.shop_mobile.product.ProductAdapter;
import ru.vetoshkin.shop_mobile.product.dao.ProductService;

import java.util.List;





public class ShopActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        /**
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                findViewById(R.id.drawer_layout));
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Category category = CategoryService.getCategories().get(position);
        Fragment newFragment = CategoryService.getFragment(category);
        newFragment = newFragment == null ? PlaceholderFragment.newInstance(category) : newFragment;

        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .commit();

        setTitle(category.getTitle());
    }



    /**
     * A placeholder fragment containing a simple view.
     */
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
        public void onStart() {
            super.onStart();

            List<Product> currentProducts = ProductService.getProducts(categoryId, page);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            productList = getActivity().findViewById(R.id.list_items);
            productList.setLayoutManager(layoutManager);

            ProductAdapter adapter = new ProductAdapter(productList, currentProducts);
            productList.setAdapter(adapter);


            productList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    // LAST
                    if (lastVisibleItem + 1 == totalItemCount) {
                        page++;

                        currentProducts.addAll(ProductService.getProducts(categoryId, page));

                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

}
