package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stayed.Database.DB;
import com.example.stayed.Model.Managers;
import com.example.stayed.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText regEmailtxt, regFullNametxt, regUsernametxt, regPasstxt, regConfPasstxt;
    private Button regRegbtn;
    private TextView regLoginbtn;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getBaseContext(), "Welcome to Signup", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.register);
        init();
        db = new DB(getBaseContext());


        regLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        regRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regEmailtxt.getText().toString().equalsIgnoreCase("") || !checkMail(regEmailtxt.getText().toString())) {
                    warningAndFocus(regEmailtxt, "Please input your email!");

                } else if (db.checkUsedMail(regEmailtxt.getText().toString())) {
                    warningAndFocus(regEmailtxt, "Email is used, please choose another one");

                } else if (regFullNametxt.getText().toString().equalsIgnoreCase("") || !checkFullName(regFullNametxt.getText().toString())) {
                    warningAndFocus(regFullNametxt, "Please input your real Full name!");

                } else if (regUsernametxt.getText().toString().equalsIgnoreCase("")) {
                    warningAndFocus(regUsernametxt, "Please input your Username!");

                } else if (!checkUsernameOrPassword(regUsernametxt.getText().toString())) {
                    warningAndFocus(regUsernametxt, "Username must contain only uppercase, lowercase and number");

                } else if (db.checkUsedUsername(regUsernametxt.getText().toString())) {
                    warningAndFocus(regUsernametxt, "Username is used, please choose another one");

                } else if (regPasstxt.getText().toString().length() <= 8) {
                    warningAndFocus(regPasstxt, "Password must be longer than 8 characters");

                } else if (!checkUsernameOrPassword(regPasstxt.getText().toString())) {
                    warningAndFocus(regPasstxt, "Password must contain only uppercase, lowercase and number");

                } else if (regConfPasstxt.getText().toString().equalsIgnoreCase("")) {
                    warningAndFocus(regConfPasstxt, "Please confirm your password!");

                } else if (!regConfPasstxt.getText().toString().equalsIgnoreCase(regPasstxt.getText().toString())) {
                    warningAndFocus(regConfPasstxt, "You re-enter wrong password!");

                } else {
                    String mail = regEmailtxt.getText().toString();
                    String fullname = regFullNametxt.getText().toString();
                    String username = regUsernametxt.getText().toString();
                    String password = regPasstxt.getText().toString();
                    int isboss = 0;
                    Managers manager = new Managers(fullname, mail, username, password, isboss);
                    if (db.insertManager(manager)) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "Signup successfully, please login!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Signup failed, please signup again!", Toast.LENGTH_LONG).show();
                    }
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

    private boolean checkFullName(String name) {
        String regex = "^[a-zA-Z ]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean checkMail(String mail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void init() {
        regEmailtxt = findViewById(R.id.regmailtxt);
        regFullNametxt = findViewById(R.id.regfullnametxt);
        regUsernametxt = findViewById(R.id.regusernametxt);
        regPasstxt = findViewById(R.id.regpasstxt);
        regConfPasstxt = findViewById(R.id.regconfpasstxt);
        regRegbtn = findViewById(R.id.regregbtn);
        regLoginbtn = findViewById(R.id.regloginbtn);
    }
}