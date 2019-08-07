package com.example.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class View_Hearing extends AppCompatActivity {

    TextView hearings;
    ImageView search;
    EditText search_text;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference CaseHearing = db.collection("CaseHearings");
    private DocumentReference hearing = CaseHearing.document("first_hearing");
    ProgressDialog progressDialog;
    public File file;

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
                try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"File Not Found Exception",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (DocumentException e) {
                    Toast.makeText(getApplicationContext(),"Document Exception",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return true;
        }

                return true;

    }
    private void createPdf() throws FileNotFoundException, DocumentException {

        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        String path = getExternalFilesDir(null).toString()+"/CaseHearings"+timeStamp+".pdf";
        file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        document.open();
        document.add(new Paragraph(hearings.getText().toString()));
        Toast.makeText(getApplicationContext(),"PDF Downloaded",Toast.LENGTH_LONG).show();
        document.close();
        //priviewPdf();
    }

     /*private void priviewPdf(){
        Uri uri = Uri.fromFile(file);
        Intent pdfIntent  = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(uri,"application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(pdfIntent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"No Application available to view PDF",Toast.LENGTH_LONG).show();
        }
     }
*/
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
