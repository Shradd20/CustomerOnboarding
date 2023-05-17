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
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityIdentityProofBinding;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Identity_Proof_Activity extends  Drawer_Activity{
    ActivityIdentityProofBinding activityIdentityProofBinding;
    private TokenManager tokenManager;

    String path,userEmail;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap imageBitmap;
    ImageView imageView;
    EditText nameEditText,lastEditText,addressEditText, dobEditText,genderEditText,mobileEditText,aadharEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proof);
        activityIdentityProofBinding=ActivityIdentityProofBinding.inflate(getLayoutInflater());
        setContentView(activityIdentityProofBinding.getRoot());
        allocateActivityTitle("Proof of Identity");

        tokenManager = new TokenManager(getApplicationContext());

        //imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        // addressEditText = findViewById(R.id.addressEditText);
        dobEditText = findViewById(R.id.dobEditText);
        genderEditText=findViewById(R.id.genderEditText);
        mobileEditText=findViewById(R.id.mobileEditText);
        aadharEditText=findViewById(R.id.aadharEditText);
        lastEditText=findViewById(R.id.lastEditText);
        //secondEditText=findViewById(R.id.secondEditText);
        
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
                //Intent i=new Intent(Identity_Proof_Activity.this,FinalReportActivity.class);
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

            //new code
            recognizeTextFromImage(uri);
        }
    }

    private void recognizeTextFromImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!textRecognizer.isOperational()) {
                Toast.makeText(this, "Could not get the text recognizer to work", Toast.LENGTH_SHORT).show();
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                sb.append(textBlock.getValue());
                sb.append("\n");
            }

            String extractedText = sb.toString();
            Toast.makeText(this, "Identity Details extracted successfully", Toast.LENGTH_SHORT).show();


            String savedName = null,savedLname=null,savedDOB=null,savedGender=null,savedPhone=null,savedAAdhar=null;

//Extract name

            String nameStart = "To";
            String nameStart2 = "Government of India";
            int nameStartIndex = extractedText.indexOf(nameStart);
            int nameStartIndex2 = extractedText.indexOf(nameStart2);
            if (nameStartIndex != -1 || nameStartIndex2 != -1) {

                String name;
                if (nameStartIndex != -1) {
                    name = extractedText.substring(nameStartIndex + nameStart.length()).trim();
                } else {
                    name = extractedText.substring(nameStartIndex2 + nameStart2.length()).trim();
                }

                // Extract the name using the regex pattern
                String nameRegex = "[a-zA-Z ]+";
                Pattern pattern = Pattern.compile(nameRegex);
                Matcher matcher = pattern.matcher(name);
                if (matcher.find()) {
//                    String extractedName = matcher.group().trim();
//                    savedName=extractedName;
//                    // Set the name EditText field with the extracted name
//                    nameEditText.setText(extractedName);

                    String extractedName = matcher.group().trim();
                    String[] names = extractedName.split(" ");
                    String efirstName = "";
                    String elastName = "";

                    if (names.length == 1) {
                        efirstName = names[0];
                    } else if (names.length == 2) {
                        efirstName = names[0];
                        elastName = names[1];
                    } else if (names.length > 2) {
                        efirstName = names[0];
                        elastName = names[names.length - 1];
                    }
                    savedName = efirstName;
                    savedLname=elastName;

                    // Set the name EditText fields with the extracted names
                    nameEditText.setText(efirstName);
                    lastEditText.setText(elastName);
                }
            }





            // Extract date of birth
            String dobStart = "DOB:";
            int dobStartIndex = extractedText.indexOf(dobStart);
            if (dobStartIndex != -1) {
                int dobEndIndex = dobStartIndex + dobStart.length() + 11; // assuming DOB format is DD/MM/YYYY
                String dob = extractedText.substring(dobStartIndex + dobStart.length(), dobEndIndex).trim();

                // Convert the date format from DD/MM/YYYY to YYYY/MM/DD
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date date = inputFormat.parse(dob);
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String dobFormatted = outputFormat.format(date);

                    // Set the date of birth EditText field with the extracted date of birth in YYYY/MM/DD format
                    dobEditText.setText(dobFormatted);
                    savedDOB=dobFormatted;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Set the date of birth EditText field with the extracted date of birth

            }

            //Extract gender

            String gender = "";
            Pattern pattern2 = Pattern.compile("(?i)FEMALE|MALE"); // case-insensitive pattern to match "FEMALE" or "MALE"
            Matcher matcher2 = pattern2.matcher(extractedText);
            if (matcher2.find()) {
                gender = matcher2.group().toUpperCase(); // convert to uppercase
                savedGender=gender;
            }
// Set the gender EditText field with the extracted gender
            genderEditText.setText(gender);


            // Define a regular expression pattern for Indian phone numbers
            String phoneRegex = "(\\+91|0)?[6789]\\d{9}";

// Extract the phone number using the regex pattern
            Pattern pattern3 = Pattern.compile(phoneRegex);
            Matcher matcher3 = pattern3.matcher(extractedText);
            if (matcher3.find()) {
                String phoneNumber = matcher3.group().trim();
                // Set the phone number EditText field with the extracted phone number
                mobileEditText.setText(phoneNumber);
                savedPhone=phoneNumber;
            }

            //Extract AAdhar No.
            String aadhaarRegex = "\\b\\d{4}[- ]\\d{4}[- ]\\d{4}\\b"; // matches Aadhaar number in the format XXXX-XXXX-XXXX or XXXX XXXX XXXX
            Pattern pattern = Pattern.compile(aadhaarRegex);
            Matcher matcher = pattern.matcher(extractedText);

            if (matcher.find()) {
                String aadhaar = matcher.group().replaceAll("[^0-9]", ""); // remove any non-digit characters from the matched string
                // Set the Aadhaar EditText field with the extracted Aadhaar number
                savedAAdhar=aadhaar;
                aadharEditText.setText(aadhaar);
            }
            tokenManager.saveExtractedData(savedName,savedLname,savedDOB,savedGender,savedPhone,savedAAdhar);


            Toast.makeText(this, ""+savedName, Toast.LENGTH_SHORT).show();


//            nameEditText.setText(extractedName);
//            addressEditText.setText(extractedAddress);
//            dobEditText.setText(extractedDOB);

//            // Validate extracted information
//            boolean isNameValid = extractedName.matches("[a-zA-Z ]+");
//            boolean isAddressValid = extractedAddress.matches("\\d{6},[a-zA-Z ]+,\\b(\\w+\\s*)+\\b");
//            boolean isDOBValid = extractedDOB.matches("(19|20)\\d{2}");
//
//            // Check if all extracted information is valid
//            if (isNameValid && isAddressValid && isDOBValid) {
//                // If all extracted information is valid, use it to fill out form fields
//                nameEditText.setText(extractedName);
//                addressEditText.setText(extractedAddress);
//                dobEditText.setText(extractedDOB);
//            } else {
//                // If any of the extracted information is invalid, show an error message
//                Toast.makeText(this, "Invalid Aadhar card details", Toast.LENGTH_SHORT).show();
//            }

            // TODO: Implement logic to extract the specific fields from the extracted text

        } catch (IOException e) {
            e.printStackTrace();
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