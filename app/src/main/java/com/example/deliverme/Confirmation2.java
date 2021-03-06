package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
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

public class Confirmation2 extends AppCompatActivity {
    TextView text;
    Button send_mail;
    String num, msg;
    String phone = "";
    String x;
    String client_phone = "";
    String sender_phone = "";
    String sender_id = "";
    int i = 8;
    String price ="";
    String temp2="";
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation2);


//        msg="tomer king";

        /////
        temp2 = getIntent().getStringExtra("temp");
        String pacid= getIntent().getStringExtra("id_");
//        Toast.makeText(Confirmation.this,temp2, LENGTH_SHORT).show();
        text = (TextView) findViewById(R.id.confim_id2);
        temp2 = temp2.substring(0,temp2.length()-15);


        ////
        FirebaseUser take_id=FirebaseAuth.getInstance().getCurrentUser();

        String userId= take_id.getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(userId);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot kid : dataSnapshot.child("packages").getChildren()){

                    if(kid.child("pacID").getValue().toString().equals(pacid)) {
//                        Toast.makeText(Confirmation2.this , "in if",Toast.LENGTH_LONG).show();
                        sender_id = kid.child("sender_id").getValue().toString();

                        price = kid.child("price").getValue().toString();
//                        Toast.makeText(Confirmation2.this , price+"i",Toast.LENGTH_LONG).show();
                        text.setText("                  בקשה לאישור שליח"+ "\n\n\n" + "הריני מאשר שחבילה- " + "\n" + temp2 + "\n" + "תישלח על ידי שליח זה " + "\n\n\nלאישור השליח, לחץ ok"
                                + "\n"+"מחיר משלוח: "+price);

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ////


        send_mail = (Button) findViewById(R.id.ok_id2);
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
        DatabaseReference all_name = user1.child("phone");
        all_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client_phone = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        user = FirebaseDatabase.getInstance().getReference("users").child(sender_id).child("phone");
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sender_phone = dataSnapshot.getValue().toString();
//                Toast.makeText(Confirmation2.this , client_phone+"iiiii"+sender_phone,Toast.LENGTH_LONG).show();

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
                                if(flag==true) {
                                    kid.child("status").getRef().setValue("בדרך");
                                    flag = false;
                                }
//                                kid.child("sender").getRef().setValue(""+sender_name);


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
                    String sen = "+972"+ sender_phone.substring(1);
                    String per2 = "+972"+phone.substring(1);
                    ///the sms will be sent to per2
                    SmsManager sms = SmsManager.getDefault();

                    sms.sendTextMessage(sen+ "", null,"הלקוח אישר את המשלוח שלך! טלפון:" +phone, null, null);



                    Toast.makeText(Confirmation2.this, "הודעה נשלחה בהצלחה", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Confirmation2.this, "ההודעה נכשלה", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Intent intent=new Intent(Confirmation2.this, MainActivity.class);
        startActivity(intent);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_home)
        {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_prof)
        {
            Intent intent=new Intent(this, Prof.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_mess)
        {
            Intent intent=new Intent(this, Delivery_Done.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_deli)
        {
            Intent intent=new Intent(this, Delivery_Details.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_log)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

