package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
public class client extends AppCompatActivity {
    EditText citySrc, streetSrc, timeSrc1,timeSrc2,notes,cityDst, streetDst, timeDst1,timeDst2;
    Spinner product;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }
}