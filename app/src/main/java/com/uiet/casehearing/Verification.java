package com.uiet.casehearing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Verification extends AppCompatActivity implements View.OnClickListener {

    TextView verify,name;
    Button proceed;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        name = findViewById(R.id.name);
        verify = findViewById(R.id.verify);
        verify.setOnClickListener(this);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        checkVerification();

    }

    private void checkVerification() {

         if(mAuth.getCurrentUser()==null){
             finish();
             Intent i = new Intent(getApplicationContext(),Login.class);
             startActivity(i);
         }
        if(mAuth.getCurrentUser().isEmailVerified())
        {   proceed.setText("Proceed");
            proceed.setEnabled(true);
            String verify_info = "<u>Email Verified</u>";
            verify.setText(Html.fromHtml(verify_info));

        }else
        {
            String verify_info = "<u>Email Not Verified. (Click to Verify)</u>";
            verify.setText(Html.fromHtml(verify_info));
            proceed.setText("Proceed");
            proceed.setEnabled(true);
        }
    }


    @Override
    public void onClick(View view) {

        if(view==verify)
        {   progressDialog.setMessage("Sending Verification Mail...");
            progressDialog.show();
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {  progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Verification mail sent.",Toast.LENGTH_LONG).show();
                        return;
                    }else
                    {   progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            });
        }
        if(view == proceed)
        {
               finish();
               Intent i = new Intent(getApplicationContext(),MainActivity.class);
               startActivity(i);
        }
    }

    protected void onStart() {
        checkVerification();
        super.onStart();
    }

}
