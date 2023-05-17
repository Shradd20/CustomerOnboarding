package com.example.loginapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapplication.databinding.ActivityAddressProofBinding;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressProofActivity extends  Drawer_Activity {
    ActivityAddressProofBinding activityAddressProofBinding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap imageBitmap;
    ImageView aadharImageView;
    TextView addressEditText;
    Button processButton;
    Button nextButton, saveButton,buttonSubmit;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_proof);
        activityAddressProofBinding = ActivityAddressProofBinding.inflate(getLayoutInflater());
        setContentView(activityAddressProofBinding.getRoot());
        allocateActivityTitle("Proof of Address");

        tokenManager = new TokenManager(this);


        aadharImageView = findViewById(R.id.aadharimageView);
        processButton = findViewById(R.id.processButton);
        addressEditText = findViewById(R.id.addressEditText);
        //secondEditText=findViewById(R.id.secondEditText);
        nextButton = findViewById(R.id.buttonSubmit);
        saveButton = findViewById(R.id.uploadButton);

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddressProofActivity.this, "Address Proof Document has been uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddressProofActivity.this,FinalReportActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            aadharImageView.setImageURI(imageUri);
            recognizeTextFromImage(imageUri);
        }

    }

    private void recognizeTextFromImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

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
            Toast.makeText(this,""+extractedText,Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Address Extracted Sucessfully" , Toast.LENGTH_SHORT).show();


            String savedAddress = null;

//correct
//            // Define the address start pattern
//            String addressStartPattern = "india";
//
//// Define the address end pattern
//            String addressEndPattern = "1947";
//
//// Find the address start index
//            int addressStartIndex = extractedText.indexOf(addressStartPattern);
//
//// Find the address end index
//            int addressEndIndex = extractedText.indexOf(addressEndPattern, addressStartIndex + addressStartPattern.length());
//
//// Extract the address if the start and end indexes are valid
//            if (addressStartIndex != -1 && addressEndIndex != -1 && addressStartIndex < addressEndIndex) {
//                String address = extractedText.substring(addressStartIndex + addressStartPattern.length(), addressEndIndex).trim();
//                addressEditText.setText(address);
//                // Do something with the extracted address
//                // ...
//            }
//correct end

            // Get the lastName from tokenManager
            String lastNames = tokenManager.getTokenExtractedData().getlName();
            String mobileno=tokenManager.getTokenExtractedData().getMob();

// Define the address start patterns
            String[] addressStartPatterns = {"india", lastNames,"tndia"};

// Define the address end patterns
           // String[] addressEndPatterns = {"1947", "Mobile:"};
            String[] addressEndPatterns={"1947",mobileno};

// Find the address start index
            int addressStartIndex = -1;
            String foundStartPattern = null;
            for (String addressStartPattern : addressStartPatterns) {
                addressStartIndex = extractedText.indexOf(addressStartPattern);
                if (addressStartIndex != -1) {
                    foundStartPattern = addressStartPattern;
                    break;
                }
            }

// Find the address end index
            int addressEndIndex = -1;
            String foundEndPattern = null;
            for (String addressEndPattern : addressEndPatterns) {
                addressEndIndex = extractedText.indexOf(addressEndPattern, addressStartIndex + 1);
                if (addressEndIndex != -1) {
                    foundEndPattern = addressEndPattern;
                    break;
                }
            }

// Extract the address if the start and end indexes are valid
            if (addressStartIndex != -1 && addressEndIndex != -1 && addressStartIndex < addressEndIndex) {
                int addressStart = addressStartIndex + foundStartPattern.length();
                int addressEnd = addressEndIndex;
                String address = extractedText.substring(addressStart, addressEnd).trim();
                addressEditText.setText(address);
                savedAddress=address;
                tokenManager.saveExtractedAddress(savedAddress);
                // Do something with the extracted address
                // ...
            }


//
//     //Extract Address
//
//                    String address = "";
//
//// Patterns to look for in the extracted text
//            String[] addressPatterns = {"Address:", "Addr:", "Adrs:", "Adrs", "Add:"};
//
//// Loop through the address patterns to find the address
//            for (String pattern : addressPatterns) {
//                int addressStartIndex = extractedText.indexOf(pattern);
//                if (addressStartIndex != -1) {
//                    // Extract the address from the pattern match
//                    address = extractedText.substring(addressStartIndex + pattern.length()).trim();
//                    break;
//                }
//            }
//
//// If address is still empty, try to extract it by searching for other address-related keywords
//            if (address.isEmpty()) {
//                // Add more address-related keywords or phrases as necessary
//                String[] addressKeywords = {"Street", "Road", "Lane", "Avenue", "Block", "Colony", "Sector", "Village", "Town", "City", "State", "Pincode", "Pin code", "PIN code", "PINCODE","PIN Code","Flat","District","Road"};
//                for (String keyword : addressKeywords) {
//                    int keywordIndex = extractedText.indexOf(keyword);
//                    if (keywordIndex != -1) {
//                        // Extract the address from the keyword match
//                        address = extractedText.substring(keywordIndex).trim();
//                        break;
//                    }
//                }
//            }
//
//            String addressStart = tokenManager.getTokenExtractedData().getlName().toString();
//            String adressStart2 = "Address: ";
//            String addressEnd = "Mobile";
//            String addressEnd2 = tokenManager.getTokenExtractedData().getaNo().toString();

//            String addressStart = "india";
//            //String addressEnd =tokenManager.getTokenExtractedData().getaNo().toString();
//            String adressStart2 = tokenManager.getTokenExtractedData().getlName().toString();
//            String addressEnd = "1947";
//            String addressEnd2 = "Mobile";
//
////            int addressStartIndex = extractedText.indexOf(addressStart);
////            int addressStartIndex2 = extractedText.indexOf(adressStart2);
////            int addressEndIndex = extractedText.indexOf(addressEnd);
////            int addressEndIndex2 = extractedText.indexOf(addressEnd2);
////
////
////            String Address = "";
////            int addressEndPos = -1;
////
////            // Find the starting position of the address
////            if ((addressStartIndex != -1 || addressStartIndex2 != -1) && (addressEndIndex != -1 || addressEndIndex2 != -1)) {
////                Address = extractedText.substring(addressStartIndex + addressStart.length()).trim();
////            } else {
////                Address = extractedText.substring(addressStartIndex2 + adressStart2.length()).trim();
////            }
////            // Find the ending position of the address
////            if (addressEndIndex != -1) {
////                addressEndPos = addressEndIndex;
////            } else {
////                addressEndPos = addressEndIndex2;
////            }
////            // Extract the address substring
////            if (addressEndPos != -1) {
////                int endIndex = extractedText.indexOf(" ", addressEndPos);
////                if (endIndex == -1) {
////                    Address = Address.substring(0, addressEndPos - Address.length()).trim();
////                } else {
////                    Address = Address.substring(0, endIndex - Address.length()).trim();
////                }
////            }
//            int addressStartIndex = Math.max(extractedText.indexOf(addressStart), extractedText.indexOf(adressStart2));
//            int addressEndIndex = addressEndIndex = Math.min(extractedText.indexOf(addressEnd), extractedText.indexOf(addressEnd2));
//
//            if (addressStartIndex != -1 && addressEndIndex != -1) {
//                int startIndex, endIndex;
//                if (addressStartIndex == extractedText.indexOf(addressStart)) {
//                    startIndex = addressStartIndex + addressStart.length();
//                    endIndex = addressEndIndex;
//                } else {
//                    startIndex = extractedText.indexOf(adressStart2) + adressStart2.length();
//                    endIndex = extractedText.indexOf(addressEnd2);
//                }
//                String extractedAddress = extractedText.substring(startIndex, endIndex).trim();
//                extractedAddress = extractedAddress.replaceAll("[^\\p{L}\\p{N}\\s]+", "").trim();
//                addressEditText.setText(extractedAddress);

            // Set the address EditText field with the extracted address
//            addressEditText.setText(Address);
//            savedAddress=Address;
//            tokenManager.saveExtractedAddress(savedAddress);


//            if ((addressStartIndex != -1 || addressStartIndex2 != -1) && (addressEndIndex != -1 || addressEndIndex2 != -1)) {
//
//                // Check which addressStartIndex to use
//                String addressN;
//                int addresssEndIndex;
//                if (addressStartIndex != -1) {
//                    addressN = extractedText.substring(addressStartIndex + addressStart.length(), addressEndIndex).trim();
//                    addresssEndIndex = addressEndIndex;
//                } else {
//                    addressN = extractedText.substring(addressStartIndex2 + adressStart2.length(), addressEndIndex2).trim();
//                    addresssEndIndex = addressEndIndex2;
//                }
//
//                // Extract the address
//                String addressS;
//                if (extractedText.contains(addressEnd2)) {
//                    addressS = extractedText.substring(addresssEndIndex + addressEnd2.length(), extractedText.indexOf(addressEnd2)).trim();
//                } else {
//                    addressS = extractedText.substring(addresssEndIndex + addressEnd.length(), extractedText.length()).trim();
//                }
//
//                // Set the address EditText field with the extracted address
//                savedAddress = addressS;
//                addressEditText.setText(addressS);
//                tokenManager.saveExtractedAddress(savedAddress);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
