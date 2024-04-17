package com.example.mediconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class landing extends AppCompatActivity {


    TextView readMore;
    Button btnReg,btnReadMore;
    LinearLayout landingDetails;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public void onStart() {
        super.onStart();


        mAuth = FirebaseAuth.getInstance();

        // Checking for user present or not and if already registered
        FirebaseUser currentUser = mAuth.getCurrentUser();
        user = mAuth.getCurrentUser();

        if (user == null) {
            // If user is not logged in, navigate to login activity
            startActivity(new Intent(getApplicationContext(), login.class));
            finish(); // Finish MainActivity to prevent returning to it from login
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);
        readMore = findViewById(R.id.readMore);
        btnReg=findViewById(R.id.buttonRegister);
        landingDetails =findViewById(R.id.landing_controls);
        btnReadMore=findViewById(R.id.buttonReadMore);




        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registration.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void toggleDetails(View view){
        if(readMore.getVisibility() == View.VISIBLE){
            readMore.setVisibility(View.GONE);
            btnReadMore.setText("Read More");
            landingDetails.setVisibility(View.VISIBLE);
        }else{
            readMore.setVisibility(View.VISIBLE);
            btnReadMore.setText("Go Back");
            landingDetails.setVisibility(View.GONE);

        }
    }
}