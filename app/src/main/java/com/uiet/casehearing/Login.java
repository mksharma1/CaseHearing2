package com.uiet.casehearing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText mail,password;
    Button login,skipLogin;
    TextView signupText,forgetPassword;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.email_id);
        password = findViewById(R.id.pwd);
        login = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
        forgetPassword = findViewById(R.id.forget_password);
        progressDialog = new ProgressDialog(this);
        skipLogin = findViewById(R.id.skipLogin);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);
        signupText.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        skipLogin.setOnClickListener(this);

        if(mAuth.getCurrentUser()!=null)
        {
            finish();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }

    }

    private void userLogin()
    {
        String email = mail.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(email.isEmpty())
        {
            mail.setError("Email is required...");
            mail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mail.setError("Please enter a valid email.");
            mail.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Password is required...");
            password.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Minimum length of password should be 6.");
            password.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   finish();
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else
                {
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

        if(view == login)
        {
           userLogin();
        }

        if (view == signupText)
        {   finish();
            Intent i = new Intent(getApplicationContext(),Signup.class);
            startActivity(i);
        }

        if (view == forgetPassword){
          Intent i = new Intent(getApplicationContext(),ResetPassword.class);
          startActivity(i);
        }

        if(view == skipLogin){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }
}
