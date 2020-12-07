package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button signout;
    TextView text;

   private Button send_btn, take_btn;

    DatabaseReference all_name;
    String y="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String name ="shalom";
//        SharedPreferences prefs = getSharedPreferences("deliver",MODE_PRIVATE);
//        name = prefs.getString("username",)
        signout=(Button)findViewById(R.id.Logout);
        send_btn=(Button)findViewById(R.id.send_delivery);
        take_btn =(Button)findViewById(R.id.take_delivery);


//        String a = "aviviviviv";
        text = (TextView) findViewById(R.id.userName_);
//        text.setText(a.toString());

        /////
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        String userId= user.getUid();
//        all_name= FirebaseDatabase.getInstance().getReference("users").child(userId);
////        ArrayList<String> arr  = new ArrayList<>();
//        all_name.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                arr.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        y = "id: " + snapshot.child("mail").getValue(String.class);
//                        text.setText(y.toString());
//
//
//
//                }
////                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        String x ="1";
        ////
        take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_take();
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_send();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }

        });
    }
    public void open_send()
    {
        Intent intent=new Intent(this, Client.class);
        startActivity(intent);
    }
    public void open_take()
    {
        Intent intent=new Intent(this, Delivery_Person.class);
        startActivity(intent);
    }
}
///////132