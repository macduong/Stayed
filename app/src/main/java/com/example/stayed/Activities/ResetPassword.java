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
import com.example.stayed.Model.Managers;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityResetPasswordBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {
    private ActivityResetPasswordBinding binding;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        db = new DB(getBaseContext());

        Intent intent = getIntent();
        String mail = intent.getStringExtra("mail");

        binding.forgotloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        binding.forgotresetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = binding.forgotpasstxt.getText().toString();
                String confpass = binding.forgotconfpasstxt.getText().toString();

                if (pass.length() <= 8) {
                    warningAndFocus(binding.forgotpasstxt, "Password must be longer than 8 characters");

                } else if (!checkUsernameOrPassword(pass)) {
                    warningAndFocus(binding.forgotpasstxt, "Password must contain only uppercase, lowercase and number");

                } else if (confpass.isEmpty()) {
                    warningAndFocus(binding.forgotconfpasstxt, "Please confirm your password!");

                } else if (!confpass.equalsIgnoreCase(pass)) {
                    warningAndFocus(binding.forgotconfpasstxt, "You re-enter wrong password!");

                } else {
                    Managers managers = db.searchOneManagerByMail(mail);
                    managers.setPassWord(pass);
                    db.updateManager(managers);
                    Toast.makeText(getBaseContext(), "Completed, please login again!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }

    private boolean checkUsernameOrPassword(String UOP) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(UOP);
        return matcher.matches();
    }

}