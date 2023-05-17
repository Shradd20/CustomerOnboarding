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

    EditText inputPass, inputFName, inputLName, inputPassword, inputCpassword,inputDOB,inputGenderR,inputPhone,inputAdhar;
    Button button2;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //getSupportActionBar().hide();
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tokenManager=new TokenManager(getApplicationContext());
        inputPass = findViewById(R.id.inputPass);
        inputFName = findViewById(R.id.inputFName);
        inputLName = findViewById(R.id.inputLName);
        inputPassword = findViewById(R.id.inputPassword);
        inputCpassword = findViewById(R.id.inputCpassword);
        button2 = findViewById(R.id.button2);
        inputAdhar=findViewById(R.id.inputAdhar);
        alreadyhaveAccount = findViewById(R.id.alreadyhaveAccount);

        inputDOB=findViewById(R.id.inputDOB);
        inputGenderR=findViewById(R.id.inputGenderR);
        inputPhone=findViewById(R.id.inputPhone);

        loadDataFromTokenManager();
        alreadyhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity2.this, SignInActivity2.class));//SignUpActivity2.class--original
            }
        });

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
                    signupRequest.setMobile(inputPhone.getText().toString());

//                    //New lines of code
//                    inputDOB.setText(inputDOB.getText().toString());
//                    inputGenderR.setText(inputGenderR.getText().toString());
//                    inputPhone.setText(inputPhone.getText().toString());
//                    inputAdhar.setText(inputAdhar.getText().toString());
                    signupUser(signupRequest);
                }
            }
        });

    }

    private void loadDataFromTokenManager() {
        String email=tokenManager.getRegisterData().getEmail();
        String fName=tokenManager.getRegisterData().getfName();
        String lName=tokenManager.getRegisterData().getlName();
        String mob=tokenManager.getRegisterData().getMob();
        String dob=tokenManager.getRegisterData().getDate();
        String gender=tokenManager.getRegisterData().getGen();
        String aadharNo=tokenManager.getRegisterData().getaNo();

        inputPass.setText(email);
        inputFName.setText(fName);
        inputLName.setText(lName);
        inputPhone.setText(mob);
        inputDOB.setText(dob);
        inputGenderR.setText(gender);
        inputAdhar.setText(aadharNo);

    }


    public void signupUser(SignUpRequest signupRequest) {
        Call<SignUpResponse> signupResponseCall = ApiClient.getService().signupUser(signupRequest);
        signupResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    String message = "SignUp Successful...";
                    Toast.makeText(SignUpActivity2.this, message, Toast.LENGTH_LONG).show();

                    String email= (inputPass.getText().toString());
                    String fName=(inputFName.getText().toString());
                    String lName=(inputLName.getText().toString());
                    String DOB=(inputDOB.getText().toString());
                    String gender=(inputGenderR.getText().toString());
                    String mobile=(inputPhone.getText().toString());
                    String ano=(inputAdhar.getText().toString());
                    tokenManager.saveRegisterDetails(email,fName,lName,DOB,gender,mobile,ano);

                    startActivity(new Intent(SignUpActivity2.this, SignInActivity2.class));
                    finish();

                } else {
                    String message = "User Already Register please Login....";
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