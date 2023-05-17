package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.loginapplication.databinding.ActivityFinalReportBinding;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FinalReportActivity extends  Drawer_Activity {

    Button verify_result_button,buttonSubmit;
    private TokenManager tokenManager;
    TextView firstName;
    TextView lastName;
    TextView mobile;
    TextView dob;
    TextView email;
    TextView aadhar;
    TextView gender;
    TextView address;

    private RelativeLayout mainLayout;
    private RelativeLayout formLayout;
    private boolean isFormVisible = false;


    ActivityFinalReportBinding activityFinalReportBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);
        activityFinalReportBinding=ActivityFinalReportBinding.inflate(getLayoutInflater());
        setContentView(activityFinalReportBinding.getRoot());
        allocateActivityTitle("Verification of Doucuments");

        verify_result_button = findViewById(R.id.verify_result_button);
        mainLayout = findViewById(R.id.main_layout);
        formLayout = findViewById(R.id.form_layout);



        verify_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFormVisible) {
                    // Hide the VerifyResult button
                    verify_result_button.setVisibility(View.GONE);

                    // Show the form layout
                    formLayout.setVisibility(View.VISIBLE);
                    isFormVisible = true;
                }
            }
        });


        tokenManager = new TokenManager(getApplicationContext());
        firstName = findViewById(R.id.firstName);
        // addressEditText = findViewById(R.id.addressEditText);
        dob = findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        mobile=findViewById(R.id.mobile);
        aadhar=findViewById(R.id.aadhar);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        buttonSubmit=findViewById(R.id.buttonSubmit);
        address=findViewById(R.id.address);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to download the form
                String first=tokenManager.getTokenExtractedData().getfName();
                String last=tokenManager.getTokenExtractedData().getlName();
                String dob=tokenManager.getTokenExtractedData().getDate();
                String mob=tokenManager.getTokenExtractedData().getMob();
                String gender=tokenManager.getTokenExtractedData().getGen();
                String email=tokenManager.getData().getEmail();
                String aadhar=tokenManager.getTokenExtractedData().getaNo();
                //New
                String address=tokenManager.getTokenExtractedAddressData().getAddress();

                try{
                    createPDF(first,last,dob,mob,gender,email,aadhar,address);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });


// Retrieve saved extracted data

        String extractedName = tokenManager.getTokenExtractedData().getfName();

        //String extractedsName=tokenManager.getTokenExtractedData().getsName();
        String extractedlName=tokenManager.getTokenExtractedData().getlName();
        Log.e("TAG", "AAdhar: " + extractedlName);
        String extractedDOB = tokenManager.getTokenExtractedData().getDate();
        String extractedGender = tokenManager.getTokenExtractedData().getGen();
        String extractedPhone = tokenManager.getTokenExtractedData().getMob();
        String extractedAdhar = tokenManager.getTokenExtractedData().getaNo();

        // Get user-input data from EditText fields
        String eName = tokenManager.getData().getfName();
        Log.e("TAG", "Not AAdhar: " + eName);
        String eEmail=tokenManager.getData().getEmail();
        //String eSName=tokenManager.getData().getsName();
        String eLname=tokenManager.getData().getlName();
        String eDob = tokenManager.getData().getDate();
        String eGender = tokenManager.getData().getGen();
        String ePhone = tokenManager.getData().getMob();
        String eAdhar = tokenManager.getData().getaNo();

// Compare the extracted data with user-input data

        email.setText(eEmail);

        if (eName.equals(extractedName)) {
            firstName.setText(extractedName);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "FirstName Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        if (extractedlName.equals(eLname)) {
            // Data matches, proceed with next steps
            // ...
            lastName.setText(extractedlName);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        if (extractedDOB.equals(eDob)) {
            // Data matches, proceed with next steps
            // ...
            dob.setText(extractedDOB);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        if (extractedGender.equals(eGender)) {
            // Data matches, proceed with next steps
            // ...
            gender.setText(extractedGender);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        if (extractedPhone.equals(ePhone)) {
            // Data matches, proceed with next steps
            // ...
            mobile.setText(extractedPhone);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        if (extractedAdhar.equals(eAdhar)) {
            // Data matches, proceed with next steps
            // ...
            aadhar.setText(extractedAdhar);
        } else {
            // Data does not match, prompt user to re-enter correct data
            Toast.makeText(FinalReportActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
        }

        address.setText(tokenManager.getTokenExtractedAddressData().getAddress());

        Toast.makeText(FinalReportActivity.this, "Your Details are Verified Successfully and this is your Final Report", Toast.LENGTH_SHORT).show();
    }


    private void createPDF(String first, String last, String dob, String mob, String gender,String email, String aadhar, String address) throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "VerifiedDetailsUser.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument=new PdfDocument(writer);
        Document document=new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0,0,0,0);

        Drawable d=getDrawable(R.drawable.header);
        Bitmap bitmap=((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData=stream.toByteArray();

        ImageData imageData= ImageDataFactory.create(bitmapData);
        Image image=new Image(imageData);

        Paragraph verifiedReport= new Paragraph("Verified Report").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        Paragraph goup=new Paragraph("Below Report verifies user's identity \n " +
                "based on aadhar card").setTextAlignment(TextAlignment.CENTER).setFontSize(12);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Paragraph day=new Paragraph("Report generated on: ").setTextAlignment(TextAlignment.CENTER).setFontSize(12);
            Paragraph daydate=new Paragraph(LocalDate.now().format(dateTimeFormatter).toString());
        }



        float[] width={100f,100f};
        Table table=new Table(width);

        table.setHorizontalAlignment(HorizontalAlignment.CENTER);


        table.addCell(new Cell().add(new Paragraph("Verified First Name as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(first)));

        table.addCell(new Cell().add(new Paragraph("Verified Last Name as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(last)));


        table.addCell(new Cell().add(new Paragraph("Verified Date of Birth as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(dob)));

        table.addCell(new Cell().add(new Paragraph("Verified Gender as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(gender)));

        table.addCell(new Cell().add(new Paragraph("Verified Mobile Number as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(mob)));

        table.addCell(new Cell().add(new Paragraph("Verified Email")));
        table.addCell(new Cell().add(new Paragraph(email)));

        table.addCell(new Cell().add(new Paragraph("Verified Aadhar Number")));
        table.addCell(new Cell().add(new Paragraph(aadhar)));

        table.addCell(new Cell().add(new Paragraph("Verified Address as per Aadhar Card")));
        table.addCell(new Cell().add(new Paragraph(address)));


        BarcodeQRCode qrCode=new BarcodeQRCode(first+"\n"+last+"\n"+dob+"\n"+gender+"\n"+mob+"\n"+aadhar+"\n"+email+"\n"+address);
        PdfFormXObject qrCodeObject=qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image qrCodeImage=new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

        document.add(image);
        document.add(verifiedReport);
        document.add(goup);
        document.add(table);
        document.add(qrCodeImage);
        document.close();
        Toast.makeText(this, "Report  is downloaded in Downloads File Folder", Toast.LENGTH_SHORT).show();
    }

}
