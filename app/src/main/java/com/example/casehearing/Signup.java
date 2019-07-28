package com.example.casehearing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity implements View.OnClickListener{

    Button signup;
    TextView loginText;
    EditText name,mail,pass;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = findViewById(R.id.signupButton);
        loginText = findViewById(R.id.loginText);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(this);
        loginText.setOnClickListener(this);

    }

    private void registerUser(){

        final String username = name.getText().toString().trim();
        final String userid = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if(username.isEmpty())
        {
            name.setError("Name is Required");
            name.requestFocus();
            return;
        }
        if(userid.isEmpty())
        {
            mail.setError("Email is Required");
            mail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userid).matches())
        {
            mail.setError("Please enter a valid email.");
            mail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            pass.setError("Password is Required");
            pass.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            pass.setError("Minimum length of password should be 6.");
            pass.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(userid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   finish();
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("name",username);
                    i.putExtra("email",userid);
                    startActivity(i);
                }else
                {   progressDialog.cancel();
                   if(task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_LONG).show();
                   }else {
                       Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                   }

                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        if(view == signup)
        {
           registerUser();
        }

        if(view == loginText)
        {   finish();
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        }

    }
}
