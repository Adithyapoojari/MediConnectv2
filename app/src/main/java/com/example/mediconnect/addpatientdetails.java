package com.example.mediconnect;

import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class addpatientdetails extends AppCompatActivity {

    EditText name, contact_no, reason, vital_signs, medication, notes;
    RadioGroup gender;
    TextView pageTitleText;

    FloatingActionButton delete_patient,edit_patient;
    Button add_patient;
    String editname,editcontact_no,editgender,editreason,editvital_signs,editmedication,editnotes,docId;
    boolean isEditMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addpatientdetails);

        name = findViewById(R.id.patientName);
        contact_no = findViewById(R.id.contact_no);
        gender = findViewById(R.id.gender);
        reason = findViewById(R.id.reason);
        vital_signs = findViewById(R.id.vital_signs);
        medication = findViewById(R.id.medication);
        notes = findViewById(R.id.notes);
        add_patient = findViewById(R.id.add_patient);
        pageTitleText= findViewById(R.id.pageTitle);
        delete_patient = findViewById(R.id.delete_btn);
        edit_patient = findViewById(R.id.save_btn);



        //receive for edit
        editname = getIntent().getStringExtra("name");
        editcontact_no = getIntent().getStringExtra("contact_no");
        editgender = getIntent().getStringExtra("gender");
        editreason = getIntent().getStringExtra("reason");
        editvital_signs = getIntent().getStringExtra("vital_signs");
        editmedication = getIntent().getStringExtra("medication");
        editnotes = getIntent().getStringExtra("notes");
        docId = getIntent().getStringExtra("docId");


        if(docId != null && !docId.isEmpty()) {
            isEditMode = true;
            delete_patient.setVisibility(View.VISIBLE);
            edit_patient.setVisibility(View.VISIBLE);
            add_patient.setVisibility(View.GONE);
            //for edit
        }

        name.setText(editname);
        contact_no.setText(editcontact_no);
        reason.setText(editreason);
        vital_signs.setText(editvital_signs);
        medication.setText(editmedication);
        notes.setText(editnotes);

        if(isEditMode){
            pageTitleText.setText("Edit Patient Details");
        }

        add_patient.setOnClickListener((v) -> savePatient());
        delete_patient.setOnClickListener((v)->deletePatientFromFireBase());;
        edit_patient.setOnClickListener((v)->savePatient());

    }



    void savePatient() {
        String stname = this.name.getText().toString();
        String stcontact_no = this.contact_no.getText().toString();
        String stgender = String.valueOf(this.gender.getCheckedRadioButtonId());
        String streason = this.reason.getText().toString();
        String stvital_signs = this.vital_signs.getText().toString();
        String stmedication = this.medication.getText().toString();
        String stnotes = this.notes.getText().toString();

        if (stname == null || stname.isEmpty()) {
            this.name.setError("Name is required");
            return;
        }
        if (stgender == null || stgender.isEmpty()) {
            this.name.setError("Gender is required");
            return;
        }
        if (streason == null || streason.isEmpty()) {
            this.reason.setError("Reason is required");
            return;
        }
        if (stvital_signs == null || stvital_signs.isEmpty()) {
            this.vital_signs.setError("Vital signs is required");
            return;
        }
        if (stmedication == null || stmedication.isEmpty()) {
            this.medication.setError("Medication Prescribed is required");
            return;
        }


        diagnosis diagnosis = new diagnosis();

        diagnosis.setName(stname);
        diagnosis.setContact_no(stcontact_no);
        diagnosis.setGender(stgender);
        diagnosis.setReason(streason);
        diagnosis.setVital_signs(stvital_signs);
        diagnosis.setMedication(stmedication);
        diagnosis.setNotes(stnotes);
        diagnosis.setTimestamp(Timestamp.now());

        // save the diagnosis object to the Firebase firestore database
            saveDiagnosisToFirebase(diagnosis);
    }
    void saveDiagnosisToFirebase(diagnosis diagnosis) {
        DocumentReference documentReference;

        if(isEditMode){//in edit diagnosis pass the docId
            documentReference = Utility.getCollectionReferenceFromUsers().document(docId);
        }else{//if not in edit mode i.e new patient
            documentReference = Utility.getCollectionReferenceFromUsers().document();
        }
        documentReference.set(diagnosis).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //diagnosis is saved to the firebase cloud storage
                    Utility.showToast(addpatientdetails.this, "Diagnosis Saved");
                    finish();
                }else{//if failed to save
                    Utility.showToast(addpatientdetails.this, "Failed to Save");
                }
            }
        });

    }
    private void deletePatientFromFireBase() {
        DocumentReference documentReference;

        documentReference = Utility.getCollectionReferenceFromUsers().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //diagnosis is deleted from the firebase cloud storage
                    Utility.showToast(addpatientdetails.this, "Diagnosis Deleted");
                    finish();
                }else{//if failed to deleted
                    Utility.showToast(addpatientdetails.this, "Failed to Delete");
                }
            }
        });
    }

}