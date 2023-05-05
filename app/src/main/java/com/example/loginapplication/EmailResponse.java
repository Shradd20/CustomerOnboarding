package com.example.loginapplication;

public class EmailResponse {
    private String message;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    private String email;
    private String registrationStatus;

    public EmailResponse(String email, String registrationStatus) {
        this.email = email;
        this.registrationStatus = registrationStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public boolean isRegistered() {
        return registrationStatus.equals("REGISTERED");
    }
}
