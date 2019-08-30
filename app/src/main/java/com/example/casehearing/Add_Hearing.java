package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

    EditText case_id,advocate_name,case_title,ndh,doA;
    Button submit;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton_civil,radioButton_criminal;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Advocates = db.collection("Advocates");
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = db.document("CaseHearings/first_hearing");
    private String advocates,purposes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hearing);

        case_id = findViewById(R.id.case_id);
        doA = findViewById(R.id.doa);
        final Spinner advocate_name = (Spinner) findViewById(R.id.advocate_name);
        case_title = findViewById(R.id.case_title);
        ndh = findViewById(R.id.ndh);
        ndh.setOnClickListener(this);
        final Spinner purpose_spinner = (Spinner) findViewById(R.id.purpose) ;
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        radioGroup = findViewById(R.id.case_type);
        radioButton_civil = findViewById(R.id.civil);
        radioButton_criminal = findViewById(R.id.criminal);
        int s = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(s);

        radioButton_civil.setOnClickListener(this);
        radioButton_criminal.setOnClickListener(this);

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

        purpose_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String case_purpose = purpose_spinner.getSelectedItem().toString();
                purposes = case_purpose;
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
        if(view==radioButton_civil){
            String[] civil_purpose = {"Purpose of NDH","Filing","Appearance","WS","Arguments On Stay Application","Framing Of Issues","PWs","DWs","Rebuttal & Arguments","Final Order/Judgement"};
            Spinner purpose_spinner = (Spinner) findViewById(R.id.purpose);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout, civil_purpose);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                purpose_spinner.setAdapter(arrayAdapter);

            }
        if(view==radioButton_criminal){
            String[] criminal_purpose = {"Purpose of NDH","Awaiting Challan - Police Custody","Awaiting Challan - Judicial Custody","Awaiting Challan - On Bail","Arguments on framing of charge","PWs.","Statement of Accused under section 313 Cr.P.C.","DWs AND Arguments","Final Order/Judgement","Miscellaneous Application","Decided"};
            Spinner purpose_spinner = (Spinner) findViewById(R.id.purpose);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout, criminal_purpose);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            purpose_spinner.setAdapter(arrayAdapter);
        }
        if(view == doA){
            int mYear, mMonth, mDay, mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            doA.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if(view==ndh){
            int mYear, mMonth, mDay, mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ndh.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        }

public void addHearing() {
        int s = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(s);

        try {
            String case_type = radioButton.getText().toString();
            String id = case_id.getText().toString();
            String doa = doA.getText().toString();
            String title = case_title.getText().toString();
            final String get_ndh = ndh.getText().toString();


            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
            String last_updated = dateFormat.format(date);

    if(!(radioButton.isChecked()))
    {
        radioButton.setError("Case Type is required");
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

            progressDialog.setMessage("Saving Info...");
            progressDialog.show();

            DB_CaseHearing db_caseHearing = new DB_CaseHearing(case_type, id,doa,advocates, title, get_ndh, purposes, last_updated);

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
