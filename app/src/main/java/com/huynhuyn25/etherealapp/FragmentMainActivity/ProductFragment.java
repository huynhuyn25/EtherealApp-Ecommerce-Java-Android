package com.huynhuyn25.etherealapp.FragmentMainActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Adapter.PhotoSliderViewPagerAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.ChangeInforActivity;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.MainActivity;
import com.huynhuyn25.etherealapp.Model.Category;
import com.huynhuyn25.etherealapp.Model.CategoryPhoto;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.Presenter.ProductPresenter;
import com.huynhuyn25.etherealapp.ProductDetailActivity;
import com.huynhuyn25.etherealapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerViewProduct;
    private SearchView searchView;
    private ProductRecycleViewAdapter productRecycleViewAdapter;
    private Spinner spinnerCategory;
    private Spinner spinnerSort;
    ArrayList<Product> listProduct;
    ArrayList<String> listCategory;
    ArrayList<String> listSort;
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterSort;
    ProductPresenter productPresenter= new ProductPresenter();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        getWidget(view);
        getListProduct();
        setSpinnerAdapter();



        return view;
    }
    public void getWidget(View view){
        recyclerViewProduct = view.findViewById(R.id.list_product);
        searchView = view.findViewById(R.id.search);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        spinnerSort = view.findViewById(R.id.spinner_sort);
    }
    public void setEvent(){

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                if(i==1){

                    ArrayList<Product> listProductNew = new ArrayList<>();
                    listProductNew = productPresenter.sortThaptocao(listProduct);
                    productRecycleViewAdapter.setList(listProductNew);
                }else if(i==2){
                    ArrayList<Product> listProductNew = new ArrayList<>();
                    listProductNew = productPresenter.sortcaotoThap(listProduct);
                    productRecycleViewAdapter.setList(listProductNew);
                }else{
                    productRecycleViewAdapter.setList(listProduct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                String s = spinnerCategory.getSelectedItem().toString();
                ArrayList<Product> listCategory = new ArrayList<>();
                if(!s.equals("All")) {
                    listCategory = productPresenter.locCategory(listProduct, s);
                    productRecycleViewAdapter.setList(listCategory);
                }else{
                    productRecycleViewAdapter.setList(listProduct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void getListProduct(){
        ProductAPI.productAPI.getAllProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                try {
                    if(!response.isSuccessful()||response.body().size()==0)
                        throw new Exception();
                    listProduct=response.body();
                    if(productRecycleViewAdapter==null){
                        setRecyclerViewProductAdapter();
                        Search();
                        setEvent();
                    }else {
                        Log.d("TAG", "onResponse: setlist");
                        productRecycleViewAdapter.setList(listProduct);
                    }

                }catch (Exception e){
                    Log.d("test","error_null");
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(getActivity(), "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setRecyclerViewProductAdapter(){
        productRecycleViewAdapter = new ProductRecycleViewAdapter(listProduct, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProduct.get(pos);
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("product", product);
                getContext().startActivity(intent);
            }
        });
        recyclerViewProduct.setAdapter(productRecycleViewAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
    public void Search(){
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productRecycleViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productRecycleViewAdapter.getFilter().filter(newText);
                return false;
            }

        });
    }
    public void setSpinnerAdapter(){
        listCategory = new ArrayList<>();
        listSort = new ArrayList<>();
        listCategory.add("All");
        listCategory.add("Makeup");
        listCategory.add("Skincare");
        listCategory.add("Bodycare");

        listSort.add("Sản phâm nổi bật");
        listSort.add("Giá từ thấp đến cao");
        listSort.add("Giá từ cao đến thấp");

        adapterCategory = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listCategory);
        adapterSort = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,listSort);

        spinnerCategory.setAdapter(adapterCategory);
        spinnerSort.setAdapter(adapterSort);
    }
    @Override
    public void onResume() {
        super.onResume();
        getListProduct();
    }
}