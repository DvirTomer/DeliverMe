package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import static android.widget.Toast.*;


public class Delivery_Person extends AppCompatActivity {
    ListView listview;
    Button confirm;
    String selected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_person);
        ///// Listview
        listview = (ListView) findViewById(R.id.lv1);
        ArrayList<String> arraylist = new ArrayList<>();
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String y = "id: "+snapshot.child("id").getValue(String.class)+" mail:"+snapshot.child("mail").getValue(String.class);
                    arraylist.add(y);
//                    arraylist.add(snapshot.getValue().toString());
//                    User u = new User(snapshot.getValue(User.class).id,snapshot.getValue(User.class).allName, snapshot.getValue(User.class).mail);
//                    arraylist.add(u.id);

//                    String t = x.id;
//                    arraylist.add(t);

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
                Toast.makeText(Delivery_Person.this,"clicked"+selected, LENGTH_SHORT).show();
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
                    Toast.makeText(Delivery_Person.this,"clicked "+selected, LENGTH_SHORT).show();
                   choose_package();
                }

            }
        });

    }
    public void choose_package(){
        Intent intent = new Intent(this, Confirmation.class);
        intent.putExtra("temp", selected);
        startActivity(intent);
    }



}

