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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText AllName, Password, Mail, Phone;
    TextView loginbutton;
    Button regButton;
    FirebaseAuth mhuth;
    ProgressBar progressBar;
    DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connects the xml to java file
        setContentView(R.layout.activity_register);
        //Buttons id from xml
        AllName = findViewById(R.id.userName_id);
        Mail = findViewById(R.id.mail_id);
        loginbutton = findViewById(R.id.connect_id);
        regButton = findViewById(R.id.Reg_id);
        Password = findViewById(R.id.Password_id);
        progressBar = findViewById(R.id.Loading_id);
        Phone = findViewById(R.id.user_phone);
        dbUser= FirebaseDatabase.getInstance().getReference("users");


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
                String allName = AllName.getText().toString().trim();
                String phone=Phone.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Mail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("password is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Phone.setError("phone is Required");
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
//                             id="";
//                            if(mhuth.getCurrentUser()!=null)
//                            {
                            String id =mhuth.getCurrentUser().getUid();
//                            }
//                            String id=dbUser.push().getKey();
                            User user=new User(id,allName,email,phone,"0",0.0,0,"");
                            dbUser.child(id).setValue(user);
                            dbUser= FirebaseDatabase.getInstance().getReference("users").child(id).child("packages");
//                            dbUser= FirebaseDatabase.getInstance().getReference("users").child(id).child("picture");
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