package com.uiet.casehearing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RemoveAdvocate extends AppCompatActivity implements View.OnClickListener {

    EditText searchAdvocate,removeAdvocate;
    ImageView searchButton;
    Button removeButton;
    ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Advocates = db.collection("Advocates");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_advocate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Remove Advocates");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        searchAdvocate = findViewById(R.id.searchAdvocate);
        removeAdvocate = findViewById(R.id.removeAdvocate);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        loadAdvocates();
    }

    private void loadAdvocates() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Advocates.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.cancel();

                TextView serialHeading = new TextView(getApplicationContext());
                TextView nameHeading = new TextView(getApplicationContext());
                TextView periodHeading = new TextView(getApplicationContext());

                serialHeading.setText(" S.No ");
                serialHeading.setTypeface(null, Typeface.BOLD);
                serialHeading.setBackgroundResource(R.drawable.table_heading_shape);
                serialHeading.setTextColor(Color.WHITE);
                serialHeading.setTextSize(20);
                serialHeading.setPadding(8,2,8,2);

                nameHeading.setText(" Advocate Name ");
                nameHeading.setTypeface(null, Typeface.BOLD);
                nameHeading.setBackgroundResource(R.drawable.table_heading_shape);
                nameHeading.setTextColor(Color.WHITE);
                nameHeading.setTextSize(20);
                nameHeading.setPadding(8,2,8,2);

                periodHeading.setText(" Period Of Employment ");
                periodHeading.setTypeface(null, Typeface.BOLD);
                periodHeading.setBackgroundResource(R.drawable.table_heading_shape);
                periodHeading.setTextColor(Color.WHITE);
                periodHeading.setTextSize(20);
                periodHeading.setPadding(8,2,8,2);


                TableLayout tt = (TableLayout) findViewById(R.id.tt);

                TableRow Heading = new TableRow(getApplicationContext());
                TableRow.LayoutParams headingParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                headingParams.rightMargin = 30;
                headingParams.setMargins(30,30,30,30);
                Heading.setPadding(6,6,6,6);
                Heading.setLayoutParams(headingParams);

                Heading.addView(serialHeading);
                Heading.addView(nameHeading);
                Heading.addView(periodHeading);
                tt.addView(Heading,0);

                int serial_no = 1;

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    DB_Advocates db_advocates = documentSnapshot.toObject(DB_Advocates.class);

                    String no =" " + Integer.toString(serial_no) +" ";
                    String name = " " + db_advocates.getAdvocate() +" ";
                    String startDate = db_advocates.getStartDate();
                    String endDate = db_advocates.getEndDate();
                    String period =" " + startDate + " to " +endDate + " ";

                    TextView serialNo = new TextView(getApplicationContext());
                    TextView advocateName = new TextView(getApplicationContext());
                    TextView employmentPeriod = new TextView(getApplicationContext());

                    serialNo.setText(no);
                    serialNo.setBackgroundResource(R.drawable.table_shape);
                    serialNo.setGravity(Gravity.CENTER);

                    advocateName.setText(name);
                    advocateName.setBackgroundResource(R.drawable.table_shape);
                    advocateName.setGravity(Gravity.CENTER);

                    employmentPeriod.setText(period);
                    employmentPeriod.setBackgroundResource(R.drawable.table_shape);
                    employmentPeriod.setGravity(Gravity.CENTER);


                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                    lp.rightMargin = 10;
                    lp.setMargins(10,10,10,10);
                    row.setPadding(6,6,6,6);
                    row.setLayoutParams(lp);

                    row.addView(serialNo);
                    row.addView(advocateName);
                    row.addView(employmentPeriod);
                    tt.addView(row,serial_no);

                    serial_no++;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == searchButton){
          searchAdvocate();
        }
        if(view == removeButton){
          removeAdvocate();
        }
    }

    private void removeAdvocate() {
        progressDialog.setMessage("Removing...");
        progressDialog.show();

        final String advocateName = removeAdvocate.getText().toString().toLowerCase();

       Advocates.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               progressDialog.cancel();
               int FLAG =0;

               for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                   DB_Advocates db_advocates = documentSnapshot.toObject(DB_Advocates.class);
                   String advocate = db_advocates.getAdvocate();
                   if(advocate.equals(advocateName)){
                       Advocates.document(documentSnapshot.getId()).delete();
                       FLAG = 1;
                   }
               }
               if(FLAG == 1){
                   Toast.makeText(getApplicationContext(),"Advocate removed succesfully...",Toast.LENGTH_LONG).show();
               } else {
                   Toast.makeText(getApplicationContext(),"Advocate does not exists...",Toast.LENGTH_LONG).show();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               progressDialog.cancel();
               Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
           }
       });
    }

    private void searchAdvocate() {
        progressDialog.setMessage("Searching...");
        progressDialog.show();

        String searchResult = searchAdvocate.getText().toString().toLowerCase();

        Advocates.whereEqualTo("advocate",searchResult).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.cancel();

                TableLayout tt = (TableLayout) findViewById(R.id.tt);
                tt.removeAllViews();

                TextView serialHeading = new TextView(getApplicationContext());
                TextView nameHeading = new TextView(getApplicationContext());
                TextView periodHeading = new TextView(getApplicationContext());

                serialHeading.setText(" S.No ");
                serialHeading.setTypeface(null, Typeface.BOLD);
                serialHeading.setBackgroundResource(R.drawable.table_heading_shape);
                serialHeading.setTextColor(Color.WHITE);
                serialHeading.setTextSize(20);
                serialHeading.setPadding(8,2,8,2);

                nameHeading.setText(" Advocate Name ");
                nameHeading.setTypeface(null, Typeface.BOLD);
                nameHeading.setBackgroundResource(R.drawable.table_heading_shape);
                nameHeading.setTextColor(Color.WHITE);
                nameHeading.setTextSize(20);
                nameHeading.setPadding(8,2,8,2);

                periodHeading.setText(" Period Of Employment ");
                periodHeading.setTypeface(null, Typeface.BOLD);
                periodHeading.setBackgroundResource(R.drawable.table_heading_shape);
                periodHeading.setTextColor(Color.WHITE);
                periodHeading.setTextSize(20);
                periodHeading.setPadding(8,2,8,2);

                TableRow Heading = new TableRow(getApplicationContext());
                TableRow.LayoutParams headingParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                headingParams.rightMargin = 30;
                headingParams.setMargins(30,30,30,30);
                Heading.setPadding(6,6,6,6);
                Heading.setLayoutParams(headingParams);

                Heading.addView(serialHeading);
                Heading.addView(nameHeading);
                Heading.addView(periodHeading);
                tt.addView(Heading,0);

                int serial_no = 1;

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    DB_Advocates db_advocates = documentSnapshot.toObject(DB_Advocates.class);

                    String no = " " +Integer.toString(serial_no) +" ";
                    String name =" " + db_advocates.getAdvocate() +" ";
                    String startDate = db_advocates.getStartDate();
                    String endDate = db_advocates.getEndDate();
                    String period =" " + startDate + " to " +endDate +" ";

                    TextView serialNo = new TextView(getApplicationContext());
                    TextView advocateName = new TextView(getApplicationContext());
                    TextView employmentPeriod = new TextView(getApplicationContext());

                    serialNo.setText(no);
                    serialNo.setBackgroundResource(R.drawable.table_shape);
                    serialNo.setGravity(Gravity.CENTER);

                    advocateName.setText(name);
                    advocateName.setBackgroundResource(R.drawable.table_shape);
                    advocateName.setGravity(Gravity.CENTER);

                    employmentPeriod.setText(period);
                    employmentPeriod.setBackgroundResource(R.drawable.table_shape);
                    employmentPeriod.setGravity(Gravity.CENTER);


                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                    lp.rightMargin = 10;
                    lp.setMargins(10,10,10,10);
                    row.setPadding(6,6,6,6);
                    row.setLayoutParams(lp);

                    row.addView(serialNo);
                    row.addView(advocateName);
                    row.addView(employmentPeriod);
                    tt.addView(row,serial_no);

                    serial_no++;
                }
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
