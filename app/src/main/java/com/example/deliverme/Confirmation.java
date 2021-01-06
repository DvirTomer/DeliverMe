package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Confirmation extends AppCompatActivity {
    TextView text;
    Button send_mail;
    String num, msg;
    String phone = "";
    String x;
    String sender_name = "";
    String sender_id ="";
    int i = 8;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


//        msg="tomer king";

        /////
        String temp2 = getIntent().getStringExtra("temp");
//        Toast.makeText(Confirmation.this,temp2, LENGTH_SHORT).show();
        text = (TextView) findViewById(R.id.confim_id);
        text.setText("                  בקשה לאישור חבילה" + "\n\n\n" + "הריני מאשר שחבילה- " + "\n" + temp2 + "\n" + "תישלח ותגיע בשלמותה ליעדה " + "\n\n\n\nלשליחה לאישור הלקוח, שלח הודעה");
        send_mail = (Button) findViewById(R.id.ok_id);
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dia();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sending();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });



    }
    private void dia(){

//    CustomDialogClass cdd = new CustomDialogClass(Confirmation.this);
//    cdd.show();

    }
    private void sending() {
        /////////
        FirebaseUser take_id=FirebaseAuth.getInstance().getCurrentUser();

        String userId= take_id.getUid();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user1 = user.child(userId);
//        DatabaseReference all_name = user1.child("allName");
        user1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sender_name = dataSnapshot.child("allName").getValue().toString();
                sender_id = dataSnapshot.child("id").getValue().toString();
                Toast.makeText(Confirmation.this , sender_id+"",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        /////////
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String y;
                    int k = 0;
                    String pac_id = getIntent().getStringExtra("id_");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (k != 0) {
                            break;
                        }
                        for (DataSnapshot kid : snapshot.child("packages").getChildren()) {

                            if (getIntent().getStringExtra("id_").equals(kid.child("pacID").getValue().toString())) {
                                k = 1;
                                phone = snapshot.child("phone").getValue().toString();

//                                kid.getRef().removeValue();
                                if(flag == true) {
                                    kid.child("status").getRef().setValue("ממתין לאישור");
                                    flag = false;
                                }




                                kid.child("sender").getRef().setValue(""+sender_name);
                                ///////sender_id
                                kid.child("sender_id").getRef().setValue(sender_id);



                                break;
                            }
                        }

                    }
                } catch (Exception e) {
//                    Toast.makeText(Confirmation.this , "ctch",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        /////////////// delivery man phone
        take_id = FirebaseAuth.getInstance().getCurrentUser();
        userId = take_id.getUid();
        user = FirebaseDatabase.getInstance().getReference("users").child(userId);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                x = dataSnapshot.child("phone").getValue().toString();
                String sener_phone= "0"+x.substring(1);



                try {
                    String per2 = "+972"+phone.substring(1);
                    ///the sms will be sent to per2
                    SmsManager sms = SmsManager.getDefault();

                    sms.sendTextMessage(per2+ "", null,"שליח מ-DeliverME מעוניין למסור את החבילה שלך! טלפון:" +sener_phone, null, null);



                    Toast.makeText(Confirmation.this, "הודעה נשלחה בהצלחה", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Confirmation.this, "ההודעה נכשלה", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Intent intent=new Intent(Confirmation.this, MainActivity.class);
        startActivity(intent);


    }
}

