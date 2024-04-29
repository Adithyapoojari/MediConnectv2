package com.example.mediconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLog;
    FirebaseAuth mAuth;
    TextView textView;

   @Override
    public void onStart() {
        super.onStart();

        // Checking for user present or not
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
            finish();
        }
       SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putBoolean("isFirstRun", false);
       editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLog = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.registernow);
        Vibration.init(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to registration on logout
                Intent intent = new Intent(getApplicationContext(),registration.class);
               startActivity(intent);
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                Toast.makeText(login.this,"Logging In, PLease Wait.",Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login.this,"Enter email",Toast.LENGTH_SHORT).show();
                    Vibration.vibrate();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"Enter password",Toast.LENGTH_SHORT).show();
                    Vibration.vibrate();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success display toast msg
                                    Toast.makeText(login.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Vibration.vibrate();
                                    Intent intent = new Intent(getApplicationContext(), home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    Vibration.vibrate();
                                }
                            }
                        });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}