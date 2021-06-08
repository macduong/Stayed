package com.example.stayed.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.stayed.Database.DB;
import com.example.stayed.Model.Managers;
import com.example.stayed.R;
import com.example.stayed.databinding.LoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private LoginBinding binding;
    private DB db;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login);
        db = new DB(getBaseContext());

        if (!db.checkUsedUsername("duong")) {
            db.insertManager(new Managers("Mac Duong", "duongitachi7@gmail.com", "duong", "123456789", 1));
        }

        binding.loginregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.loginloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = binding.loginusernametxt.getText().toString();
                String pass = binding.loginpasstxt.getText().toString();
                int accountId = db.login(user, pass);

                if (user.isEmpty() || !checkUsernameOrPassword(user)) {
                    warningAndFocus(binding.loginusernametxt, "Wrong username!");

                } else if (pass.isEmpty() || !checkUsernameOrPassword(pass)) {
                    warningAndFocus(binding.loginpasstxt, "Wrong password!");

                } else if (accountId == 0) {
                    warningAndFocus(binding.loginusernametxt, "Wrong username or password!");

                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("accountId", accountId);
                    editor.commit();

                    Intent intent = new Intent(getBaseContext(), ChooseManagement.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

        binding.loginforgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OTPConfirm.class);
                intent.putExtra("activityName", "ResetPassword");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.loginusernametxt.getText().clear();
        binding.loginpasstxt.getText().clear();
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