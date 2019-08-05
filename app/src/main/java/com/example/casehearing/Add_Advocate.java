package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Add_Advocate extends AppCompatActivity implements View.OnClickListener{

    EditText advocate;
    Button add;
    ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Advocates = db.collection("Advocates");
    private DocumentReference Names = db.document("Advocates/first_name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advocate);

        advocate = findViewById(R.id.advocate);
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
                   Advocates.add(db_advocates.getAdvocate());

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
    }

    public void addAdvocate() {
        String advocate_name = advocate.getText().toString();
        if(advocate_name.isEmpty())
        {
            advocate.setError("Advocate Name is required");
            advocate.requestFocus();
            return;
        }

        DB_Advocates db_advocates = new DB_Advocates(advocate_name);
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
}
