package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity2 extends AppCompatActivity {
    TextView alreadyhaveAccount;

    EditText inputPass, inputFName, inputLName, inputPassword, inputCpassword;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //getSupportActionBar().hide();
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        inputPass = findViewById(R.id.inputPass);
        inputFName = findViewById(R.id.inputFName);
        inputLName = findViewById(R.id.inputLName);
        inputPassword = findViewById(R.id.inputPassword);
        inputCpassword = findViewById(R.id.inputCpassword);
        button2 = findViewById(R.id.button2);
        alreadyhaveAccount = findViewById(R.id.alreadyhaveAccount);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(inputPass.getText().toString()) || TextUtils.isEmpty(inputFName.getText().toString()) || TextUtils.isEmpty(inputLName.getText().toString()) || TextUtils.isEmpty(inputPassword.getText().toString()) || TextUtils.isEmpty(inputCpassword.getText().toString())) {
                    String message = "Please fill all input fields...";
                    Toast.makeText(SignUpActivity2.this, message, Toast.LENGTH_LONG).show();
                } else {
                    SignUpRequest signupRequest = new SignUpRequest();
                    signupRequest.setEmail(inputPass.getText().toString());
                    signupRequest.setFirstName(inputFName.getText().toString());
                    signupRequest.setLastName(inputLName.getText().toString());
                    signupRequest.setPassword(inputPassword.getText().toString());
                    signupUser(signupRequest);
                }
            }
        });

    }

    public void signupUser(SignUpRequest signupRequest) {
        Call<SignUpResponse> signupResponseCall = ApiClient.getService().signupUser(signupRequest);
        signupResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                if (response.isSuccessful()) {
                    String message = "SignUp Successful...";
                    Toast.makeText(SignUpActivity2.this, message, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SignUpActivity2.this, SignInActivity2.class));
                    finish();

                } else {
                    String message = "An error occured please try again later....";
                    Toast.makeText(SignUpActivity2.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(SignUpActivity2.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

}