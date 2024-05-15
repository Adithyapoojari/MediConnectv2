package com.example.mediconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class mainAdaptor extends FirebaseRecyclerAdapter<mainMethod, mainAdaptor.myviewHolder> {
    public mainAdaptor(@NonNull FirebaseRecyclerOptions<mainMethod> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder mainAdaptor, int i, @NonNull mainMethod mainMethod) {
        mainAdaptor.name.setText(mainMethod.getName());
        mainAdaptor.diagnosis.setText(mainMethod.getDiagnosis());
        mainAdaptor.date.setText(mainMethod.getDate());
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_display_patients,parent,false);
        return new myviewHolder(view);

    }

    class myviewHolder extends RecyclerView.ViewHolder{

        TextView name,diagnosis,date;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.nameText);
            diagnosis = (TextView)itemView.findViewById(R.id.diagnosisText);
            date = (TextView)itemView.findViewById(R.id.dateText);
        }
    }

}
