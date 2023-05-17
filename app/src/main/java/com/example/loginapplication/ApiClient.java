package com.example.loginapplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("http://192.168.54.17:3000")
                //.baseUrl("http://192.168.43.87:3000")
                .baseUrl("http://192.168.239.17:3000")
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static UserServices getService(){
        UserServices userService=getRetrofit().create(UserServices.class);
        return  userService;
    }

    public static UserServices getSEmail(){
        UserServices userServicEmail=getRetrofit().create(UserServices.class);
        return  userServicEmail;
    }

    public static UserServices getPDetails(String token) {
        UserServices userServiceDetails=getRetrofit().create(UserServices.class);
        return userServiceDetails;
    }

    public static UserServices getIDetails() {
        UserServices userIdentityDetails=getRetrofit().create(UserServices.class);
        return userIdentityDetails;
    }
}
