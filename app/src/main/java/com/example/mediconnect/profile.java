package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottom_navigator);

        //for navigation of bottom
        bottomNavigationView.setSelectedItemId(R.id.home);

// Assuming you have a BottomNavigationView variable named bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("Profile")){
                    return true;

                }
                else if(item.getTitle().equals("Home")){
                    startActivity(new Intent(getApplicationContext(), home.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                else if(item.getTitle().equals("All Records")){
                    startActivity(new Intent(getApplicationContext(), diagnosisHistory.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                return false;
            }
        });
    }
}