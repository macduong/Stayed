package com.example.stayed.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.stayed.Database.DB;
import com.example.stayed.MailSender.JavaMailUtil;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityOTPConfirmBinding;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPConfirm extends AppCompatActivity {
    private ActivityOTPConfirmBinding binding;
    private String otp, recepient;
    private int inputtimes = 0;
    private String activity, mailEdit;
    private DB db;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_o_t_p_confirm);
        db = new DB(getBaseContext());

        Intent intent = getIntent();
        activity = intent.getStringExtra("activityName");
        mailEdit = intent.getStringExtra("email");

        if (activity.equalsIgnoreCase("ManagerInfo")) {
            binding.forgotinputtxt.setText(mailEdit);
            binding.forgotinputtxt.setEnabled(false);
        }

        binding.forgotgetOTPbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputtimes = 1;

                if (binding.forgotinputtxt.getText().toString().isEmpty() || !checkMail(binding.forgotinputtxt.getText().toString())) {
                    warningAndFocus(binding.forgotinputtxt, "Please input your right email!");
                } else {
                    recepient = binding.forgotinputtxt.getText().toString();
                    otp = givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect();

                    String confirmMail = "We sent an OTP to the email:\n" + binding.forgotinputtxt.getText().toString() + "\nPlease check Inbox or Spam to get it\nThe OTP will remain in 5 minutes";
                    binding.forgotemailconftxt.setText(confirmMail);
                    binding.forgotinputtxt.getText().clear();
                    binding.forgotinputtxt.setHint("Input your OTP here!");
                    binding.forgotconfOTPbtn.setVisibility(View.VISIBLE);
                    binding.forgotgetOTPbtn.setVisibility(View.GONE);

                    if (db.checkUsedMail(recepient)) {
                        try {
                            JavaMailUtil.sendMail(recepient, otp);

                            binding.forgotinputtxt.setEnabled(true);
                        } catch (Exception e) {
                            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }

                    OTPCountdown.start();

                }
            }

        });
        binding.forgotconfOTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputtimes <= 5) {
                    if (binding.forgotinputtxt.getText().toString().equalsIgnoreCase(otp)) {
                        OTPCountdown.cancel();

                        if (activity.equalsIgnoreCase("ResetPassword")) {
                            Intent intent1 = new Intent(getBaseContext(), ResetPassword.class);
                            intent1.putExtra("mail", recepient);
                            startActivity(intent1);

                            finish();
                        } else {
                            Intent intent1 = new Intent(getBaseContext(), ManagerInfo.class);
                            intent1.putExtra("confirmed", true);
                            startActivity(intent1);
                            finish();
                        }
                    } else {
                        warningAndFocus(binding.forgotinputtxt, "Wrong OTP, please recheck!");
                    }
                    inputtimes += 1;
                } else {
                    Toast.makeText(getBaseContext(), "You insert wrong OTP too many times, please reconfirm email", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        activity = intent.getStringExtra("activityName");
        mailEdit = intent.getStringExtra("email");
    }

    public String givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public boolean checkMail(String mail) {
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

    CountDownTimer OTPCountdown = new CountDownTimer(300000, 100) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            binding.forgotemailconftxt.setText("Insert your email and click Get OTP\\nto confirm your email first");
            binding.forgotconfOTPbtn.setVisibility(View.GONE);
            binding.forgotgetOTPbtn.setVisibility(View.VISIBLE);
            binding.forgotinputtxt.getText().clear();
            binding.forgotinputtxt.setHint("Your email");
            Toast.makeText(getBaseContext(), "Your OTP is expired. Please reenter your email and get another OTP", Toast.LENGTH_LONG).show();
        }
    };


}