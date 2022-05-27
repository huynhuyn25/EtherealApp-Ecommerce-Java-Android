package com.huynhuyn25.etherealapp.Admin.AdminFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Admin.AddProductActivity;
import com.huynhuyn25.etherealapp.Admin.UpdateDeleteProductActivity;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Product;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewProduct;
    private SearchView searchView;
    private FloatingActionButton floatingActionButton;
    ArrayList<Product> listProduct = new ArrayList<>();
    private ProductRecycleViewAdapter productRecycleViewAdapter;

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
        View view = inflater.inflate(R.layout.fragment_product2, container, false);
        getWidget(view);
        getListProduct();
        setEvent();

        Search(view);

        return view;
    }

    public void getWidget(View view) {
        recyclerViewProduct = view.findViewById(R.id.list);
        floatingActionButton = view.findViewById(R.id.fab);
        searchView = view.findViewById(R.id.search);
//        spinnerCategory = view.findViewById(R.id.spinner_category);
//        spinnerSort = view.findViewById(R.id.spinner_sort);
    }

    public void getListProduct() {
        ProductAPI.productAPI.getAllProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                try {
                    if (!response.isSuccessful() || response.body().size() == 0)
                        throw new Exception();
                    listProduct = response.body();
                    if (productRecycleViewAdapter == null) {
                        setRecyclerViewProductAdapter();
                    } else {
                        Log.d("TAG", "onResponse: setlist");
                        productRecycleViewAdapter.setList(listProduct);
                    }

                } catch (Exception e) {
                    Log.d("test", "error_null");
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("test", "loi");
                throwable.printStackTrace();
                Toast.makeText(getActivity(), "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setRecyclerViewProductAdapter() {
        productRecycleViewAdapter = new ProductRecycleViewAdapter(listProduct, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProduct.get(pos);
                Intent intent = new Intent(getContext(), UpdateDeleteProductActivity.class);
                intent.putExtra("product", product);
                getContext().startActivity(intent);
            }
        });
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewProduct.setAdapter(productRecycleViewAdapter);

    }

    public void setEvent() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListProduct();
    }

    public void Search(View v) {
        SearchManager searchManager = (SearchManager) v.getContext().getSystemService(Context.SEARCH_SERVICE);

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

}