package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SignInResponse signinResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Intent intent=getIntent();
        //if(intent.getExtras()!=null){
          //  signinResponse = (SignInResponse) intent.getSerializableExtra("data");

            //Log.e("TAG","====>"+signinResponse.getJwt());
        //}
    }
}