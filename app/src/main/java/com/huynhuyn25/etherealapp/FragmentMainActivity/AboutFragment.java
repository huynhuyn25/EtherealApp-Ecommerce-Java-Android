package com.huynhuyn25.etherealapp.FragmentMainActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huynhuyn25.etherealapp.Adapter.PhotoSliderViewPagerAdapter;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {
    private ViewPager2 sliderViewPager2;
    private CircleIndicator3 sliderIndicator3;
    ArrayList<SliderPhoto> listSliderPhoto;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        getWidget(view);
        getListSliderPhoto();
        setSliderAdapter();
        return view;
    }
    public void getWidget(View view){
        sliderViewPager2 = view.findViewById(R.id.slider_viewPager);
        sliderIndicator3 =view.findViewById(R.id.slider_indicator);
    }
    public void getListSliderPhoto(){
        listSliderPhoto = new ArrayList<>();
//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner4));
//        //Photo photo = new Photo("image","png",null);
//        //Log.d("test", photo.toString());
//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner5));
//        listSliderPhoto.add(new SliderPhoto(R.drawable.banner7));
    }
    public void setSliderAdapter(){
        PhotoSliderViewPagerAdapter photoSliderViewPagerAdapter = new PhotoSliderViewPagerAdapter(getActivity(),listSliderPhoto);
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
    }
}