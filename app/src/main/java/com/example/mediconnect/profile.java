package com.example.mediconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class profile extends AppCompatActivity {
    private static final String IMAGE_URI_KEY = "image_uri";
    private SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    ImageView imageView;

    Button logout,change_act,know_more,appIfo;
    TextView userEmail,userName;
    FirebaseAuth mAuth;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        imageView = findViewById(R.id.imageView2);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        userEmail = findViewById(R.id.userEmail);
        userName = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);
        change_act = findViewById(R.id.Create_new);
        know_more = findViewById(R.id.Know_us);
        appIfo = findViewById(R.id.App_Info);

        logout.setOnClickListener(v-> logoout());
        change_act.setOnClickListener(v->newAct());
        know_more.setOnClickListener(v->knowmore());
        appIfo.setOnClickListener(v->appInfo());

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);

        String imageUri = sharedPreferences.getString(IMAGE_URI_KEY, null);
        if (imageUri!= null) {
            imageView.setImageURI(Uri.parse(imageUri));
        }
        else{
            imageView.setImageResource(R.drawable.user_img);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(profile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail() + "";
        //Set the email in the TextView
        userEmail.setText(email);
        //set the name in the textview
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
            databaseReference.child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot dataSnapshot = task.getResult();
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.getValue(String.class);
                            userName.setText(username);
                        } else {
                            userName.setText("Username does not exist");
                        }
                    } else {
                        userName.setText("Error getting username: " + task.getException().getMessage());
                    }
                }
            });
        } else {
            userName.setText("You are not signed in");
        }


        //for navigation of bottom
        bottomNavigationView.setSelectedItemId(R.id.profile);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.grey)));
            }
        }
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

    private void appInfo() {
        Utility.showToast(profile.this, "App Version 1.0");
    }

    private void knowmore() {
        String url = "https://adithyarpoojary.netlify.app";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    private void newAct() {
        // Clear any existing user data or session
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        preferences.edit().clear().apply();

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), landing.class));
        finish();
        Vibration.vibrate();
    }

    private void logoout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), login.class));
        finish();
        Vibration.vibrate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imageView.setImageURI(uri);

        sharedPreferences.edit().putString(IMAGE_URI_KEY, uri.toString()).apply();
    }
}