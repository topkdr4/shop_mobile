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
        Log.e("SELECTED CATEGORY: ", category.toString());
        Fragment newFragment = CategoryService.getFragment(category);
        newFragment = newFragment == null ? PlaceholderFragment.newInstance(position + 1) : newFragment;

        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .commit();

        setTitle(category.getTitle());
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }


        static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_shop, container, false);
        }


        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            RecyclerView productList = getActivity().findViewById(R.id.list_items);
            productList.setLayoutManager(layoutManager);

            ProductAdapter adapter = new ProductAdapter(productList);
            productList.setAdapter(adapter);


            productList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (lastVisibleItem + 1 == totalItemCount) {


                        for (int i = 0; i < 10; i++) {
                            ProductService.getTop_products().add(new Product());
                        }

                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }


        @Override
        public void onAttach(Activity context) {
            super.onAttach(context);


            /**/

        }
    }

}
