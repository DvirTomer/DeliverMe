package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Client extends AppCompatActivity {
    EditText citySrc, streetSrc, timeSrc1,timeSrc2,note,cityDst, streetDst, timeDst1,timeDst2;
    Spinner product;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citySrc = findViewById(R.id.city_src);
        streetSrc = findViewById(R.id.street_src);
        timeSrc1 = findViewById(R.id.time_src1);
        timeSrc2 = findViewById(R.id.time_src2);
        note = findViewById(R.id.notes);
        cityDst = findViewById(R.id.city_dst);
        streetDst = findViewById(R.id.street_dst);
        timeDst1 = findViewById(R.id.time_dst1);
        timeDst2 = findViewById(R.id.time_dst2);
        product = findViewById(R.id.spinner);
        send = findViewById(R.id.send_button);
        setContentView(R.layout.activity_client);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ScitySrc = citySrc.getText().toString().trim();
                String SstreetSrc = streetSrc.getText().toString().trim();
                String StimeSrc1 = timeSrc1.getText().toString().trim();
                String StimeSrc2 = timeSrc2.getText().toString().trim();
                String ScityDst = cityDst.getText().toString().trim();
                String SstreetDst = streetDst.getText().toString().trim();
                String StimeDst1 = timeDst1.getText().toString().trim();
                String StimeDst2 = timeDst2.getText().toString().trim();
                String Sproduct = product.getTransitionName().toString().trim();
//                String timeDst1 = timeDst1.getText().toString().trim();
//                String timeDst1 = timeDst1.getText().toString().trim();

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
                if (Sproduct=="סוג מוצר...") {
                    TextView err= (TextView) product.getSelectedView();
                    err.setError("Select product is Required");
                    return;
                }

                // REGISTER THE USER DATA


            }


        });
    }
}