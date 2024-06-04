package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class home extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser  user;
    TextView username;
    ImageButton menu_btn;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton viewHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        username =findViewById(R.id.username);
        menu_btn = findViewById(R.id.menu);
        bottomNavigationView = findViewById(R.id.bottom_navigator);

        //for navigation of bottom
        bottomNavigationView.setSelectedItemId(R.id.home);

// Assuming you have a BottomNavigationView variable named bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("Profile")){
                    startActivity(new Intent(getApplicationContext(), profile.class));
                    overridePendingTransition(0,0);
                    finish();

                }
                else if(item.getTitle().equals("Home")){
                    return true;
                }
                else if(item.getTitle().equals("All Records")){
                    startActivity(new Intent(getApplicationContext(), diagnosisHistory.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                return false;
            }
        });

        menu_btn.setOnClickListener(v-> showMenu());

        String greetingMessage;
        greetingMessage = "Hello There!";
        // Get the current user from Firebase Authentication
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userName = currentUser.getEmail() + "";

        // Construct the text to be displayed
        String displayText = greetingMessage + "\n" + userName;

        // Set the text to the TextView
        username.setText(displayText);
    }

    void showMenu(){
        PopupMenu popupmenu = new PopupMenu(this,menu_btn);
        popupmenu.getMenu().add("Profile");
        popupmenu.getMenu().add("Share App");
        popupmenu.getMenu().add("Info");
        popupmenu.getMenu().add("Logout");
        popupmenu.show();

        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Profile")){
                    startActivity(new Intent(getApplicationContext(), profile.class));
                    finish();
                }
                else if(item.getTitle().equals("Share App")){
                    Utility.showToast(home.this, "In Development");
                }
                else if(item.getTitle().equals("Info")){
                    Utility.showToast(home.this, "App Version 1.0");
                }
                else if(item.getTitle().equals("Logout")){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), login.class));
                    finish();
                    Vibration.vibrate();
                    return true;
                }
                return false;
            }
        });

    }


}