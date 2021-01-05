package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Hashtable;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import static android.widget.Toast.*;


public class Delivery_Person extends AppCompatActivity {
    ListView listview;
    Button confirm;
    String selected = "";
    String pack;
    String uid="";
    Hashtable<String,String> hash_id = new Hashtable<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_person);
        ///// Listview
        listview = (ListView) findViewById(R.id.lv1);
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayList<String> helper = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list,arraylist);
        listview.setAdapter(adapter);


        //
        confirm = (Button) findViewById(R.id.button2);
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

                        if(kid.child("status").getValue().toString().equals("ממתין למשלוח")) {

                            try {
                                String full = "סוג מוצר: " + kid.child("product").getValue().toString() + "\n" + "מוצא: " + kid.child("citySrc").getValue().toString() + " " + kid.child("streetSrc").getValue().toString()
                                        + "\n" + "יעד: " + kid.child("cityDst").getValue().toString()
                                        + " " + kid.child("streetDst").getValue().toString() + "\n" + "זמני משלוח: " + "\n" + "תאריך: " + kid.child("dateDst").getValue().toString() + "\n"
                                        + "שעות משלוח: " + kid.child("timeDst").getValue().toString();

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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Delivery_Person.this,"clicked"+parent.getItemAtPosition(position).toString(), LENGTH_SHORT).show();
                selected =""+ parent.getItemAtPosition(position).toString();
//                String val = hash_id.get(full);
                Toast.makeText(Delivery_Person.this,"חבילה נבחרה!", LENGTH_SHORT).show();
                view.setSelected(true);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected=="" || selected==" " || selected.length()<2 || selected==null)
                {
                    Toast.makeText(Delivery_Person.this,"עליך לבחור חבילה לשליחה!", LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(Delivery_Person.this,"חבילה נבחרה", LENGTH_SHORT).show();
                   choose_package();
                }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void choose_package(){
        Intent intent = new Intent(this, Confirmation.class);
        intent.putExtra("temp", selected);
        intent.putExtra("id_", hash_id.get(selected));
        hash_id.remove(selected);


        startActivity(intent);
    }



}

