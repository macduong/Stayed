package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayed.Database.DB;
import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityEditServiceBinding;

public class EditService extends AppCompatActivity {
    private ActivityEditServiceBinding binding;
    private DB db;
    private Intent intent;
    private Services service = new Services();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_service);
        db = new DB(getBaseContext());
        intent = getIntent();
        int serviceId = intent.getIntExtra("serviceId", 0);
        service = db.searchOneServicesById(serviceId);

        binding.editserviceInfotxt.setText(service.getName() + " Info");
        binding.editserviceNametxt.setText(service.getName());
        binding.editservicePricetxt.setText(String.valueOf(service.getPrice()));
        binding.editserviceUnittxt.setText(service.getUnit());

        binding.editserviceEditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editserviceNametxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.editserviceNametxt, "Please insert service's name");
                } else if (binding.editservicePricetxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.editservicePricetxt, "Please insert service's price");
                } else if (binding.editserviceUnittxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.editserviceUnittxt, "Please insert service's unit");
                } else {
                    String name = binding.editserviceNametxt.getText().toString();
                    int price = Integer.parseInt(binding.editservicePricetxt.getText().toString());
                    String unit = binding.editserviceUnittxt.getText().toString();
                    db.updateService(new Services(serviceId, name, price, unit));
                    finish();
                }
            }
        });

        binding.editserviceDeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteServiceById(serviceId);
                finish();
            }
        });

    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}