package com.huynhuyn25.etherealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.huynhuyn25.etherealapp.Adapter.FragmentViewPagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestPermission();
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        viewPager=findViewById(R.id.fragment_viewPager);
        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(getSupportFragmentManager(),5);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch(position){
                    case 0:bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);

                        break;

                    case 1:bottomNavigationView.getMenu().findItem(R.id.navigation_shopping).setChecked(true);

                        break;

                    case 2:bottomNavigationView.getMenu().findItem(R.id.navigation_qr_scan).setChecked(true);

                        break;

                    case 3:bottomNavigationView.getMenu().findItem(R.id.navigation_contact).setChecked(true);

                        break;
                    case 4:bottomNavigationView.getMenu().findItem(R.id.navigation_person).setChecked(true);

                        break;
                    default:bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);

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
                   case R.id.navigation_home:viewPager.setCurrentItem(0);
                       break;
                   case R.id.navigation_shopping:viewPager.setCurrentItem(1);
                       break;
                   case R.id.navigation_qr_scan:viewPager.setCurrentItem(2);
                       break;
                   case R.id.navigation_contact:viewPager.setCurrentItem(3);
                       break;
                   case R.id.navigation_person:viewPager.setCurrentItem(4);
                       break;
                   default:viewPager.setCurrentItem(0);
                       break;
               }
               return true;
           }
       });
    }

    public void RequestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }


        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

}