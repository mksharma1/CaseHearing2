package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Date;

public class Add_Hearing extends AppCompatActivity implements View.OnClickListener {

    EditText case_id,advocate_name,case_title,ndh,purpose;
    Button submit;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton radioButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Advocates = db.collection("Advocates");
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = db.document("CaseHearings/first_hearing");
    private String advocates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hearing);

        case_id = findViewById(R.id.case_id);
        final Spinner advocate_name = (Spinner) findViewById(R.id.advocate_name);
        case_title = findViewById(R.id.case_title);
        ndh = findViewById(R.id.ndh);
        purpose = findViewById(R.id.purpose);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        radioGroup = findViewById(R.id.case_type);
        advocate_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = advocate_name.getSelectedItem().toString();
                advocates = name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadAdvocates();
    }

    public void loadAdvocates() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Advocates.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 progressDialog.cancel();
                ArrayList<String> Advocates = new ArrayList<>();
                Advocates.add("Advocate Name");
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    DB_Advocates db_advocates = documentSnapshot.toObject(DB_Advocates.class);
                    Advocates.add(db_advocates.getAdvocate());

                }
                Spinner spinner = (Spinner) findViewById(R.id.advocate_name);
                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout, Advocates);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==submit)
        {
          addHearing();
        }
    }

public void addHearing() {
        int s = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(s);
        try {
            String case_type = radioButton.getText().toString();
            String id = case_id.getText().toString();
            String title = case_title.getText().toString();
            final String get_ndh = ndh.getText().toString();
            String case_purpose = purpose.getText().toString();


            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
            String last_updated = dateFormat.format(date);

    if(!(radioButton.isChecked()))
    {
        radioButton.setError("Case Title is required");
        radioButton.requestFocus();
        return;
    }

            if (id.isEmpty()) {
                case_id.setError("Case ID is required");
                case_id.requestFocus();
                return;
            }
            if (advocates.isEmpty()) {
                advocate_name.setError("Advocate Name is required");
                advocate_name.requestFocus();
                return;
            }
            if (title.isEmpty()) {
                case_title.setError("Case Title is required");
                case_title.requestFocus();
                return;
            }
            if (get_ndh.isEmpty()) {
                ndh.setError("NDH is required");
                ndh.requestFocus();
                return;
            }
            if (case_purpose.isEmpty()) {
                purpose.setError("Purpose is required");
                purpose.requestFocus();
                return;
            }

            progressDialog.setMessage("Saving Info...");
            progressDialog.show();

            DB_CaseHearing db_caseHearing = new DB_CaseHearing(case_type, id, advocates, title, get_ndh, case_purpose, last_updated);

            CaseHearing.add(db_caseHearing).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });

        }catch (Exception e){}

}

}
