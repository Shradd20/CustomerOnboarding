package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityPersonalDetailsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetails_Activity extends  Drawer_Activity  {
    ActivityPersonalDetailsBinding activityPersonalDetailsBinding;


    private TokenManager tokenManager;
    EditText firstName;
    EditText lastName;
    EditText mobile;
    EditText dob;
    EditText email;
    EditText aadhar;
    EditText gender;
    Button buttonSubmit;
    Button buttonSubmit2;
    String userEmail;
    PersonalDetailsRequest personalDetailsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonalDetailsBinding = ActivityPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityPersonalDetailsBinding.getRoot());
        allocateActivityTitle("Personal Profile");


        tokenManager = new TokenManager(getApplicationContext());
        firstName=findViewById(R.id.firstNames);
        lastName=findViewById(R.id.lastNames);
        mobile=findViewById(R.id.mobiles);
        dob=findViewById(R.id.dobs);
        email=findViewById(R.id.emails);
        aadhar=findViewById(R.id.aadhars);
        gender=findViewById(R.id.genders);



        loadDataFromTokenManager();

//        dob.setText(tokenManager.getData().getDate());
//        firstName.setText(tokenManager.getData().getfName());
//        lastName.setText(tokenManager.getData().getlName());
//        mobile.setText(tokenManager.getData().getMob());
//        email.setText(tokenManager.getData().getEmail());
//        //password.setText(tokenManager.getPass());

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit2=findViewById(R.id.buttonSubmit2);

        buttonSubmit.setOnClickListener(view -> {

//            dob.setText(tokenManager.getDate());
//        firstName.setText(tokenManager.getfName());
//        lastName.setText(tokenManager.getlName());
//        mobile.setText(tokenManager.getMob());
//        email.setText(tokenManager.getEmail());
//        password.setText(tokenManager.getPass());
            //tokenManager.getUserDetails();



                // EmailRequest emailRequest = new EmailRequest("sshiiinde13@gmail.com", "shraddha.shinde.cs@ghrietn.raisoni.net", "Test email", "Hello, this is a test email!");
                //PersonalDetailsRequest personalDetailsRequest = new PersonalDetailsRequest(token,fName, lName, mob, date);
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String mob = mobile.getText().toString();
                String date = dob.getText().toString();
                personalDetailsRequest = new PersonalDetailsRequest(date, fName, lName, mob);
            saveUser(personalDetailsRequest);


        });

        buttonSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalDetails_Activity.this, Email_Verification_Activity.class));
            }
        });

    }

    private void saveUser(PersonalDetailsRequest personalDetailsRequest) {

        String token = tokenManager.getToken();

        if (token != null) {
            Call<PersonalDetailsResponse> personalDetailsResponseCall = ApiClient.getPDetails(token).saveUser("Bearer " + token, personalDetailsRequest);

            personalDetailsResponseCall.enqueue(new Callback<PersonalDetailsResponse>() {

                @Override
                public void onResponse(Call<PersonalDetailsResponse> call, Response<PersonalDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        PersonalDetailsResponse personalDetailsResponse = response.body();


                        //email.setText(tokenManager.getEmail());
                        //password.setText(tokenManager.getPass());


                        String fName = firstName.getText().toString();
                        Log.e("Tag","fName"+fName);
                        String lName = lastName.getText().toString();
                        String mob = mobile.getText().toString();
                        String date = dob.getText().toString();
                        String mail= email.getText().toString();
                        String gen=gender.getText().toString();
                        String aNo=aadhar.getText().toString();

                        //tokenManager.saveUserDetails(date,fName,lName,mob,mail,gen,aNo);
                        tokenManager.saveRegisterDetails(mail,fName,lName,date,gen,mob,aNo);

                        //loadDataFromTokenManager();
                            //email.setText(tokenManager.getEmail());
                            //password.setText(tokenManager.getPass());


                        //tokenManager.getUserDetails();
                        //Toast.makeText(PersonalDetails_Activity.this, ""+tokenManager.getUserDetails(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(PersonalDetails_Activity.this,personalDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // "done" message
                        // Handle the response
                    } else {
                        // Handle the error
                    }
                }

                @Override
                public void onFailure(Call<PersonalDetailsResponse> call, Throwable t) {
                    String message = t.getLocalizedMessage();
                    Toast.makeText(PersonalDetails_Activity.this, message, Toast.LENGTH_LONG).show();
                    Log.e("TAG", "Error message: " + t.getMessage());
                }
            });
        }

    }
    private void loadDataFromTokenManager() {
        // Get the user data from the TokenManager
//        String mail=tokenManager.getData().getEmail();
//        //String mail= userEmail;
//       // String fName = tokenManager.getData().getfName();
//        String fName= tokenManager.getData().getfName();
//        String lName = tokenManager.getData().getlName();
//        String mob = tokenManager.getData().getMob();
//        String date = tokenManager.getData().getDate();
//        String gen=tokenManager.getData().getGen();
//        String aadharN=tokenManager.getData().getaNo();
        //String mail= email.getText().toString();
        //String pass=password.getText().toString();
  String mail=tokenManager.getRegisterData().getEmail();
  String fName=tokenManager.getRegisterData().getfName();
  String lName=tokenManager.getRegisterData().getlName();
  String mob=tokenManager.getRegisterData().getMob();
  String date = tokenManager.getRegisterData().getDate();
  String gen=tokenManager.getRegisterData().getGen();
  String aadharN=tokenManager.getRegisterData().getaNo();


        email.setText(mail);
        firstName.setText(fName);
        lastName.setText(lName);
        mobile.setText(mob);
        dob.setText(date);
        gender.setText(gen);
        aadhar.setText(aadharN);
    }
    }
