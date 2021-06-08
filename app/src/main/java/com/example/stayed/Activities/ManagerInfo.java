package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayed.Database.DB;
import com.example.stayed.Model.Managers;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityManagerInfoBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerInfo extends AppCompatActivity {
    private ActivityManagerInfoBinding binding;
    private int accountId;
    private DB db;
    private Managers manager = new Managers();
    private boolean confirmed;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manager_info);
        db = new DB(getBaseContext());

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        accountId = sharedPreferences.getInt("accountId", 0);

        manager = db.searchOneManagerById(accountId);


        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!confirmed) {
                    Intent intent1 = new Intent(getBaseContext(), OTPConfirm.class);
                    intent1.putExtra("activityName", "ManagerInfo");
                    intent1.putExtra("email", binding.mailtxt.getText().toString());
                    startActivity(intent1);
                    finish();

                } else {
                    update();
                    confirmed = false;
                }
            }
        });
    }

    private void update() {

        String username = binding.usernametxt.getText().toString();
        String fullname = binding.fullnametxt.getText().toString();
        String email = binding.mailtxt.getText().toString();
        String password = binding.passtxt.getText().toString();
        binding.passtxt.getText().clear();
        String confpassword = binding.confpasstxt.getText().toString();

        if (email.isEmpty() || !checkMail(email)) {
            warningAndFocus(binding.mailtxt, "Please input your email!");

        } else if (db.checkUsedMail(email) && !email.equalsIgnoreCase(db.searchOneManagerById(accountId).getMail())) {
            warningAndFocus(binding.usernametxt, "Email is used, please choose another one");

        } else if (fullname.isEmpty() || !checkFullName(fullname)) {
            warningAndFocus(binding.fullnametxt, "Please input your real Full name!");

        } else if (username.isEmpty()) {
            warningAndFocus(binding.usernametxt, "Please input your Username!");

        } else if (!checkUsernameOrPassword(username)) {
            warningAndFocus(binding.usernametxt, "Username must contain only uppercase, lowercase and number");

        } else if (db.checkUsedUsername(username) && username != db.searchOneManagerById(accountId).getUserName()) {
            warningAndFocus(binding.usernametxt, "Username is used, please choose another one");

        } else if (password.length() <= 8) {
            warningAndFocus(binding.passtxt, "Password must be longer than 8 characters");

        } else if (!checkUsernameOrPassword(password)) {
            warningAndFocus(binding.passtxt, "Password must contain only uppercase, lowercase and number");

        } else if (confpassword.equalsIgnoreCase("")) {
            warningAndFocus(binding.confpasstxt, "Please confirm your password!");

        } else if (!confpassword.equalsIgnoreCase(password)) {
            warningAndFocus(binding.confpasstxt, "You re-enter wrong password!");

        } else {
            int isboss = 0;
            Managers manager = new Managers(fullname, email, username, password, isboss);
            if (db.insertManager(manager)) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Update successfully, please login!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Update failed, please update again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.fullnametxt.setText(manager.getFullName());
        binding.mailtxt.setText(manager.getMail());
        binding.passtxt.setText(manager.getPassWord());
        binding.usernametxt.setText(manager.getUserName());

        Intent intent = getIntent();
        confirmed = intent.getBooleanExtra("confirmed", false);

        if (confirmed) {
            binding.fullnametxt.setEnabled(true);
            binding.passtxt.setEnabled(true);
            binding.passtxt.getText().clear();
            binding.confpasstxt.setVisibility(View.VISIBLE);
            binding.mailtxt.setEnabled(true);
            binding.mailtxt.requestFocus();
            binding.usernametxt.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean checkUsernameOrPassword(String UOP) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(UOP);
        return matcher.matches();
    }

    private boolean checkFullName(String name) {
        String regex = "^[a-zA-Z ]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean checkMail(String mail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }

}