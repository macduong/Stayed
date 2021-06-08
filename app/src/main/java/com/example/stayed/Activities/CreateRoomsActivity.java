package com.example.stayed.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.stayed.Database.DB;
import com.example.stayed.R;
import com.example.stayed.databinding.CreateRoomsBinding;

public class CreateRoomsActivity extends AppCompatActivity {
    private int number, vipNumber, vipPrice, normalPrice;
    private CreateRoomsBinding binding;
    private DB database;
    private int accountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getBaseContext(), "Please enter number of rooms", Toast.LENGTH_SHORT).show();
        binding = DataBindingUtil.setContentView(this, R.layout.create_rooms);
        database = new DB(getBaseContext());

        Intent intent = getIntent();
        intent.getIntExtra("accountId", 0);

        binding.createnumberviptxt.setVisibility(View.GONE);
        binding.createvippricext.setVisibility(View.GONE);
        binding.createnormalpricetxt.setVisibility(View.GONE);
        binding.createhavevip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.createnumberviptxt.setVisibility(View.VISIBLE);
                binding.createnumberviptxt.requestFocus();
            }
        });

        binding.createlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.createnumberviptxt.getText().toString().isEmpty()) {
                    vipNumber = 0;
                } else {
                    vipNumber = Integer.parseInt(binding.createnumberviptxt.getText().toString());
                }
                if (binding.createnumbertxt.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getBaseContext(), "Please enter number of your rooms", Toast.LENGTH_SHORT).show();
                    binding.createnumbertxt.requestFocus();
                } else {
                    number = Integer.parseInt(binding.createnumbertxt.getText().toString());
                    if (number < vipNumber) {
                        Toast.makeText(getBaseContext(), "VIP rooms can't be more than total rooms", Toast.LENGTH_SHORT).show();
                        binding.createnumbertxt.requestFocus();
                    } else {
                        binding.createintrotxt.setText("Please input the room's price below (VND)");
                        binding.createintrotxt.setTextColor(Color.GREEN);
                        binding.createbtn.setHint("Let's manage!");
                        binding.createnumberviptxt.setVisibility(View.GONE);
                        binding.createhavevip.setVisibility(View.GONE);
                        binding.createnumbertxt.setVisibility(View.GONE);
                        binding.createnormalpricetxt.setVisibility(View.VISIBLE);
                        binding.createnormalpricetxt.requestFocus();
                        if (vipNumber > 0) {
                            binding.createvippricext.setVisibility(View.VISIBLE);
                        } else {
                            binding.createvippricext.setText("0");
                        }
                        binding.createbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (binding.createnormalpricetxt.getText().toString().isEmpty()) {
                                    Toast.makeText(getBaseContext(), "Please enter room's price", Toast.LENGTH_SHORT).show();
                                    binding.createnormalpricetxt.requestFocus();
                                } else if (binding.createvippricext.getText().toString().isEmpty()) {
                                    Toast.makeText(getBaseContext(), "Please enter VIP room's price", Toast.LENGTH_SHORT).show();
                                    binding.createvippricext.requestFocus();
                                } else {
                                    vipPrice = Integer.parseInt(binding.createvippricext.getText().toString());
                                    normalPrice = Integer.parseInt(binding.createnormalpricetxt.getText().toString());
                                    database.deleteAllRooms();
                                    database.insertRooms(number, normalPrice, vipNumber, vipPrice);
                                    startActivity(new Intent(getBaseContext(), ManageRoomsActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.createnumberviptxt.setVisibility(View.GONE);
        binding.createvippricext.setVisibility(View.GONE);
        binding.createnormalpricetxt.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}