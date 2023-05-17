package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityEmailVerificationBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Email_Verification_Activity extends Drawer_Activity {

    ActivityEmailVerificationBinding activityEmailVerificationBinding;

    private TokenManager tokenManager;
    Button buttonSubmit;
    Button buttonSubmit2,verify_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        activityEmailVerificationBinding=ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(activityEmailVerificationBinding.getRoot());
        allocateActivityTitle("Email Verification");

        tokenManager = new TokenManager(getApplicationContext());
        buttonSubmit=findViewById(R.id.buttonSubmit);
        buttonSubmit2=findViewById(R.id.buttonSubmit2);
        verify_email=findViewById(R.id.verify_email);

       // verify_email.getText(tokenManager.getVerify());



        verify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailUser();
                //startActivity(new Intent(Email_Verification_Activity.this, Phone_verify_Activity.class));

            }
        });

        //next button
        buttonSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailUser();
                startActivity(new Intent(Email_Verification_Activity.this, Phone_verify_Activity.class));

            }
        });

        //back button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Email_Verification_Activity.this,DashboardActivity.class));

            }
        });
        
    }

    public void emailUser() {
        //delete this line if making use of intent extras
        String token = tokenManager.getToken();

        //String token = getIntent().getStringExtra("jwt_token");//intent extra


        if (token != null) {

            //  EmailRequest emailRequest = new EmailRequest("sender@example.com", "recipient@example.com", "Test email", "Hello, this is a test email!");
            //Call<EmailResponse> emailResponseCall = ApiClass.getSEmail().emailUser("Bearer " + token, emailRequest);
            Call<EmailResponse> emailResponseCall = ApiClient.getSEmail().emailUser("Bearer " + token);

            Log.e("TAG","token:- "+token);
           // Toast.makeText(Email_Verification_Activity.this, "token" +token, Toast.LENGTH_SHORT).show();

            emailResponseCall.enqueue(new Callback<EmailResponse>() {

                @Override
                public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                    if (!response.isSuccessful()) {
                        try {
                            Log.e("TAG", "Error response: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.isSuccessful()) {

                        EmailResponse emailResponse = response.body();

                        //String jwtToken = getIntent().getStringExtra("jwt_token");
                        //Boolean emailVerified=true;
                       // Toast.makeText(getApplicationContext(),"retrive kela re"+token,Toast.LENGTH_LONG).show();
                        Log.d("TAG", "Response from server: " + token);
                        Toast.makeText(Email_Verification_Activity.this, emailResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Email_Verification_Activity.this, "Failed to verify email. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Error response code: " + response.code());
                        Log.e("TAG", "Error response message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<EmailResponse> call, Throwable t) {
                    String message = t.getLocalizedMessage();
                    Toast.makeText(Email_Verification_Activity.this, message, Toast.LENGTH_LONG).show();
                    Log.e("TAG", "Error message: " + t.getMessage());


                }
            });

        }

        // Get token from Intent extras(delete this if using tokenManager.getToken())
        //String jwtToken = getIntent().getStringExtra("jwt_token");

        // Call<EmailResponse> emailResponseCall = ApiClass.getSEmail().emailUser("Bearer " + token, emailRequest);
        // Call<EmailResponse> emailResponseCall = ApiClass.getSEmail().emailUser("Bearer " + jwtToken, emailRequest);
//        emailResponseCall.enqueue(new Callback<EmailResponse>() {
//
//
//
//            @Override
//            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
//                if (!response.isSuccessful()) {
//                    try {
//                        Log.e("TAG", "Error response: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (response.isSuccessful()) {
//
//                    EmailResponse emailResponse = response.body();
//
//                    String jwtToken = getIntent().getStringExtra("jwt_token");
//
//                    Toast.makeText(getApplicationContext(),"retrive kela re"+jwtToken,Toast.LENGTH_LONG).show();
//                    Log.d("TAG", "Response from server: " + jwtToken);
//                    Toast.makeText(EmailActivity.this, "Ja mail check kar"+emailResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(EmailActivity.this, "Failed to retrieve email. Please try again.", Toast.LENGTH_SHORT).show();
//                    Log.e("TAG", "Error response code: " + response.code());
//                    Log.e("TAG", "Error response message: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EmailResponse> call, Throwable t) {
//                String message = t.getLocalizedMessage();
//                Toast.makeText(EmailActivity.this, message, Toast.LENGTH_LONG).show();
//                Log.e("TAG", "Error message: " + t.getMessage());
//
//
//            }
//        });
    }
}