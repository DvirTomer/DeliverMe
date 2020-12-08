package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Client extends AppCompatActivity {
    EditText citySrc, streetSrc, timeSrc1,timeSrc2,note,cityDst, streetDst, timeDst1,timeDst2;
//    Spinner product;
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
        timeSrc1 = findViewById(R.id.time_src1);
        timeSrc2 = findViewById(R.id.time_src2);
        note = findViewById(R.id.notes);
        cityDst = findViewById(R.id.city_dst);
        streetDst = findViewById(R.id.street_dst);
        timeDst1 = findViewById(R.id.time_dst1);
        timeDst2 = findViewById(R.id.time_dst2);
//        product = findViewById(R.id.spinner);
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
//        String x ="1";
//        Package pac = new Package(x,x,x,x,x,x,x,x,x,x);
//                Toast.makeText(Client.this,""+ScitySrc ,Toast.LENGTH_LONG).show();

        String pacID = dbUserPac.push().getKey();


        //        addPack(pac,pacID);
        ////
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ScitySrc = citySrc.getText().toString().trim();

                String SstreetSrc = streetSrc.getText().toString();
                String StimeSrc1 = timeSrc1.getText().toString();
                String StimeSrc2 = timeSrc2.getText().toString();
                String ScityDst = cityDst.getText().toString();
                String SstreetDst = streetDst.getText().toString();
                String StimeDst1 = timeDst1.getText().toString();
                String StimeDst2 = timeDst2.getText().toString();
                String Snote = note.getText().toString().trim();

//                String Sproduct = textProduct.getText().toString().trim();
//


                if (TextUtils.isEmpty(ScitySrc)) {
                    citySrc.setError("Source city is Required");
                    return;
                }
                if (TextUtils.isEmpty(SstreetSrc)) {
                    streetSrc.setError("Source street is Required");
                    return;
                }
                if (TextUtils.isEmpty(StimeSrc1)) {
                    timeSrc1.setError("Source time is Required");
                    return;
                }
                if (TextUtils.isEmpty(StimeSrc2)) {
                    timeSrc2.setError("Source time is Required");
                    return;
                }
                if (TextUtils.isEmpty(ScityDst)) {
                    cityDst.setError("Destination city is Required");
                    return;
                }
                if (TextUtils.isEmpty(SstreetDst)) {
                    streetDst.setError("Destination street is Required");
                    return;
                }
                if (TextUtils.isEmpty(StimeDst1)) {
                    timeDst1.setError("Destination time is Required");
                    return;
                }
                if (TextUtils.isEmpty(StimeDst2)) {
                    timeDst2.setError("Destination time is Required");
                    return;
                }

                Package pac = new Package(ScitySrc,SstreetSrc,StimeSrc1,StimeSrc2,ScityDst,SstreetDst,StimeDst1,StimeDst2,"Snote","x");
                dbUserPac.child(pacID).setValue(pac);
////                if (TextUtils.isEmpty(Sproduct)) {
////                   textProduct.setError("נא לבחור סוג מוצר");
////                    return;
////                }
//
//                if(user!=null)
//                {
//                Package pac = new Package(ScitySrc,SstreetSrc,StimeSrc1,StimeSrc2,Snote,ScityDst,SstreetDst,StimeDst1,StimeDst2,"ארון");

//                }
//                else{
//                }






                sendTo();
//
            }
//
//
        });
    }
    public void sendTo()
    {
        Toast.makeText(Client.this, "המשלוח הועלה בהצלחה ומחכה לאישור שליח", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void addPack(Package pac,String pacID)
    {
//        dbUserPac.setValue(pac);
//                dbUserPac.setValue("pac");
    }
}