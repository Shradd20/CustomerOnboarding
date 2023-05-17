package com.example.loginapplication;

public class TokenData {
    String date;
    String fName;
    String lName;
    String mob;
    String email;
    String gen;
    String aNo;

    public String getDate() {
        return date;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getMob() {
        return mob;
    }

    public String getEmail() {
        return email;
    }

    public String getGen() {
        return gen;
    }

    public String getaNo() {
        return aNo;
    }

    public TokenData(String date, String fName, String lName, String mob, String email, String gen, String aNo) {
        this.date = date;
        this.fName = fName;
        this.lName = lName;
        this.mob = mob;
        this.email = email;
        this.gen = gen;
        this.aNo = aNo;
    }
}
