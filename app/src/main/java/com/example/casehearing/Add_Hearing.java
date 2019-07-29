package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;

public class Add_Hearing extends AppCompatActivity implements View.OnClickListener {

    EditText advocate_name,case_title,ndh,purpose;
    Button submit;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton radioButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = db.document("CaseHearings/first_hearing");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hearing);

        advocate_name = findViewById(R.id.advocate_name);
        case_title = findViewById(R.id.case_title);
        ndh = findViewById(R.id.ndh);
        purpose = findViewById(R.id.purpose);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        radioGroup = findViewById(R.id.case_type);
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
        String case_type = radioButton.getText().toString();
    String name = advocate_name.getText().toString();
    String title = case_title.getText().toString();
    final String get_ndh = ndh.getText().toString();
    String case_purpose = purpose.getText().toString();

    Calendar cal = Calendar.getInstance();
    Date date=cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
    String last_updated = dateFormat.format(date);

    if(name.isEmpty())
    {
        advocate_name.setError("Advocate Name is required");
        advocate_name.requestFocus();
        return;
    }
    if(title.isEmpty())
    {
        case_title.setError("Case Title is required");
        case_title.requestFocus();
        return;
    }
    if(get_ndh.isEmpty())
    {
        ndh.setError("NDH is required");
        ndh.requestFocus();
        return;
    }
    if(case_purpose.isEmpty())
    {
        purpose.setError("Purpose is required");
        purpose.requestFocus();
        return;
    }

    progressDialog.setMessage("Saving Info...");
    progressDialog.show();

    DB_CaseHearing db_caseHearing = new DB_CaseHearing(case_type,name, title, get_ndh, case_purpose, last_updated);

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

}

}
