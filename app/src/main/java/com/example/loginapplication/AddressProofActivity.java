package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loginapplication.databinding.ActivityAddressProofBinding;

public class AddressProofActivity extends  Drawer_Activity {
    ActivityAddressProofBinding activityAddressProofBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_proof);
        activityAddressProofBinding=ActivityAddressProofBinding.inflate(getLayoutInflater());
        setContentView(activityAddressProofBinding.getRoot());
        allocateActivityTitle("Email Verification");
    }
}