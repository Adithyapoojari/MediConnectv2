package com.example.mediconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;


public class diagnosisHistory extends AppCompatActivity {
    FloatingActionButton add_btn;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    diagnosisadaptor diagnosisadaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diagnosis_history);

        add_btn = findViewById(R.id.add_btn);
        recyclerView = findViewById(R.id.recyclerView);

        add_btn.setOnClickListener(v-> startActivity(new Intent(this, addpatientdetails.class)));

        bottomNavigationView = findViewById(R.id.bottom_navigator);

        //for current page value
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("All Records")){
                    return true;
                }
                else if(item.getTitle().equals("Profile")){
                    startActivity(new Intent(getApplicationContext(), profile.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                else if(item.getTitle().equals("Home")){
                    startActivity(new Intent(getApplicationContext(), home.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                return false;
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceFromUsers().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<diagnosis> options = new FirestoreRecyclerOptions.Builder<diagnosis>()
                .setQuery(query, diagnosis.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diagnosisadaptor = new diagnosisadaptor(options,this);
        recyclerView.setAdapter(diagnosisadaptor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        diagnosisadaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        diagnosisadaptor.stopListening();
    }
    @Override
    protected void onResume() {
        super.onResume();
        diagnosisadaptor.notifyDataSetChanged();
    }

}
