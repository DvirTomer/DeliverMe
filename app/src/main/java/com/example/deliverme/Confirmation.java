package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import org.w3c.dom.Text;

import static android.widget.Toast.LENGTH_SHORT;

public class Confirmation extends AppCompatActivity {
    TextView text;
    Button send_mail;
        String num , msg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);



        num = "+972526664887";
//        msg="tomer king";

        /////
        String temp2 = getIntent().getStringExtra("temp");
        Toast.makeText(Confirmation.this,temp2, LENGTH_SHORT).show();
        text = (TextView) findViewById(R.id.confim_id);
        text.setText(" הריני מאשר שחבילה "+temp2+ "   תישלח ותגיע בשלמותה ליעדה " + "\n לשליחה לאישור הלקוח, לחץ אישור") ;
        send_mail = (Button) findViewById(R.id.ok_id);
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sending();
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sending();
                }else{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                }
            }
            }
        });
        /////


    }
    private void sending(){
    try {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(num , null ,getIntent().getStringExtra("temp") ,null , null);
        Toast.makeText(this , "msg sent",Toast.LENGTH_LONG).show();
    }catch (Exception e){
        e.printStackTrace();
        Toast.makeText(this , "Does not sent",Toast.LENGTH_LONG).show();

    }
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}