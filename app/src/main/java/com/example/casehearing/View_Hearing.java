package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class View_Hearing extends AppCompatActivity {

    TextView hearings;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = db.document("CaseHearings/first_hearing");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hearing);

        hearings = findViewById(R.id.hearings);
        progressDialog = new ProgressDialog(this);
        loadHearings();

    }

    public void loadHearings()
    {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        CaseHearing.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.cancel();
                int serial_no = 1;
                String hearing = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    DB_CaseHearing db_caseHearing = documentSnapshot.toObject(DB_CaseHearing.class);
                    String case_type = db_caseHearing.getCase_type();
                    String advocate_name = db_caseHearing.getAdvocate_name();
                    String  case_title = db_caseHearing.getCase_title();
                    String ndh = db_caseHearing.getNdh();
                    String purpose = db_caseHearing.getPurpose();
                    String last_updated = db_caseHearing.getLast_updated();

                    hearing += serial_no+ ".Case Type : " +case_type + "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
                    serial_no++;
                }
                hearings.setText(hearing);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }

}
