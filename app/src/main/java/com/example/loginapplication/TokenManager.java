package com.example.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  static final String REFNAME="jwtToken";

    private Context context;

    public TokenManager(Context context){
        this.context=context;
        sharedPreferences= context.getSharedPreferences(REFNAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void saveToken(String jwt){
        editor.putString("jwt", jwt);
        editor.apply();
        //Toast.makeText(context.getApplicationContext(),"Token saved re baba "+jwt,Toast.LENGTH_LONG).show();
    }

//    public void saveToken(String token){
//        editor.putString("jwt", token);
//        editor.apply();
//        Toast.makeText(context.getApplicationContext(),"Token saved"+token,Toast.LENGTH_LONG).show();
//    }

    public String getToken() {
        String token = sharedPreferences.getString("jwt", "");
//        if (token != null) {
//            Toast.makeText(context, "Token retrieved successfully", Toast.LENGTH_SHORT).show();
//        }
        return token;

    }
// no need to write getToken() if using intentextras

    public void saveUserDetails(String date,String fName, String lName, String mob,String email,String password) {
        editor.putString("date", date);
        editor.putString("fName", fName);
        editor.putString("lName", lName);
        editor.putString("mob",mob);
       editor.putString("email",email);
        editor.putString("password",password);

        editor.apply();
    }


    public String getDate() {
        return sharedPreferences.getString("date", "");
    }

    public String getfName() {
        return sharedPreferences.getString("fName", "");
    }

    public String getlName() {
        return sharedPreferences.getString("lName", "");
    }

    public String getMob() {
        return sharedPreferences.getString("mob", "");
    }

    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    public String getPass() {
        return sharedPreferences.getString("password", "");
    }

    public String getVerify() {
        return sharedPreferences.getString("emailVerify", "");
    }

}
