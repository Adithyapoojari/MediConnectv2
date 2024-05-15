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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;
    RecyclerView recyclerView;
    mainAdaptor mainAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<mainMethod> options =
                new FirebaseRecyclerOptions.Builder<mainMethod>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("patient"), mainMethod.class)
                        .build();

        mainAdaptor = new mainAdaptor(options);
        recyclerView.setAdapter(mainAdaptor);

        // Check if this is the first run of the app
       SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);



            Vibration.init(getApplicationContext());
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
                    //Logout when user clicks
                    FirebaseAuth.getInstance().signOut();
                    // Navigate to login activity
                    startActivity(new Intent(getApplicationContext(), login.class));
                    finish();
                }
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdaptor.startListening();
    }
}