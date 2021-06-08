package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.stayed.Database.DB;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityChooseManagementBinding;

import java.io.File;

public class ChooseManagement extends AppCompatActivity {
    private ActivityChooseManagementBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_management);
        db = new DB(getBaseContext());

        if (db.roomDataExists()) {
            binding.choosecontinue.setVisibility(View.VISIBLE);
        }


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        binding.choosecontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageRoomsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.choosenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.appearifnewstart.setVisibility(View.VISIBLE);
                binding.choosenew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteAllRentGuest();
                        db.deleteAllGuestServices();
                        if (!binding.keepservices.isChecked()) {
                            db.deleteAllServices();

                        }
                        Intent intent1 = new Intent(getBaseContext(), CreateRoomsActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
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
    }


}