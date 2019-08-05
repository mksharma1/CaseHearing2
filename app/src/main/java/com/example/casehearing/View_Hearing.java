package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class View_Hearing extends AppCompatActivity {

    TextView hearings;
    ImageView search;
    EditText search_text;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = CaseHearing.document("first_hearing");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hearing);

        hearings = findViewById(R.id.hearings);
        progressDialog = new ProgressDialog(this);
        search = findViewById(R.id.search);
        search_text = findViewById(R.id.search_text);
        loadHearings();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Searching...");
                progressDialog.show();
                final String search_result = search_text.getText().toString();
                CaseHearing.whereEqualTo("case_id",search_result).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressDialog.cancel();
                        int serial_no = 1;
                        String hearing = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            DB_CaseHearing db_caseHearing = documentSnapshot.toObject(DB_CaseHearing.class);
                            String case_type = db_caseHearing.getCase_type();
                            String case_id = db_caseHearing.getCase_id();
                            String advocate_name = db_caseHearing.getAdvocate_name();
                            String  case_title = db_caseHearing.getCase_title();
                            String ndh = db_caseHearing.getNdh();
                            String purpose = db_caseHearing.getPurpose();
                            String last_updated = db_caseHearing.getLast_updated();

                            hearing += serial_no+ ".Case Type : " +case_type + "\n   Case ID : " +case_id +  "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
                            serial_no++;
                        }
                        hearings.setText(hearing);
                        search_text.setText(search_result);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_case_hearings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.download:
                /*try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                return true;
        }*/}

                return true;

    }
   /* private void createPdf() throws FileNotFoundException, DocumentException {

        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();

        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        File myFile = new File(pdfFolder + timeStamp + ".pdf");

        OutputStream output = new FileOutputStream(myFile);

        //Step 1
        Document document = new Document();

        //Step 2
        try {
            PdfWriter.getInstance(document, output);
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }

        //Step 3
        document.open();

        //Step 4 Add content
        try {
            document.add(new Paragraph(hearings.getText().toString()));
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }

        //Step 5: Close the document
        document.close();

    }*/



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
                    String case_id = db_caseHearing.getCase_id();
                    String advocate_name = db_caseHearing.getAdvocate_name();
                    String  case_title = db_caseHearing.getCase_title();
                    String ndh = db_caseHearing.getNdh();
                    String purpose = db_caseHearing.getPurpose();
                    String last_updated = db_caseHearing.getLast_updated();

                    hearing += serial_no+ ".Case Type : " +case_type + "\n   Case ID : " +case_id +  "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
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
