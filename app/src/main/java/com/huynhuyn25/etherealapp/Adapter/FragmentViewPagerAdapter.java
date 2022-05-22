package com.huynhuyn25.etherealapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huynhuyn25.etherealapp.FragmentMainActivity.AboutFragment;
import com.huynhuyn25.etherealapp.FragmentMainActivity.HomeFragment;
import com.huynhuyn25.etherealapp.FragmentMainActivity.MyFragment;
import com.huynhuyn25.etherealapp.FragmentMainActivity.ProductFragment;
import com.huynhuyn25.etherealapp.FragmentMainActivity.ScanFragment;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    private int pageNum;
    public FragmentViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pageNum=behavior;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new HomeFragment();
            case 1: return new ProductFragment();
            case 2: return new ScanFragment();
            case 3: return new AboutFragment();
            default:return new MyFragment();
        }
    }
    @Override
    public int getCount() {
        return pageNum;
    }
}
