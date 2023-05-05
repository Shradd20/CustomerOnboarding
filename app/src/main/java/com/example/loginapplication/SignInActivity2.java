package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity2 extends AppCompatActivity {

    EditText inputPass, inputPassword,inputFName;
    Button btnlogin;
    TextView textViewSignUp;
    String userStr;

    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);


        //getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tokenManager=new TokenManager(getApplicationContext());
        inputPass = (EditText) findViewById(R.id.inputPass);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        inputFName=findViewById(R.id.inputFName);



        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity2.this, SignUpActivity2.class));//SignUpActivity2.class--original
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(inputPass.getText().toString()) || TextUtils.isEmpty(inputPassword.getText().toString())) {
                    String message = "Please fill all input fields...";
                    Toast.makeText(SignInActivity2.this, message, Toast.LENGTH_LONG).show();
                } else {
                    SignInRequest signinRequest = new SignInRequest();
                    signinRequest.setEmail(inputPass.getText().toString());
                    signinRequest.setPassword(inputPassword.getText().toString());

                    signinUser(signinRequest);
                }
            }
        });
    }

                public void signinUser(SignInRequest signinRequest) {

                   // Boolean emailVerified = Boolean.valueOf(getIntent().getStringExtra("verifyemail"));//intent extra

                    //if(emailVerified==true) {
                        Call<SignInResponse> signinResponseCall = ApiClient.getService().signinUser(signinRequest);
                        signinResponseCall.enqueue(new Callback<SignInResponse>() {
                            @Override
                            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                                if (response.isSuccessful()) {
                                    SignInResponse signinResponse = response.body();

                                    String token = signinResponse.getJwt();
                                    Log.d("TAG", "Response from server: " + response.body());
                                    Log.d("TAG", "Token value is: " + token);
                                    //save token
                                    //String token = signinResponse.getJwt().toString();
                                    //String token=signinResponse.getJwt() != null ? signinResponse.getJwt(): null;
                                    // Log.d("TAG", "Token value is: " + signinResponse.getJwt());
                                    if (token != null) {
                                        tokenManager.saveToken(token);
                                    } else {
                                        // Handle the case where the JWT token is null
                                    }

                                    // tokenManager.saveToken(token);
                                    // Show a toast message with the token string
                                   // Toast.makeText(getApplicationContext(), "saved kela re " + token, Toast.LENGTH_LONG).show();

                                    Toast.makeText(getApplicationContext(),"LOGIN SUCESSFULLY", Toast.LENGTH_LONG).show();


                                    // tokenManager.saveToken(signinResponse.getToken().toString());
                                    //tokenManager.saveToken(signinResponse.getJwt().toString());


                                    //Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                    Intent i = new Intent(SignInActivity2.this, DashboardActivity.class);
                                    i.putExtra("FirstName",inputFName.getText().toString());
                                    i.putExtra("jwt_token", token);//include this line agar getToken() method nhi use karni
                                    startActivity(i);

                                    //startActivity(new Intent(SignInActivity.this, MainActivity.class));//
                                    finish();
                                } else {
                                    String message = "Invalid Credentials";
                                    Toast.makeText(SignInActivity2.this, message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SignInResponse> call, Throwable t) {
                                String message = t.getLocalizedMessage();
                                Toast.makeText(SignInActivity2.this, message, Toast.LENGTH_LONG).show();

                            }


                        });
                    //}
                }
            }