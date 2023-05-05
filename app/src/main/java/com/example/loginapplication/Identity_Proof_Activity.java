package com.example.loginapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityIdentityProofBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Identity_Proof_Activity extends  Drawer_Activity{
    ActivityIdentityProofBinding activityIdentityProofBinding;
    private TokenManager tokenManager;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proof);
        activityIdentityProofBinding=ActivityIdentityProofBinding.inflate(getLayoutInflater());
        setContentView(activityIdentityProofBinding.getRoot());
        allocateActivityTitle("Proof of Identity");

        tokenManager = new TokenManager(getApplicationContext());
        
        clickListerns();
    }

    private void clickListerns() {
        activityIdentityProofBinding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,10);
                }else{
                    ActivityCompat.requestPermissions(Identity_Proof_Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }

            }
        });
        activityIdentityProofBinding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFile();
            }
        });
        activityIdentityProofBinding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Identity_Proof_Activity.this,AddressProofActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode== Activity.RESULT_OK){
            Uri uri=data.getData();
            Context context=Identity_Proof_Activity.this;
            path= RealPathUtil.getRealPath(context,uri);
            Bitmap bitmap= BitmapFactory.decodeFile(path);
            activityIdentityProofBinding.imageView.setImageBitmap(bitmap);
        }
    }
    public  void addFile(){
        File file=new File(path);
        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("aadhar",file.getName(),requestFile);

        String token = tokenManager.getToken();
        Call<IdentityResponse> idResponseCall = ApiClient.getIDetails().addFile("Bearer "+token, body);
        idResponseCall.enqueue(new Callback<IdentityResponse>() {
            @Override
            public void onResponse(Call<IdentityResponse> call, Response<IdentityResponse> response) {

                if(response.isSuccessful()){
                    IdentityResponse idResponse = response.body();
                    Toast.makeText(Identity_Proof_Activity.this, "ID Proof Document has been uploaded "+idResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Identity_Proof_Activity.this,"not Added",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IdentityResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(Identity_Proof_Activity.this, message, Toast.LENGTH_LONG).show();
                Log.e("TAG", "Error message: " + t.getMessage());
            }
        });


    }

}