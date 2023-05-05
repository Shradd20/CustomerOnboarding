package com.example.loginapplication;

public class PersonalDetailsRequest {
    String dob;
    String firstName;
    String lastName;
    String mobile;
    private static String token;



    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        PersonalDetailsRequest.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public PersonalDetailsRequest(String dob,String firstName, String lastName, String mobile) {
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        //this.token=token;
    }

}
