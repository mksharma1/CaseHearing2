package com.example.casehearing;

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



    TextView verify,mail_info,name;
    Button proceed,refresh;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        name = findViewById(R.id.name);
        verify = findViewById(R.id.verify);
        verify.setOnClickListener(this);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        mail_info = findViewById(R.id.mail_info);
        Intent i = getIntent();
        String username = i.getStringExtra("name");
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        if(username!=null) {
            name.setText(" " + username);
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
                    }else
                    {   progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
        if(view == refresh)
        {
            Intent i = new Intent(getApplicationContext(), Verification.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }

    }


    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null)
        {
            finish();
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        }
        if(mAuth.getCurrentUser().isEmailVerified())
        {   proceed.setText("Proceed");
            proceed.setEnabled(true);
            String verify_info = "<u>Email Verified</u>";
            verify.setText(Html.fromHtml(verify_info));
            mail_info.setVisibility(View.GONE);

        }else if(!mAuth.getCurrentUser().isEmailVerified())
        {   Intent i = getIntent();
            String id = i.getStringExtra("email");
            String verify_info = "<u>Email Not Verified. (Click to Verify)</u>";
            if(id!=null)
            {
                mail_info.setText("Note : Verification mail would be sent to your mail id - " + id);
            }else {
                mail_info.setText("Note : Verification mail would be sent to your mail id.");
            }
            proceed.setText("Verify Email to Proceed");
            proceed.setEnabled(false);
            verify.setText(Html.fromHtml(verify_info));
        }
    }


}
