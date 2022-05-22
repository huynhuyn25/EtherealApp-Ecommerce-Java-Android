package com.huynhuyn25.etherealapp.Adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.huynhuyn25.etherealapp.Fragment.PhotoSliderFragment;
import com.huynhuyn25.etherealapp.Model.Photo;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;

import java.util.ArrayList;

public class PhotoSliderViewPagerAdapter extends FragmentStateAdapter {
    private ArrayList<SliderPhoto> listPhoto;
    public PhotoSliderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<SliderPhoto> listPhoto) {
        super(fragmentActivity);
        this.listPhoto=listPhoto;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("TAG", "createFragment: huhu");
        SliderPhoto photo = listPhoto.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_photo",photo);
        PhotoSliderFragment photoSliderFragment = new PhotoSliderFragment();
        photoSliderFragment.setArguments(bundle);


        return photoSliderFragment;
    }

    @Override
    public int getItemCount() {
        if(listPhoto!=null){
            return listPhoto.size();
        }
        return 0;
    }
}
