package com.example.loginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class Drawer_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolBar;
    @Override

    public void setContentView(View view){
        drawerLayout= (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer,null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        toolBar=drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.Home:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.Profile:
                startActivity(new Intent(this,PersonalDetails_Activity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.Tips:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.Logout:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(0,0);
                break;
        }
        return false;
    }

    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}