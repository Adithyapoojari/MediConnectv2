package com.example.mediconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Check if this is the first run of the app
       SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // If it's the first run, launch the landing activity
            startActivity(new Intent(this, landing.class));
            finish(); // Finish MainActivity to prevent returning to it from landing
        } else {
            // If not the first run, proceed with user authentication flow
            setContentView(R.layout.activity_main);
        }

            auth = FirebaseAuth.getInstance();
            btn = findViewById(R.id.logout);
            textView = findViewById(R.id.user_details);
            user = auth.getCurrentUser();

            if (user == null) {
                // If user is not logged in, navigate to login activity
                startActivity(new Intent(getApplicationContext(), login.class));
                finish(); // Finish MainActivity to prevent returning to it from login
            } else {
                // User is logged in, display their email and allow logout
                textView.setText(user.getEmail());
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform logout
                    FirebaseAuth.getInstance().signOut();
                    // Navigate to login activity
                    startActivity(new Intent(getApplicationContext(), login.class));
                    finish(); // Finish MainActivity after logout
                }
            });

            // Apply padding for system bars
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }