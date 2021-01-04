package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.lang.reflect.Array;

public class Login extends AppCompatActivity {
    EditText Email, password;
    Button mLogin;
    TextView Create;
//    ProgressBar progressBar;
    FirebaseAuth mhuth;
//    static String flag="";
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.editTextTextPassword4);
        Email = findViewById(R.id.editTextTextEmailAddress4);
        mLogin = findViewById(R.id.BLogin);
        Create = (TextView)findViewById(R.id.create);
        flag = "0";
//        progressBar = findViewById(R.id.progressBar2);
        mhuth = FirebaseAuth.getInstance();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("password is Required");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Your password must be greater than 6");
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                //check the user
                mhuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "התחברות בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
//                          check_admin();
                           String admin_mail= mhuth.getCurrentUser().getEmail();
                            Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
                            switch (email)
                            {
                                case "admin1@gmail.com":
                                    startActivity(new Intent(getApplicationContext(), Admin.class));
                                    break;
                                default:
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    break;
                            }
                        } else {
                            Toast.makeText(Login.this, "שגיאה!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LOGIN -> create account -> Button
                create_User();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(mhuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }

    public void create_User()
    {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
//    public void check_admin()
//    {
//
//        FirebaseUser take_id=FirebaseAuth.getInstance().getCurrentUser();
//        String userId= take_id.getUid();
//        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
//        DatabaseReference user1 = user.child(userId);
//        DatabaseReference Admin = user1.child("admin");
//        Admin.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                flag= dataSnapshot.getValue().toString();
//
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//    }

}



