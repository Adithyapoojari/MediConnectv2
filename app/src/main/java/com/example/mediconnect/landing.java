package com.example.mediconnect;

import android.content.Context;
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

public class landing extends AppCompatActivity {

    ImageView logo;
    TextView appname,urdiagnosis,passage,readMore;
    Button btnReg, btnReadMore;
    LinearLayout landingDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);
        logo = findViewById(R.id.logo);
        appname = findViewById(R.id.appname);
        urdiagnosis = findViewById(R.id.urDiag);
        passage = findViewById(R.id.passage);
        btnReg = findViewById(R.id.buttonRegister);
        btnReadMore = findViewById(R.id.buttonReadMore);
        readMore = findViewById(R.id.readMore);
        landingDetails =findViewById(R.id.landing_controls);


        SharedPreferences sharedPreferences = getSharedPreferences("my_preferances", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun",false);
        editor.apply();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void toggleDetails(View view){
        if(readMore.getVisibility() == View.VISIBLE){
            readMore.setVisibility(View.GONE);
            landingDetails.setVisibility(View.VISIBLE);
        }else{
            readMore.setVisibility(View.VISIBLE);
            landingDetails.setVisibility(View.GONE);

        }
    }
}