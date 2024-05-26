package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class home extends AppCompatActivity {

    Button logout;
    FloatingActionButton add_btn;

    FirebaseAuth mAuth;
    FirebaseUser  user;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        logout = findViewById(R.id.logoutfromhome);
        username =findViewById(R.id.username);
        add_btn = findViewById(R.id.add_btn);


        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        add_btn.setOnClickListener(v-> startActivity(new Intent(this, addpatientdetails.class)));

        String greetingMessage;
        greetingMessage = "Hello There!";
        // Get the current user from Firebase Authentication
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userName = currentUser.getEmail() + "";

        // Construct the text to be displayed
        String displayText = greetingMessage + "\n" + userName;

        // Set the text to the TextView
        username.setText(displayText);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Vibration.vibrate();
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            }
        });




    }
}