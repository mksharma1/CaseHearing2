package com.example.casehearing;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.MonthDisplayHelper;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    RadioGroup searchOption;
    RadioButton radioButtonCaseId,radioButtonAdvocateName;
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
        searchOption = findViewById(R.id.searchOption);
        radioButtonCaseId = findViewById(R.id.radioButtonCaseId);
        radioButtonAdvocateName = findViewById(R.id.radioButtonAdvocateName);
        loadHearings();

        radioButtonCaseId.setChecked(true);


        radioButtonCaseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text.setHint("Search Case Id");
            }
        });

        radioButtonAdvocateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text.setHint("Search Advocate Name");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Searching...");
                progressDialog.show();
                final String search_result = search_text.getText().toString();

                int s = searchOption.getCheckedRadioButtonId();

                if(s==radioButtonCaseId.getId()){
                    CaseHearing.whereEqualTo("case_id" ,search_result).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            progressDialog.cancel();

                            LinearLayout LL = (LinearLayout) findViewById(R.id.LL);
                            TableLayout tt = (TableLayout) findViewById(R.id.tt);

                            tt.removeAllViews();

                            TextView serialHeading = new TextView(getApplicationContext());
                            TextView DoA= new TextView(getApplicationContext());
                            TextView caseTypeHeading = new TextView(getApplicationContext());
                            TextView caseIdHeading = new TextView(getApplicationContext());
                            TextView advocateNameHeading = new TextView(getApplicationContext());
                            TextView caseTitleHeading = new TextView(getApplicationContext());
                            TextView NdhHeading = new TextView(getApplicationContext());
                            TextView PurposeHeading = new TextView(getApplicationContext());
                            TextView lastUpdatedHeading = new TextView(getApplicationContext());

                            serialHeading.setText("S.No");
                            serialHeading.setTypeface(null, Typeface.BOLD);
                            serialHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            serialHeading.setTextColor(Color.WHITE);
                            serialHeading.setTextSize(20);
                            serialHeading.setPadding(8,2,8,2);

                            DoA.setText("Date of Assignment by DLSA");
                            DoA.setTypeface(null, Typeface.BOLD);
                            DoA.setBackgroundResource(R.drawable.table_heading_shape);
                            DoA.setTextColor(Color.WHITE);
                            DoA.setTextSize(20);
                            DoA.setPadding(8,2,8,2);

                            caseTypeHeading.setText("Case Type");
                            caseTypeHeading.setTypeface(null, Typeface.BOLD);
                            caseTypeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseTypeHeading.setTextColor(Color.WHITE);
                            caseTypeHeading.setTextSize(20);
                            caseTypeHeading.setPadding(8,2,8,2);
                            caseIdHeading.setText("Case Id");
                            caseIdHeading.setTextColor(Color.WHITE);
                            caseIdHeading.setTypeface(null, Typeface.BOLD);
                            caseIdHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseIdHeading.setTextSize(20);
                            caseIdHeading.setPadding(8,2,8,2);
                            advocateNameHeading.setText("Advocate Name");
                            advocateNameHeading.setTypeface(null, Typeface.BOLD);
                            advocateNameHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            advocateNameHeading.setTextSize(20);
                            advocateNameHeading.setPadding(8,2,8,2);
                            advocateNameHeading.setTextColor(Color.WHITE);
                            caseTitleHeading.setText("Case Title");
                            caseTitleHeading.setTypeface(null, Typeface.BOLD);
                            caseTitleHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseTitleHeading.setTextSize(20);
                            caseTitleHeading.setPadding(8,2,8,2);
                            caseTitleHeading.setTextColor(Color.WHITE);
                            NdhHeading.setText("NDH");
                            NdhHeading.setTypeface(null, Typeface.BOLD);
                            NdhHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            NdhHeading.setTextSize(20);
                            NdhHeading.setPadding(8,2,8,2);
                            NdhHeading.setTextColor(Color.WHITE);
                            PurposeHeading.setText("Purpose");
                            PurposeHeading.setTypeface(null, Typeface.BOLD);
                            PurposeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            PurposeHeading.setTextSize(20);
                            PurposeHeading.setPadding(8,2,8,2);
                            PurposeHeading.setTextColor(Color.WHITE);
                            lastUpdatedHeading.setText("Last Updated");
                            lastUpdatedHeading.setTypeface(null, Typeface.BOLD);
                            lastUpdatedHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            lastUpdatedHeading.setTextSize(20);
                            lastUpdatedHeading.setPadding(8,2,8,2);
                            lastUpdatedHeading.setTextColor(Color.WHITE);


                            TableRow Heading = new TableRow(getApplicationContext());
                            TableRow.LayoutParams headingParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                            headingParams.rightMargin = 30;
                            headingParams.setMargins(30,30,30,30);
                            Heading.setPadding(6,6,6,6);
                            Heading.setLayoutParams(headingParams);

                            Heading.addView(serialHeading);
                            Heading.addView(DoA);
                            Heading.addView(caseTypeHeading);
                            Heading.addView(caseIdHeading);
                            Heading.addView(advocateNameHeading);
                            Heading.addView(caseTitleHeading);
                            Heading.addView(NdhHeading);
                            Heading.addView(PurposeHeading);
                            Heading.addView(lastUpdatedHeading);
                            tt.addView(Heading,0);


                            int serial_no = 1;
                            //String hearing = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {

                                DB_CaseHearing db_caseHearing = documentSnapshot.toObject(DB_CaseHearing.class);
                                String no = Integer.toString(serial_no);
                                String case_type = db_caseHearing.getCase_type();
                                String case_id = db_caseHearing.getCase_id();
                                String advocate_name = db_caseHearing.getAdvocate_name();
                                String  case_title = db_caseHearing.getCase_title();
                                String ndh = db_caseHearing.getNdh();
                                String purpose = db_caseHearing.getPurpose();
                                String last_updated = db_caseHearing.getLast_updated();

                                TextView serial = new TextView(getApplicationContext());
                                TextView caseType = new TextView(getApplicationContext());
                                TextView caseId = new TextView(getApplicationContext());
                                TextView advocateName = new TextView(getApplicationContext());
                                TextView caseTitle = new TextView(getApplicationContext());
                                TextView Ndh = new TextView(getApplicationContext());
                                TextView Purpose = new TextView(getApplicationContext());
                                TextView lastUpdated = new TextView(getApplicationContext());

                                serial.setText(no);
                                serial.setBackgroundResource(R.drawable.table_shape);
                                serial.setGravity(Gravity.CENTER);
                                caseType.setText(case_type);
                                caseType.setBackgroundResource(R.drawable.table_shape);
                                caseType.setGravity(Gravity.CENTER);
                                caseId.setText(case_id);
                                caseId.setBackgroundResource(R.drawable.table_shape);
                                caseId.setGravity(Gravity.CENTER);
                                advocateName.setText(advocate_name);
                                advocateName.setBackgroundResource(R.drawable.table_shape);
                                advocateName.setGravity(Gravity.CENTER);
                                caseTitle.setText(case_title);
                                caseTitle.setBackgroundResource(R.drawable.table_shape);
                                caseTitle.setGravity(Gravity.CENTER);
                                Ndh.setText(ndh);
                                Ndh.setBackgroundResource(R.drawable.table_shape);
                                Ndh.setGravity(Gravity.CENTER);
                                Purpose.setText(purpose);
                                Purpose.setBackgroundResource(R.drawable.table_shape);
                                Purpose.setGravity(Gravity.CENTER);
                                lastUpdated.setText(last_updated);
                                lastUpdated.setBackgroundResource(R.drawable.table_shape);
                                lastUpdated.setGravity(Gravity.CENTER);

                                TableRow row = new TableRow(getApplicationContext());
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                lp.rightMargin = 10;
                                lp.setMargins(10,10,10,10);
                                row.setPadding(6,6,6,6);
                                row.setLayoutParams(lp);

                                row.addView(serial);
                                row.addView(caseType);
                                row.addView(caseId);
                                row.addView(advocateName);
                                row.addView(caseTitle);
                                row.addView(Ndh);
                                row.addView(Purpose);
                                row.addView(lastUpdated);
                                tt.addView(row,serial_no);

                                //  hearing += serial_no+ ".Case Type : " +case_type + "\n   Case ID : " +case_id +  "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
                                serial_no++;
                            }
                            //  hearings.setText(hearing);
                            search_text.setText(search_result);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    CaseHearing.whereEqualTo("advocate_name" ,search_result).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            progressDialog.cancel();

                            LinearLayout LL = (LinearLayout) findViewById(R.id.LL);
                            TableLayout tt = (TableLayout) findViewById(R.id.tt);

                            tt.removeAllViews();

                            TextView serialHeading = new TextView(getApplicationContext());
                            TextView caseTypeHeading = new TextView(getApplicationContext());
                            TextView caseIdHeading = new TextView(getApplicationContext());
                            TextView advocateNameHeading = new TextView(getApplicationContext());
                            TextView caseTitleHeading = new TextView(getApplicationContext());
                            TextView NdhHeading = new TextView(getApplicationContext());
                            TextView PurposeHeading = new TextView(getApplicationContext());
                            TextView lastUpdatedHeading = new TextView(getApplicationContext());

                            serialHeading.setText("S.No");
                            serialHeading.setTypeface(null, Typeface.BOLD);
                            serialHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            serialHeading.setTextColor(Color.WHITE);
                            serialHeading.setTextSize(20);
                            serialHeading.setPadding(8,2,8,2);
                            caseTypeHeading.setText("Case Type");
                            caseTypeHeading.setTypeface(null, Typeface.BOLD);
                            caseTypeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseTypeHeading.setTextColor(Color.WHITE);
                            caseTypeHeading.setTextSize(20);
                            caseTypeHeading.setPadding(8,2,8,2);
                            caseIdHeading.setText("Case Id");
                            caseIdHeading.setTextColor(Color.WHITE);
                            caseIdHeading.setTypeface(null, Typeface.BOLD);
                            caseIdHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseIdHeading.setTextSize(20);
                            caseIdHeading.setPadding(8,2,8,2);
                            advocateNameHeading.setText("Advocate Name");
                            advocateNameHeading.setTypeface(null, Typeface.BOLD);
                            advocateNameHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            advocateNameHeading.setTextSize(20);
                            advocateNameHeading.setPadding(8,2,8,2);
                            advocateNameHeading.setTextColor(Color.WHITE);
                            caseTitleHeading.setText("Case Title");
                            caseTitleHeading.setTypeface(null, Typeface.BOLD);
                            caseTitleHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            caseTitleHeading.setTextSize(20);
                            caseTitleHeading.setPadding(8,2,8,2);
                            caseTitleHeading.setTextColor(Color.WHITE);
                            NdhHeading.setText("NDH");
                            NdhHeading.setTypeface(null, Typeface.BOLD);
                            NdhHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            NdhHeading.setTextSize(20);
                            NdhHeading.setPadding(8,2,8,2);
                            NdhHeading.setTextColor(Color.WHITE);
                            PurposeHeading.setText("Purpose");
                            PurposeHeading.setTypeface(null, Typeface.BOLD);
                            PurposeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            PurposeHeading.setTextSize(20);
                            PurposeHeading.setPadding(8,2,8,2);
                            PurposeHeading.setTextColor(Color.WHITE);
                            lastUpdatedHeading.setText("Last Updated");
                            lastUpdatedHeading.setTypeface(null, Typeface.BOLD);
                            lastUpdatedHeading.setBackgroundResource(R.drawable.table_heading_shape);
                            lastUpdatedHeading.setTextSize(20);
                            lastUpdatedHeading.setPadding(8,2,8,2);
                            lastUpdatedHeading.setTextColor(Color.WHITE);


                            TableRow Heading = new TableRow(getApplicationContext());
                            TableRow.LayoutParams headingParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                            headingParams.rightMargin = 30;
                            headingParams.setMargins(30,30,30,30);
                            Heading.setPadding(6,6,6,6);
                            Heading.setLayoutParams(headingParams);

                            Heading.addView(serialHeading);
                            Heading.addView(caseTypeHeading);
                            Heading.addView(caseIdHeading);
                            Heading.addView(advocateNameHeading);
                            Heading.addView(caseTitleHeading);
                            Heading.addView(NdhHeading);
                            Heading.addView(PurposeHeading);
                            Heading.addView(lastUpdatedHeading);
                            tt.addView(Heading,0);


                            int serial_no = 1;
                            //String hearing = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {

                                DB_CaseHearing db_caseHearing = documentSnapshot.toObject(DB_CaseHearing.class);
                                String no = Integer.toString(serial_no);
                                String case_type = db_caseHearing.getCase_type();
                                String case_id = db_caseHearing.getCase_id();
                                String advocate_name = db_caseHearing.getAdvocate_name();
                                String  case_title = db_caseHearing.getCase_title();
                                String ndh = db_caseHearing.getNdh();
                                String purpose = db_caseHearing.getPurpose();
                                String last_updated = db_caseHearing.getLast_updated();

                                TextView serial = new TextView(getApplicationContext());
                                TextView caseType = new TextView(getApplicationContext());
                                TextView caseId = new TextView(getApplicationContext());
                                TextView advocateName = new TextView(getApplicationContext());
                                TextView caseTitle = new TextView(getApplicationContext());
                                TextView Ndh = new TextView(getApplicationContext());
                                TextView Purpose = new TextView(getApplicationContext());
                                TextView lastUpdated = new TextView(getApplicationContext());

                                serial.setText(no);
                                serial.setBackgroundResource(R.drawable.table_shape);
                                serial.setGravity(Gravity.CENTER);
                                caseType.setText(case_type);
                                caseType.setBackgroundResource(R.drawable.table_shape);
                                caseType.setGravity(Gravity.CENTER);
                                caseId.setText(case_id);
                                caseId.setBackgroundResource(R.drawable.table_shape);
                                caseId.setGravity(Gravity.CENTER);
                                advocateName.setText(advocate_name);
                                advocateName.setBackgroundResource(R.drawable.table_shape);
                                advocateName.setGravity(Gravity.CENTER);
                                caseTitle.setText(case_title);
                                caseTitle.setBackgroundResource(R.drawable.table_shape);
                                caseTitle.setGravity(Gravity.CENTER);
                                Ndh.setText(ndh);
                                Ndh.setBackgroundResource(R.drawable.table_shape);
                                Ndh.setGravity(Gravity.CENTER);
                                Purpose.setText(purpose);
                                Purpose.setBackgroundResource(R.drawable.table_shape);
                                Purpose.setGravity(Gravity.CENTER);
                                lastUpdated.setText(last_updated);
                                lastUpdated.setBackgroundResource(R.drawable.table_shape);
                                lastUpdated.setGravity(Gravity.CENTER);

                                TableRow row = new TableRow(getApplicationContext());
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                lp.rightMargin = 10;
                                lp.setMargins(10,10,10,10);
                                row.setPadding(6,6,6,6);
                                row.setLayoutParams(lp);

                                row.addView(serial);
                                row.addView(caseType);
                                row.addView(caseId);
                                row.addView(advocateName);
                                row.addView(caseTitle);
                                row.addView(Ndh);
                                row.addView(Purpose);
                                row.addView(lastUpdated);
                                tt.addView(row,serial_no);

                                //  hearing += serial_no+ ".Case Type : " +case_type + "\n   Case ID : " +case_id +  "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
                                serial_no++;
                            }
                            //  hearings.setText(hearing);
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

                //String hearing = "";

                TextView serialHeading = new TextView(getApplicationContext());
                TextView caseTypeHeading = new TextView(getApplicationContext());
                TextView caseIdHeading = new TextView(getApplicationContext());
                TextView advocateNameHeading = new TextView(getApplicationContext());
                TextView caseTitleHeading = new TextView(getApplicationContext());
                TextView NdhHeading = new TextView(getApplicationContext());
                TextView PurposeHeading = new TextView(getApplicationContext());
                TextView lastUpdatedHeading = new TextView(getApplicationContext());

                serialHeading.setText("S.No");
                serialHeading.setTypeface(null, Typeface.BOLD);
                serialHeading.setBackgroundResource(R.drawable.table_heading_shape);
                serialHeading.setTextColor(Color.WHITE);
                serialHeading.setTextSize(20);
                serialHeading.setPadding(8,2,8,2);
                caseTypeHeading.setText("Case Type");
                caseTypeHeading.setTypeface(null, Typeface.BOLD);
                caseTypeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                caseTypeHeading.setTextColor(Color.WHITE);
                caseTypeHeading.setTextSize(20);
                caseTypeHeading.setPadding(8,2,8,2);
                caseIdHeading.setText("Case Id");
                caseIdHeading.setTextColor(Color.WHITE);
                caseIdHeading.setTypeface(null, Typeface.BOLD);
                caseIdHeading.setBackgroundResource(R.drawable.table_heading_shape);
                caseIdHeading.setTextSize(20);
                caseIdHeading.setPadding(8,2,8,2);
                advocateNameHeading.setText("Advocate Name");
                advocateNameHeading.setTypeface(null, Typeface.BOLD);
                advocateNameHeading.setBackgroundResource(R.drawable.table_heading_shape);
                advocateNameHeading.setTextSize(20);
                advocateNameHeading.setPadding(8,2,8,2);
                advocateNameHeading.setTextColor(Color.WHITE);
                caseTitleHeading.setText("Case Title");
                caseTitleHeading.setTypeface(null, Typeface.BOLD);
                caseTitleHeading.setBackgroundResource(R.drawable.table_heading_shape);
                caseTitleHeading.setTextSize(20);
                caseTitleHeading.setPadding(8,2,8,2);
                caseTitleHeading.setTextColor(Color.WHITE);
                NdhHeading.setText("NDH");
                NdhHeading.setTypeface(null, Typeface.BOLD);
                NdhHeading.setBackgroundResource(R.drawable.table_heading_shape);
                NdhHeading.setTextSize(20);
                NdhHeading.setPadding(8,2,8,2);
                NdhHeading.setTextColor(Color.WHITE);
                PurposeHeading.setText("Purpose");
                PurposeHeading.setTypeface(null, Typeface.BOLD);
                PurposeHeading.setBackgroundResource(R.drawable.table_heading_shape);
                PurposeHeading.setTextSize(20);
                PurposeHeading.setPadding(8,2,8,2);
                PurposeHeading.setTextColor(Color.WHITE);
                lastUpdatedHeading.setText("Last Updated");
                lastUpdatedHeading.setTypeface(null, Typeface.BOLD);
                lastUpdatedHeading.setBackgroundResource(R.drawable.table_heading_shape);
                lastUpdatedHeading.setTextSize(20);
                lastUpdatedHeading.setPadding(8,2,8,2);
                lastUpdatedHeading.setTextColor(Color.WHITE);

                TableLayout tt = (TableLayout) findViewById(R.id.tt);

                TableRow Heading = new TableRow(getApplicationContext());
                TableRow.LayoutParams headingParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                headingParams.rightMargin = 30;
                headingParams.setMargins(30,30,30,30);
                Heading.setPadding(6,6,6,6);
                Heading.setLayoutParams(headingParams);

                Heading.addView(serialHeading);
                Heading.addView(caseTypeHeading);
                Heading.addView(caseIdHeading);
                Heading.addView(advocateNameHeading);
                Heading.addView(caseTitleHeading);
                Heading.addView(NdhHeading);
                Heading.addView(PurposeHeading);
                Heading.addView(lastUpdatedHeading);
                tt.addView(Heading,0);

                int serial_no = 1;

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {

                    DB_CaseHearing db_caseHearing = documentSnapshot.toObject(DB_CaseHearing.class);
                    String no = Integer.toString(serial_no);
                    String case_type = db_caseHearing.getCase_type();
                    String case_id = db_caseHearing.getCase_id();
                    String advocate_name = db_caseHearing.getAdvocate_name();
                    String  case_title = db_caseHearing.getCase_title();
                    String ndh = db_caseHearing.getNdh();
                    String purpose = db_caseHearing.getPurpose();
                    String last_updated = db_caseHearing.getLast_updated();
                    //TextView serialNo = new TextView(getApplicationContext());

                    TextView serial = new TextView(getApplicationContext());
                    TextView caseType = new TextView(getApplicationContext());
                    TextView caseId = new TextView(getApplicationContext());
                    TextView advocateName = new TextView(getApplicationContext());
                    TextView caseTitle = new TextView(getApplicationContext());
                    TextView Ndh = new TextView(getApplicationContext());
                    TextView Purpose = new TextView(getApplicationContext());
                    TextView lastUpdated = new TextView(getApplicationContext());

                    serial.setText(no);
                    serial.setBackgroundResource(R.drawable.table_shape);
                    serial.setGravity(Gravity.CENTER);
                    caseType.setText(case_type);
                    caseType.setBackgroundResource(R.drawable.table_shape);
                    caseType.setGravity(Gravity.CENTER);
                    caseId.setText(case_id);
                    caseId.setBackgroundResource(R.drawable.table_shape);
                    caseId.setGravity(Gravity.CENTER);
                    advocateName.setText(advocate_name);
                    advocateName.setBackgroundResource(R.drawable.table_shape);
                    advocateName.setGravity(Gravity.CENTER);
                    caseTitle.setText(case_title);
                    caseTitle.setBackgroundResource(R.drawable.table_shape);
                    caseTitle.setGravity(Gravity.CENTER);
                    Ndh.setText(ndh);
                    Ndh.setBackgroundResource(R.drawable.table_shape);
                    Ndh.setGravity(Gravity.CENTER);
                    Purpose.setText(purpose);
                    Purpose.setBackgroundResource(R.drawable.table_shape);
                    Purpose.setGravity(Gravity.CENTER);
                    lastUpdated.setText(last_updated);
                    lastUpdated.setBackgroundResource(R.drawable.table_shape);
                    lastUpdated.setGravity(Gravity.CENTER);

                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                    lp.rightMargin = 10;
                    lp.setMargins(10,10,10,10);
                    row.setPadding(6,6,6,6);
                    row.setLayoutParams(lp);

                    row.addView(serial);
                    row.addView(caseType);
                    row.addView(caseId);
                    row.addView(advocateName);
                    row.addView(caseTitle);
                    row.addView(Ndh);
                    row.addView(Purpose);
                    row.addView(lastUpdated);
                    tt.addView(row,serial_no);

                 //   hearing += serial_no+ ".Case Type : " +case_type + "\n   Case ID : " +case_id +  "\n   Advocate Name : " +advocate_name +"\n   Case Title : " +case_title + "\n   NDH : " +ndh + "\n   Purpose : " +purpose +"\n   Last Updated : " +last_updated + "\n\n";
                    serial_no++;
                }
               // hearings.setText(hearing);
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
