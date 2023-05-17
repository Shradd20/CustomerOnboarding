package com.example.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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

    public void saveUserDetails(String pdate,String pfName, String plName, String pmob,String pemail,String pgen,String paNo) {
        editor.putString("pdate", pdate);
        editor.putString("pfName", pfName);
        editor.putString("plName", plName);
        editor.putString("pmob",pmob);
       editor.putString("pemail",pemail);
        editor.putString("pgender",pgen);
        editor.putString("padharNo",paNo);
        editor.apply();
    }

    public TokenData2 getData() {
        String mail=sharedPreferences.getString("remail", "");
        String name = sharedPreferences.getString("rfName", "");
        String lName=sharedPreferences.getString("rlName", "");
        String dob = sharedPreferences.getString("rdate", "");
        String gender=sharedPreferences.getString("rgender", "");
        String phone=sharedPreferences.getString("rmob", "");
        String aadhar=sharedPreferences.getString("radharNo", "");

        return new TokenData2(dob, name, lName, phone, mail, gender, aadhar);
    }


    public void saveRegisterDetails(String remail,String rfName, String rlName, String rdate,String rgen,String rmob,String raNo){
        editor.putString("rdate", rdate);
        editor.putString("rfName", rfName);
        editor.putString("rlName", rlName);
        editor.putString("rmob",rmob);
        editor.putString("remail",remail);
        editor.putString("rgender",rgen);
        editor.putString("radharNo",raNo);
        editor.apply();
    }

    public TokenData getRegisterData() {
        String mail=sharedPreferences.getString("remail", "");
        String name = sharedPreferences.getString("rfName", "");
        String lName=sharedPreferences.getString("rlName", "");
        String dob = sharedPreferences.getString("rdate", "");
        String gender=sharedPreferences.getString("rgender", "");
        String phone=sharedPreferences.getString("rmob", "");
        String aadhar=sharedPreferences.getString("radharNo", "");

        return new TokenData(dob, name, lName, phone, mail, gender, aadhar);
    }

//    public String getDate() {
//        return sharedPreferences.getString("date", "");
//    }
//
//    public String getfName() {
//        return sharedPreferences.getString("fName", "");
//    }
//
//    public String getlName() {
//        return sharedPreferences.getString("lName", "");
//    }
//
//    public String getMob() {
//        return sharedPreferences.getString("mob", "");
//    }
//
////    public String getEmail() {
////        return sharedPreferences.getString("email", "");
////    }
//
//    public String getPass() {
//        return sharedPreferences.getString("password", "");
//    }
//
//    public String getVerify() {
//        return sharedPreferences.getString("emailVerify", "");
//    }
    public void saveExtractedData(String  extractedName, String lName,String dob, String gender,String phoneNumber,String aadhaar) {
        editor.putString("name",  extractedName);
        //editor.putString("sName",  sName);
        editor.putString("lName",  lName);
        editor.putString("dob", dob);
        editor.putString("gender", gender);
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("aadhaar",aadhaar);
        editor.apply();
    }

    public void saveExtractedAddress(String add){
        editor.putString("address",add);
        editor.apply();
    }
    public Map<String,String> getSavedExtractedAddress(){
        Map<String, String> extractedData = new HashMap<>();
        extractedData.put("address", sharedPreferences.getString("address", ""));
        return extractedData;
    }

  public TokenAddress getTokenExtractedAddressData(){
      Map<String, String> savedData =getSavedExtractedAddress();
      String address=savedData.get("address");

      return new  TokenAddress(address);
  }

    public Map<String, String> getSavedExtractedData() {
        Map<String, String> extractedData = new HashMap<>();
        extractedData.put("name", sharedPreferences.getString("name", ""));
        // extractedData.put("sName", sharedPreferences.getString("sName", ""));
        extractedData.put("lName", sharedPreferences.getString("lName", ""));
        extractedData.put("dob", sharedPreferences.getString("dob", ""));
        extractedData.put("gender", sharedPreferences.getString("gender", ""));
        extractedData.put("phoneNumber", sharedPreferences.getString("phoneNumber", ""));
        extractedData.put("aadhaar", sharedPreferences.getString("aadhaar", ""));
        extractedData.put("mail",sharedPreferences.getString("email", ""));
        return extractedData;
    }
    public TokenData getTokenExtractedData() {
        Map<String, String> savedData = getSavedExtractedData();
        String name = savedData.get("name");
        //String sName = savedData.get("sName");
        String lName = savedData.get("lName");
        String dob = savedData.get("dob");
        String gender=savedData.get("gender");
        String phoneNumber=savedData.get("phoneNumber");
        String aadhaar=savedData.get("aadhaar");
        String email=savedData.get("mail");

        return new TokenData(dob, name, lName, phoneNumber, email, gender, aadhaar);
    }

}
