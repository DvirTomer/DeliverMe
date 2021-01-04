package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class Delivery_Details extends AppCompatActivity {
    ListView listview;
    Button confirm;
    String selected = "";
    String pack;
    String uid="";
    Hashtable<String,String> hash_id = new Hashtable<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__details);

        ///// Listview
        listview = (ListView) findViewById(R.id.lv2);
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayList<String> helper = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list,arraylist);
        listview.setAdapter(adapter);


        //
        confirm = (Button) findViewById(R.id.button22);
        //

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arraylist.clear();
                String y;
                hash_id.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot kid : snapshot.child("packages").getChildren()){

                        if(!kid.child("status").getValue().toString().equals("ממתין למשלוח")) {

                            try {
                                String full = "סוג מוצר: " + kid.child("product").getValue().toString() + "\n" + "סטטוס: "  +kid.child("status").getValue().toString() + "\n" + "פרטי שליח: "+kid.child("sender").getValue().toString() + "\n"+"לאישור, לחץ כאן";
                                String ID = kid.child("pacID").getValue().toString();
//                        y = kid.child("note").getValue().toString();
//                        String x = kid.child("product").getValue().toString();
                                hash_id.put(full, ID);
//                        String ans = "    "+hash_id.get(full);
                                arraylist.add(full);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void choose_package(){
        Intent intent = new Intent(this, Confirmation.class);
        intent.putExtra("temp", selected);
        intent.putExtra("id_", hash_id.get(selected));
        hash_id.remove(selected);


        startActivity(intent);
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
}