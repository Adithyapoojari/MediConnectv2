package com.example.mediconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    EditText editTextEmail, editTextPassword,c_passwordtext,username;
    Button buttonReg;
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser= mAuth.getCurrentUser();
        user = mAuth.getCurrentUser();

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
        setContentView(R.layout.activity_registration);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.loginto);
        c_passwordtext = findViewById(R.id.confirm_password);
        username = findViewById(R.id.userName);
        Vibration.init(this);


        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();


        textView.setOnClickListener(v -> startActivity(new Intent(this, login.class)));

        buttonReg.setOnClickListener(v -> create_Account());
    }
    void create_Account(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String c_password = c_passwordtext.getText().toString();
        String username = this.username.getText().toString();
        boolean is_valid = validateData(email,password,c_password);
        if(!is_valid){
            return;
        }
        createAccountInFireBase(email,password,username);
    }
    void createAccountInFireBase(String email, String password,String username){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(registration.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Utility.showToast(registration.this,"Account created successfully! Check Email For Verification");
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            String username = registration.this.username.getText().toString();
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
                            databaseReference.child("username").setValue(username);
                            firebaseAuth.signOut();
                            startActivity(new Intent(registration.this,login.class));
                        }else{
                            Utility.showToast(registration.this,task.getException().getLocalizedMessage());
                        }
                    }
                });
    }



    boolean validateData(String email, String password, String c_password) {
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            return false;
        }
        if (c_password.isEmpty()) {
            c_passwordtext.setError("Confirm Password is required");
            return false;
        }
        if (password.length() < 7 || c_password.length()<7) {
            editTextPassword.setError("Password must be at least 7 characters");
            return false;
        }
        if (!password.equals(c_password)) {
            c_passwordtext.setError("Password does not match");
            return false;
        }
        return true;
    }
}
