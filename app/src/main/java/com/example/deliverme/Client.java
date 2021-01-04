package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Client extends AppCompatActivity {
    EditText citySrc, streetSrc, dateSrc, timeSrc,note,cityDst, streetDst, dateDst, timeDst;
    Spinner product;
    Button send;
//    FirebaseAuth m;
//String ScitySrc = "ariel";

    DatabaseReference dbUserPac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        citySrc = findViewById(R.id.city_src);
        streetSrc = findViewById(R.id.street_src);
        dateSrc = findViewById(R.id.date_src);
        timeSrc = findViewById(R.id.time_src);
        note = findViewById(R.id.notes);
        cityDst = findViewById(R.id.city_dst);
        streetDst = findViewById(R.id.street_dst);
        dateDst = findViewById(R.id.date_dst);
        timeDst = findViewById(R.id.time_dst);
        product = findViewById(R.id.spinner);
//        m = FirebaseAuth.getInstance();
//        TextView textProduct= (TextView) product.getSelectedView();
        send = (Button)findViewById(R.id.send_pac);
        ////
//        dbUserPac= FirebaseDatabase.getInstance().getReference("users");
//        String id=dbUserPac.getKey();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        // id auth
        String userId= user.getUid();
        dbUserPac= FirebaseDatabase.getInstance().getReference("users").child(userId).child("packages");
        String pacID = dbUserPac.push().getKey();


        //        addPack(pac,pacID);
        ////
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ScitySrc = citySrc.getText().toString().trim();
                String SstreetSrc = streetSrc.getText().toString();
                String StimeSrc1 = dateSrc.getText().toString();
                String StimeSrc2 = timeSrc.getText().toString();
                String ScityDst = cityDst.getText().toString();
                String SstreetDst = streetDst.getText().toString();
                String StimeDst1 = dateDst.getText().toString();
                String StimeDst2 = timeDst.getText().toString();
                String Snote = note.getText().toString().trim();
                String Sproduct=product.getSelectedItem().toString();


                if (TextUtils.isEmpty(ScitySrc)) {
                    citySrc.setError("אנא בחר/י עיר מקור");
                    return;
                }
                if (TextUtils.isEmpty(SstreetSrc)) {
                    streetSrc.setError("אנא בחר/י רחוב מקור");
                    return;
                }
                if (TextUtils.isEmpty(StimeSrc1)) {
                    dateSrc.setError("אנא בחר/י תאריך מקור");
                    return;
                }
                if (TextUtils.isEmpty(StimeSrc2)) {
                    timeSrc.setError("אנא בחר/י שעת מקור");
                    return;
                }
                if (TextUtils.isEmpty(ScityDst)) {
                    cityDst.setError("אנא בחר/י עיר יעד");
                    return;
                }
                if (TextUtils.isEmpty(SstreetDst)) {
                    streetDst.setError("אנא בחר/י רחוב יעד");
                    return;
                }
                if (TextUtils.isEmpty(StimeDst1)) {
                    dateDst.setError("אנא בחר/י תאריך יעד");
                    return;
                }
                if (TextUtils.isEmpty(StimeDst2)) {
                    timeDst.setError("אנא בחר/י שעת יעד");
                    return;
                }
                if (Sproduct.equals("סוג מוצר...")) {
                    TextView errorText =(TextView)product.getSelectedView();
                    errorText.setText("אנא בחר/י סוג מוצר");
                    return;
                }
                Package pac = new Package(ScitySrc,SstreetSrc,StimeSrc1,StimeSrc2,ScityDst,SstreetDst,StimeDst1,StimeDst2,Snote,Sproduct,pacID,userId);
                dbUserPac.child(pacID).setValue(pac);

                sendTo();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }
    public void sendTo()
    {
        Toast.makeText(Client.this, "המשלוח הועלה בהצלחה ומחכה לאישור שליח", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}