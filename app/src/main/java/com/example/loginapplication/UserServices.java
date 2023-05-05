package com.example.loginapplication;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserServices {

    @POST("/user-auth/signin")
    Call<SignInResponse> signinUser(@Body SignInRequest signinRequest);

    @POST("/user-auth/signup")
    Call<SignUpResponse> signupUser(@Body SignUpRequest signupRequest);

    @POST("/emailVerify/send-link")
    Call<EmailResponse> emailUser(
            @Header("Authorization") String authHeader
            //@Body EmailRequest emailRequest
    );

    @POST("/UserData/saveUserData")
    Call<PersonalDetailsResponse> saveUser(
            @Header("Authorization") String token,
            @Body PersonalDetailsRequest personalDetailsRequest
    );

    @Multipart
    @POST("/upload-doc/aadhar-upload")
    Call<IdentityResponse> addFile(
            @Header("Authorization") String token,
            @Part MultipartBody.Part aadhar
            //@Part("aadhar") IdentityProofRequest identityProofRequest
            //@Part("aadhar") RequestBody identityProofRequest
    );
}
