package com.example.mediconnect;

import android.content.Intent;
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

public class logout extends AppCompatActivity {

    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logout);

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.logoutlg);
        textView = findViewById(R.id.user_email);
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}