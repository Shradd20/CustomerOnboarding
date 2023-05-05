package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginapplication.Drawer_Activity;
import com.example.loginapplication.MainActivity;
import com.example.loginapplication.PersonalDetails_Activity;
import com.example.loginapplication.R;
import com.example.loginapplication.databinding.ActivityDashboardBinding;
import com.example.loginapplication.databinding.ActivityDashboardBinding;

public class DashboardActivity extends Drawer_Activity implements View.OnClickListener {
    private CardView personal,emailver,mobile,identityproof,addressproof,logout;
    TextView textView2;
    String userStr;

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        allocateActivityTitle("Customer Onboarding");

       textView2 = findViewById(R.id.textView2);
       userStr=getIntent().getExtras().getString("FirstName");
       textView2.setText("Welcome"+" "+userStr);




        personal = findViewById(R.id.personal);
        emailver = findViewById(R.id.emailver);
        mobile = findViewById(R.id.mobile);
        identityproof = findViewById(R.id.identityproof);
        addressproof = findViewById(R.id.addressproof);
        logout = findViewById(R.id.logout);




        personal.setOnClickListener((View.OnClickListener) this);
        emailver.setOnClickListener((View.OnClickListener) this);
        mobile.setOnClickListener((View.OnClickListener) this);
        identityproof.setOnClickListener((View.OnClickListener) this);
        addressproof.setOnClickListener((View.OnClickListener) this);
        logout.setOnClickListener((View.OnClickListener) this);



    }
    @Override
    public void onClick(View v){
        Intent i;
        switch(v.getId()){
            case R.id.personal:i=new Intent(this, PersonalDetails_Activity.class); startActivity(i); break;
            case R.id.emailver:i=new Intent(this, Email_Verification_Activity.class); startActivity(i); break;
            case R.id.mobile:i=new Intent(this, Phone_verify_Activity.class); startActivity(i); break;
            case R.id.identityproof:i=new Intent(this, Identity_Proof_Activity.class); startActivity(i); break;
            case R.id.addressproof:i=new Intent(this, AddressProofActivity.class); startActivity(i); break;
            case R.id.logout:i=new Intent(this, PersonalDetails_Activity.class); startActivity(i); break;

        }

    }


}