package com.example.deliverme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        String temp2 = getIntent().getStringExtra("temp");
        Toast.makeText(Confirmation.this,temp2, LENGTH_SHORT).show();
        text = (TextView) findViewById(R.id.confim_id);
        text.setText(" הריני מאשר שחבילה "+temp2+ "   תישלח ותגיע בשלמותה ליעדה " + "\n לשליחה לאישור הלקוח, לחץ אישור") ;
        send_mail = (Button) findViewById(R.id.ok_id);
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sending();



            }
        });

    }
    public void sending(){
        sendEmail();
    }
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"mayplex@gmail.com"};
        String[] CC = {"mayplex@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Confirmation.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}