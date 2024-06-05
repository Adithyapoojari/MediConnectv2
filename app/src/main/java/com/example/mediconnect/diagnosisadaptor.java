package com.example.mediconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class diagnosisadaptor extends FirestoreRecyclerAdapter<diagnosis, diagnosisadaptor.diagnosisViewHolder> {
    private List<diagnosis> diagnosisList;
    private List<diagnosis> diagnosisListFull; // Store the full list for resetting
    Context context;

    public diagnosisadaptor(@NonNull FirestoreRecyclerOptions<diagnosis> options, Context context) {
        super(options);
        this.context = context;
        this.diagnosisList = new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull diagnosisViewHolder holder, int position, @NonNull diagnosis model) {
        holder.patientNametextView.setText(model.getName());
        holder.reasontextview.setText(model.getReason());
        holder.datetextview.setText(Utility.timestamptoString(model.getTimestamp()));

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, addpatientdetails.class);
            intent.putExtra("name", model.getName());
            intent.putExtra("reason", model.getReason());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            intent.putExtra("medication", model.getMedication());
            intent.putExtra("vital_signs", model.getVital_signs());
            intent.putExtra("contact_no", model.getContact_no());
            intent.putExtra("notes", model.getNotes());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public diagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerdiagnosis, parent, false);
        return new diagnosisViewHolder(view);
    }


    class diagnosisViewHolder extends RecyclerView.ViewHolder {

        TextView patientNametextView, reasontextview, datetextview;

        public diagnosisViewHolder(@NonNull View itemView) {
            super(itemView);
            patientNametextView = itemView.findViewById(R.id.patientNametextView);
            reasontextview = itemView.findViewById(R.id.reasontextview);
            datetextview = itemView.findViewById(R.id.datetextview);
        }
    }
}
