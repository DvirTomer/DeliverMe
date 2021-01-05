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


public class Rate_Sender extends AppCompatActivity {
    RatingBar ratingBar;
    Button submit;
    String id_cur = "";
    String count_rates;
    String rates;
    int c = 0;
    double r = 0.0;
    boolean flag = false;
    boolean flag2 = false;

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
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(id_cur);

                DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference user1 = user.child(id_cur);
                DatabaseReference rate = user1.child("rate");
                DatabaseReference countrate = user1.child("count_rate");
                countrate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(flag==false) {
                            count_rates = dataSnapshot.getValue().toString();
                            c = Integer.parseInt(count_rates);
                            flag = true;

                            int g = c + 1;
                            c = g;
                            Toast.makeText(Rate_Sender.this, g + "x", Toast.LENGTH_LONG).show();

                            dataSnapshot.getRef().setValue(g);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                rate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         if(flag2==false)
                         {
                             rates = dataSnapshot.getValue().toString();
                             r = Double.parseDouble(rates);
                             double curr = Double.parseDouble(s);
                             if(c>0) {
                                 double temp = ((c - 1) * r + curr) / c;
                                 dataSnapshot.getRef().setValue(temp);
                             }
                             else{
                                 dataSnapshot.getRef().setValue(curr);
                             }
                             flag2 = true;

                         }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });








//                db.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot kid : dataSnapshot.getChildren()) {
////                            int a = (int)(kid.child("count_rate").getValue(Integer.class));
//                            String x = kid.child("count_rate").getValue().toString();
//                            Toast.makeText(Rate_Sender.this, x+"x", Toast.LENGTH_LONG).show();
//
//
////                            if(a==0){
//////                                kid.child("count_rate").getRef().setValue(++a);
//////                                Double r = Double.parseDouble(s);
//////                                kid.child("rate").getRef().setValue(r);
////                                 Toast.makeText(Rate_Sender.this, "AAVVII", Toast.LENGTH_LONG).show();
////
////
////
////                            }
//
////                            kid.child("rate").getRef().setValue()
//
//
//                        }
//
//                        }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });





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
                                id_cur = kid.child("sender_id").getValue().toString();
//                                Toast.makeText(Rate_Sender.this, id_cur, Toast.LENGTH_LONG).show();
                           break;


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
