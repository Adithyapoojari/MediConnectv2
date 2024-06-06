package com.example.mediconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
    TextView textView,forgotPassword;

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
        forgotPassword = findViewById(R.id.forgotPassword);
        Vibration.init(this);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText resetMail = dialogView.findViewById(R.id.emailtext);
                Button btnReset = dialogView.findViewById(R.id.resetbtn);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.resetbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = resetMail.getText().toString();

                        if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Utility.showToast(login.this,"Enter Your Registered Email Id");
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Utility.showToast(login.this,"Check Your Email!");
                                    Vibration.vibrate();
                                    dialog.dismiss();
                                }else{
                                    Utility.showToast(login.this,"Unable to send reset mail");
                                    Vibration.vibrate();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
        textView.setOnClickListener(v -> startActivity(new Intent(this, registration.class)));
        buttonLog.setOnClickListener(v -> loginUser());
    }

    void loginUser(){
       String email = editTextEmail.getText().toString();
       String password = editTextPassword.getText().toString();

       boolean is_valid = validateData(email,password);
       if(!is_valid){
           return;
       }
       loginAccountInFireBase(email,password);
   }

   void loginAccountInFireBase(String email, String password) {
       FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();

       firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login.this,
               new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   if(firebaseAuth.getCurrentUser().isEmailVerified()){
                       startActivity(new Intent(login.this,home.class));
                       finish();
                   }else{
                       Utility.showToast(login.this,
                               "Email Not Verified!Please Verify Your Email");
                   }
               }else{
                   Utility.showToast(login.this,task.getException().getLocalizedMessage());
               }
           }
       });
   }

   boolean validateData(String email, String password) {
       if (email.isEmpty()) {
           editTextEmail.setError("Email is required");
           return false;
       }
       if (password.isEmpty()) {
           editTextPassword.setError("Password is required");
           return false;
       }
       if (password.length() < 7) {
           editTextPassword.setError("Password is constrained to more than 7 characters");
           return false;
       }

       return true;
   }
}