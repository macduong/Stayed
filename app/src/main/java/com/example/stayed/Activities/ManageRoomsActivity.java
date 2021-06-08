package com.example.stayed.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.stayed.CustomAdapter.RoomListAdapter;
import com.example.stayed.CustomAdapter.RoomViewPagerAdapter;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.Rooms;
import com.example.stayed.R;
import com.example.stayed.databinding.ManageRoomsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ManageRoomsActivity extends AppCompatActivity {
    private ManageRoomsBinding binding;
    private RoomViewPagerAdapter viewPagerAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isFABOpen = false;
    private SharedPreferences sharedPreferences;
    private DB db;
    private int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getBaseContext(), "Welcome back, Master", Toast.LENGTH_SHORT).show();
        binding = DataBindingUtil.setContentView(this, R.layout.manage_rooms);
        viewPagerAdapter = new RoomViewPagerAdapter(getSupportFragmentManager(), RoomViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.mngroomviewpager.setAdapter(viewPagerAdapter);
        db = new DB(getBaseContext());

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        accountId = sharedPreferences.getInt("accountId", 0);

        binding.mngroomviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.mngroombottom.getMenu().findItem(R.id.bottom1).setChecked(true);
                        break;
                    case 1:
                        binding.mngroombottom.getMenu().findItem(R.id.bottom2).setChecked(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.mngroombottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom1:
                        binding.mngroomviewpager.setCurrentItem(0);
                        break;
                    case R.id.bottom2:
                        binding.mngroomviewpager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

        binding.mngroommenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        binding.mngroommenulogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountId = 0;
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        binding.mngroommenuaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ManagerInfo.class));
            }
        });

        binding.mngroommenuservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ManageServicesActivity.class));
            }
        });

        binding.mngroommenuallmanagers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ListAllManagers.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeFABMenu();
    }

    @Override
    public void onBackPressed() {

        if (!isFABOpen) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            closeFABMenu();
        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        binding.mngroommenulogout.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        binding.mngroommenuaccount.animate().translationY(-getResources().getDimension(R.dimen.standard_110));
        binding.mngroommenuservice.animate().translationY(-getResources().getDimension(R.dimen.standard_160));
        if (db.searchOneManagerById(accountId).getIsboss() == 1) {
            binding.mngroommenuallmanagers.setVisibility(View.VISIBLE);
            binding.mngroommenuallmanagers.animate().translationY(-getResources().getDimension(R.dimen.standard_210));
        }

    }

    private void closeFABMenu() {
        isFABOpen = false;
        binding.mngroommenulogout.animate().translationY(0);
        binding.mngroommenuaccount.animate().translationY(0);
        binding.mngroommenuservice.animate().translationY(0);
        if (db.searchOneManagerById(accountId).getIsboss() == 1) {
            binding.mngroommenuallmanagers.animate().translationY(0);
        }
    }

}