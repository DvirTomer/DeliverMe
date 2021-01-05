package com.example.deliverme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class rate_sender extends AppCompatActivity {
    RatingBar ratingBar;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_sender);
        ratingBar = findViewById(R.id.ratingBar);
        submit = findViewById(R.id.button3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(),s+" Stars",Toast.LENGTH_SHORT).show();

            }
        });


////////////////////////////////////////////
        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();
        String userId= take_id.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(userId);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String y;
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                for(DataSnapshot kid : dataSnapshot.child("packages").getChildren()){
/////////

                        //////////
                    if(!kid.child("status").getValue().toString().equals("ממתין למשלוח") && getIntent().getStringExtra("id_").equals(kid.child("pacID").getValue().toString())) {

                        try {
//                            String ID = kid.child("pacID").getValue().toString();

                            if(kid.child("status").getValue().toString().equals("בדרך"))   //// and
                            {
                                kid.child("status").getRef().setValue("החבילה הגיעה"); ///////
                            }

//                        y = kid.child("note").getValue().toString();
//                        String x = kid.child("product").getValue().toString();
//                        String ans = "    "+hash_id.get(full);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ////////////////////////////////////////////
    }
}
