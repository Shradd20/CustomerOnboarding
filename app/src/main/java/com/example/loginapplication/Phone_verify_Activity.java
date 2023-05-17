package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityPhoneVerifyBinding;

import java.util.Random;

public class Phone_verify_Activity extends Drawer_Activity {

    ActivityPhoneVerifyBinding activityPhoneVerifyBinding;
    Button buttonSubmit,buttonSubmit2;
    private EditText phoneNumberEditText;
    private EditText verificationCodeEditText;

    private int verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        activityPhoneVerifyBinding = ActivityPhoneVerifyBinding.inflate(getLayoutInflater());
        setContentView(activityPhoneVerifyBinding.getRoot());
        allocateActivityTitle("Phone Verification");

        buttonSubmit=findViewById(R.id.buttonSubmit);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        verificationCodeEditText = findViewById(R.id.verification_code_edit_text);

        Button sendVerificationCodeButton = findViewById(R.id.send_verification_code_button);
        sendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                sendVerificationCode(phoneNumber);
            }
        });
        Button verifyPhoneNumberButton = findViewById(R.id.verify_phone_number_button);
        verifyPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int otpEnteredByUser = Integer.parseInt(verificationCodeEditText.getText().toString());
                if (otpEnteredByUser == verificationCode) {
                    Toast.makeText(Phone_verify_Activity.this, "Phone number verified!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Phone_verify_Activity.this, "Invalid OTP, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phone_verify_Activity.this,Identity_Proof_Activity.class));

            }
        });
    }
    private void sendVerificationCode(String phoneNumber) {
        Random random = new Random();
        verificationCode = random.nextInt(999999);

        String message = "Your verification code is: " + verificationCode;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        Toast.makeText(this, "Verification code sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
}