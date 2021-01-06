package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

import static android.widget.Toast.LENGTH_SHORT;

public class Messages extends AppCompatActivity {
    ListView listview;
    String selected = "";
    String pack;
    String uid="";
    String userId="";
    Hashtable<String,String> hash_id = new Hashtable<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ///// Listview
        listview = (ListView) findViewById(R.id.lv3);
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayList<String> helper = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list,arraylist);
        listview.setAdapter(adapter);


        //
        //
        FirebaseUser take_id=FirebaseAuth.getInstance().getCurrentUser();

        userId= take_id.getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");

//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arraylist.clear();
//                String y;
//                hash_id.clear();
//
//                String rate = dataSnapshot.child("rate").getValue().toString();
//                if(rate.length()>3){
//                    rate=rate.substring(0,3);
//                }
                userId= take_id.getUid();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String client_name = snapshot.child("allName").getValue().toString();
//                    Toast.makeText(Messages.this,userId+"before22", LENGTH_SHORT).show();
//
                    for(DataSnapshot kid : snapshot.child("packages").getChildren()){

//                        Toast.makeText(Messages.this,userId, LENGTH_SHORT).show();
//
                        if(!kid.child("status").getValue().toString().equals("ממתין למשלוח")   && kid.child("sender_id").getValue().toString().equals(userId)) {
//                        Toast.makeText(Messages.this,userId+"after1", LENGTH_SHORT).show();

                            try {
                                String full = "סוג מוצר: " + kid.child("product").getValue().toString() + "\n" + "סטטוס: "  +kid.child("status").getValue().toString() + "\n" + "שם לקוח: "+client_name ;
                                String ID = kid.child("pacID").getValue().toString();

//                        y = kid.child("note").getValue().toString();
//                        String x = kid.child("product").getValue().toString();
                                hash_id.put(full, ID);
////                        String ans = "    "+hash_id.get(full);
                                arraylist.add(full);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//
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
//                if(selected.contains("בדרך")){
//                    Toast.makeText(Delivery_Details.this,"true", LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(Delivery_Details.this,"false", LENGTH_SHORT).show();
//
//                }


//                String val = hash_id.get(full);
//                Toast.makeText(Delivery_Details.this,"חבילה נבחרה!", LENGTH_SHORT).show();
                view.setSelected(true);
                if((selected=="" || selected==" " || selected.length()<2 || selected==null)|| (!selected.contains("ממתין לאישור") && !selected.contains("בדרך")))
                {
                    Toast.makeText(Messages.this,"עליך לבחור חבילה אשר ממתינה לאישור!", LENGTH_SHORT).show();
                }
                else if (selected.contains("בדרך"))
                {
                    Toast.makeText(Messages.this,"חבילה שבדרך נבחרה!", LENGTH_SHORT).show();
//                    choose_package2_rate();
                }
                else{
                    Toast.makeText(Messages.this,"חבילה נבחרה!", LENGTH_SHORT).show();
//                    choose_package();
                }
            }
        });



    }

    public void choose_package(){
        Intent intent = new Intent(this, Confirmation2.class);
        intent.putExtra("temp", selected);
        intent.putExtra("id_", hash_id.get(selected));
        hash_id.remove(selected);

        startActivity(intent);
    }
    public void choose_package2_rate(){
        Intent intent = new Intent(this, Rate_Sender.class);
        intent.putExtra("temp", selected);
        intent.putExtra("id_", hash_id.get(selected));
//        intent.putExtra("id_sender",);



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