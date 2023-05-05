package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginapplication.databinding.ActivityPhoneVerifyBinding;

public class Phone_verify_Activity extends Drawer_Activity {

    ActivityPhoneVerifyBinding activityPhoneVerifyBinding;
    Button buttonSubmit,buttonSubmit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        activityPhoneVerifyBinding = ActivityPhoneVerifyBinding.inflate(getLayoutInflater());
        setContentView(activityPhoneVerifyBinding.getRoot());
        allocateActivityTitle("Phone Verification");

        buttonSubmit=findViewById(R.id.buttonSubmit);
        buttonSubmit2=findViewById(R.id.buttonSubmit2);

        buttonSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phone_verify_Activity.this, Identity_Proof_Activity.class));

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phone_verify_Activity.this,Email_Verification_Activity.class));

            }
        });
    }
}