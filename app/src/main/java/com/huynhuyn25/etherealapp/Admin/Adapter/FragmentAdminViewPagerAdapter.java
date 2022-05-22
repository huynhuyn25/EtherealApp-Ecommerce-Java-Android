package com.huynhuyn25.etherealapp.Admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huynhuyn25.etherealapp.Admin.AdminFragment.OrderFragment;
import com.huynhuyn25.etherealapp.Admin.AdminFragment.ProductFragment;
import com.huynhuyn25.etherealapp.Admin.AdminFragment.ThongkeFragment;

public class FragmentAdminViewPagerAdapter extends FragmentStatePagerAdapter {
    private int pageNum;
    public FragmentAdminViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pageNum=behavior;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new OrderFragment();
            case 1: return new ProductFragment();
            case 2: return new ThongkeFragment();
            default:return new OrderFragment();
        }
    }
    @Override
    public int getCount() {
        return pageNum;
    }
}
