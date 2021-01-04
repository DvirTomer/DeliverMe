package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Prof extends AppCompatActivity {

    TextView all_name;
    TextView email;
    TextView phone;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        all_name = (TextView)findViewById(R.id.fullname);
        email = (TextView)findViewById(R.id.fullemail);
        phone = (TextView)findViewById(R.id.fullphone);
        title = (TextView)findViewById(R.id.titlename);

        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();

        String userId= take_id.getUid();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user1 = user.child(userId);
        DatabaseReference allname = user1.child("allName");
        DatabaseReference allemail = user1.child("mail");
        DatabaseReference allphone = user1.child("phone");


        allname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                all_name.setText(x);
                title.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allemail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                email.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                phone.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }
}