package com.uiet.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class Add_Advocate extends AppCompatActivity implements View.OnClickListener{

    EditText advocate,startDate,endDate;
    Button add;
    ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Advocates = db.collection("Advocates");
    private DocumentReference Names = db.document("Advocates/first_name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advocate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Advocates");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        advocate = findViewById(R.id.advocate);
        startDate = findViewById(R.id.startDate);
        startDate.setOnClickListener(this);
        endDate = findViewById(R.id.endDate);
        endDate.setOnClickListener(this);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

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
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    DB_Advocates db_advocates = documentSnapshot.toObject(DB_Advocates.class);
                    String advocate = db_advocates.getAdvocate() + " (" +db_advocates.getStartDate() + " - " +db_advocates.getEndDate() + ")";
                   Advocates.add(advocate);
                   advocate = "";
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),R.layout.spinner_layout, Advocates);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View view) {
      if(view==add){
          addAdvocate();
      }
      if(view == startDate){
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

                          startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                      }
                  }, mYear, mMonth, mDay);
          datePickerDialog.show();
      }
      if(view == endDate){
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

                          endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                      }
                  }, mYear, mMonth, mDay);
          datePickerDialog.show();
      }
    }

    public void addAdvocate() {
        String advocate_name = advocate.getText().toString().toLowerCase() ;
        if(advocate_name.isEmpty())
        {
            advocate.setError("Advocate Name is required");
            advocate.requestFocus();
            return;
        }
        String startingDate = startDate.getText().toString();
        if(startingDate.isEmpty()){
            startDate.setError("Start Date is required");
            startDate.requestFocus();
            return;
        }
        String endingDate = endDate.getText().toString();
        if(endingDate.isEmpty()){
            endDate.setError("End Date is required");
            endDate.requestFocus();
            return;
        }

        DB_Advocates db_advocates = new DB_Advocates(advocate_name,startingDate,endingDate);
        progressDialog.setMessage("Adding Name...");
        progressDialog.show();
        Advocates.add(db_advocates).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),"Name Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
