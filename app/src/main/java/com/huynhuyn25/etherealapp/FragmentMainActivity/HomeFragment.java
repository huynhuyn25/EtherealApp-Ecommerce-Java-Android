package com.huynhuyn25.etherealapp.FragmentMainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.APIResource.SliderPhotoAPI;
import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Adapter.PhotoSliderViewPagerAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.MainActivity;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.ProductDetailActivity;
import com.huynhuyn25.etherealapp.R;
import com.huynhuyn25.etherealapp.SignInActivity;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ProductRecycleViewAdapter productRecycleViewAdapter;
    private ViewPager2 sliderViewPager2;
    private CircleIndicator3 sliderIndicator3;
    private RecyclerView recyclerViewProduct;
    ArrayList<SliderPhoto> listSliderPhoto;
    ArrayList<Product> listProduct;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPossion = sliderViewPager2.getCurrentItem();
            if(currentPossion ==listSliderPhoto.size()-1){
                sliderViewPager2.setCurrentItem(0);
            } else {
                sliderViewPager2.setCurrentItem(currentPossion+1);
            }

        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            getWidget(view);
            getListSliderPhoto();
            Log.d("TAG", "onCreateView: hehe");
            getListProduct();

        }catch(Exception e){
            e.printStackTrace();
        }

        return view;
    }
    public void getWidget(View view){
        sliderViewPager2 = view.findViewById(R.id.slider_viewPager);
        sliderIndicator3 =view.findViewById(R.id.slider_indicator);
        recyclerViewProduct = view.findViewById(R.id.list_product);
    }
    public void getListSliderPhoto(){
        listSliderPhoto = new ArrayList<>();
        try {
            SliderPhotoAPI.sliderPhotoAPI.getAllSlider().enqueue(new Callback<ArrayList<SliderPhoto>>() {
                @Override
                public void onResponse(Call<ArrayList<SliderPhoto>> call, Response<ArrayList<SliderPhoto>> response) {
                    try {
                        if(!response.isSuccessful()||response.body().size()==0)
                            throw new Exception();
                        listSliderPhoto = response.body();
                        Log.d("test",response.body().get(0).toString());
                        setSliderAdapter();
                    }catch (Exception e){
                        Log.d("test","error_null");

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<SliderPhoto>> call, Throwable throwable) {
                    Log.d("test","loi");
                    throwable.printStackTrace();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner4));
//        //Photo photo = new Photo("image","png",null);
//        //Log.d("test", photo.toString());
//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner5));
//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner7));
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
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getActivity(),2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }
    public void getListProduct(){
        listProduct = new ArrayList<>();
        ProductAPI.productAPI.getAllProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                try {
                    if(!response.isSuccessful()||response.body().size()==0)
                        throw new Exception();
                    listProduct=response.body();
                    setRecyclerViewProductAdapter();
                }catch (Exception e){
                    Log.d("test","error_null");
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
    public void setSliderAdapter(){
        try {
            Log.d("TAG", "setSliderAdapter: huhu");
            PhotoSliderViewPagerAdapter photoSliderViewPagerAdapter = new PhotoSliderViewPagerAdapter(getActivity(),listSliderPhoto);
            Log.d("TAG", "setSliderAdapter: huhu");
            sliderViewPager2.setAdapter(photoSliderViewPagerAdapter);
            sliderIndicator3.setViewPager(sliderViewPager2);

            sliderViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,3000);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

    }


}