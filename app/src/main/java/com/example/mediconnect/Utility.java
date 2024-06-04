package com.example.mediconnect;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceFromNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes").
                document(currentUser.getUid()).collection(("my_notes"));

    }
//to convert timestamp to string
    static String timestamptoString(Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
    //to convert string to timestamp
    public static Timestamp stringToTimestamp(String dateString) {
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            return new Timestamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static CollectionReference getCollectionReferenceFromUsers() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("diagnosis")//diagnosis collection to store everything
                .document(currentUser.getUid()).collection("mydiagnosis");//mydiagnosis collection to store the user's diagnosis

    }
}
