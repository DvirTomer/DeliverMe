package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText AllName, Password, Mail;
    TextView loginbutton;
    Button regButton;
    FirebaseAuth mhuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AllName = findViewById(R.id.editTextTextPersonName);
        Mail = findViewById(R.id.editTextTextEmailAddress);
        loginbutton = findViewById(R.id.textView2);
        regButton = findViewById(R.id.button);
        Password = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);
        mhuth = FirebaseAuth.getInstance();
        if(mhuth.getCurrentUser()!=null)
        {
//            Intent open  = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Mail.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Mail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("password is Required");
                    return;
                }
                if (password.length() < 6) {
                    Password.setError("Your password must be greater than 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // REGISTER THE USER DATA
                mhuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                       if (task.isSuccessful())
                    {
                        Toast.makeText(Register.this, "User created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                       else {
                           Toast.makeText(Register.this, "ERROR!!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.GONE);
                       }}
                    });

                }


            });
        loginbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));


            }

        });
        }


    }
