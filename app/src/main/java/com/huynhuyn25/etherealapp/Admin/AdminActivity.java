package com.huynhuyn25.etherealapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cloudinary.android.MediaManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huynhuyn25.etherealapp.Admin.Adapter.FragmentAdminViewPagerAdapter;
import com.huynhuyn25.etherealapp.R;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        viewPager=findViewById(R.id.fragment_viewPager);

        FragmentAdminViewPagerAdapter adapter=new FragmentAdminViewPagerAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch(position){
                    case 0:bottomNavigationView.getMenu().findItem(R.id.mOrder).setChecked(true);

                        break;

                    case 1:bottomNavigationView.getMenu().findItem(R.id.mProduct).setChecked(true);

                        break;

                    case 2:bottomNavigationView.getMenu().findItem(R.id.mThongke).setChecked(true);

                        break;

                    default:bottomNavigationView.getMenu().findItem(R.id.mOrder).setChecked(true);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mOrder:viewPager.setCurrentItem(0);
                        break;
                    case R.id.mProduct:viewPager.setCurrentItem(1);
                        break;
                    case R.id.mThongke:viewPager.setCurrentItem(2);
                        break;
                    default:viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });


    }

}