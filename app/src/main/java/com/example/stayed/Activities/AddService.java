package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayed.Database.DB;
import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityAddServiceBinding;

public class AddService extends AppCompatActivity {
    private ActivityAddServiceBinding binding;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_service);
        db = new DB(getBaseContext());
        binding.addserviceEditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addserviceNametxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.addserviceNametxt, "Please insert service's name");
                } else if (binding.addservicePricetxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.addservicePricetxt, "Please insert service's price");
                } else if (binding.addserviceUnittxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.addserviceUnittxt, "Please insert service's unit");
                } else {
                    String name = binding.addserviceNametxt.getText().toString();
                    int price = Integer.parseInt(binding.addservicePricetxt.getText().toString());
                    String unit = binding.addserviceUnittxt.getText().toString();
                    db.insertService(new Services(name, price, unit));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}