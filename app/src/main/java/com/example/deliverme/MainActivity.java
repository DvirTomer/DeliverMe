package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity{
    Button signout;
    TextView text;
    TextView text_deliver;
    ImageView image;
   private Button send_btn, take_btn;

    DatabaseReference all_name;
    String x="";
    DrawerLayout drawerLayouta;
    NavigationView navigationView;
    Toolbar toolbar;
    String x2 ="";
//    String url = "https://firebasestorage.googleapis.com/v0/b/deliverme-fd8f8.appspot.com/o/uploads?alt=media&token=2e89bbd8-79eb-4edc-97c4-4622ab4cae5d";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//         drawerLayouta=findViewById(R.id.nav);
//         navigationView;

        signout=(Button)findViewById(R.id.Logout);
        send_btn=(Button)findViewById(R.id.send_delivery);
        take_btn =(Button)findViewById(R.id.take_delivery);
        text_deliver = (TextView) findViewById(R.id.textGrid);
        image = (ImageView)findViewById(R.id.profile_image);
        ////**** this part for get name from user
        text = (TextView)findViewById(R.id.userName_);


        FirebaseUser take_id=FirebaseAuth.getInstance().getCurrentUser();

        String userId= take_id.getUid();


        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user1 = user.child(userId);
        DatabaseReference all_name = user1.child("allName");

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("picture");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        x2 = snapshot.child("imageUrl").getValue().toString();
                        Picasso.get().load(x2).into(image);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        all_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 x = dataSnapshot.getValue().toString();
                text.setText("Hallo "+x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        ////****


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
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_profile();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }

        });
//        text_deliver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                move_profile();
//            }
//        });
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
            return super.onCreateOptionsMenu(menu);
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
            Intent intent=new Intent(this, Messages.class);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id123=item.getItemId();
//        if(id123==R.id.nav_mess)
//        {
//            Intent intent=new Intent(MainActivity.this, Messages.class);
//            startActivity(intent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//        switch(item.getItemId())
//        {
//            case R.id.nav_home:
//                Intent intent=new Intent(this,MainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.nav_log:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();
//                break;
//        }
//        return true;
//    }
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
    public void move_profile()
    {
//        Toast.makeText(MainActivity.this,"clicked", LENGTH_SHORT).show();
        Intent intent=new Intent(this, Prof.class);
        startActivity(intent);
    }


}
///////132