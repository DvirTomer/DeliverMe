package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
//import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class Client extends AppCompatActivity {
    EditText citySrc, streetSrc, dateSrc, timeSrc,note,cityDst, streetDst, dateDst, timeDst;
    Spinner product;
    Button send;
//    FirebaseAuth m;
//String ScitySrc = "ariel";

    DatabaseReference dbUserPac;


    private static final String TAG = "MainActivity";

    Button dateButton, timeButton;
    String dateTextView, timeTextView;
    Button dateButtonD, timeButtonD;
    String dateTextViewD, timeTextViewD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        citySrc = findViewById(R.id.city_src);
        streetSrc = findViewById(R.id.street_src);
//        dateSrc = findViewById(R.id.dateButton);
//        timeSrc = findViewById(R.id.timeButton);
        note = findViewById(R.id.notes);
        cityDst = findViewById(R.id.city_dst);
        streetDst = findViewById(R.id.street_dst);
//        dateDst = findViewById(R.id.date_dst);
//        timeDst = findViewById(R.id.time_dst);
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


        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        dateButtonD= findViewById(R.id.dateButtonD);
        timeButtonD = findViewById(R.id.timeButtonD);
//        dateTextView = findViewById(R.id.dateTextView);
//        timeTextView = findViewById(R.id.timeTextView);

        //        addPack(pac,pacID);
        ////
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ScitySrc = citySrc.getText().toString().trim();
                String SstreetSrc = streetSrc.getText().toString();
                String StimeSrc1 = dateTextView;
                String StimeSrc2 = timeTextView;
                String ScityDst = cityDst.getText().toString();
                String SstreetDst = streetDst.getText().toString();
                String StimeDst1 = dateTextViewD;
                String StimeDst2 = timeTextViewD;
                String Snote = note.getText().toString().trim();
                String Sproduct=product.getSelectedItem().toString();

//                Toast.makeText(Client.this, StimeSrc2, Toast.LENGTH_LONG).show();

//                if (TextUtils.isEmpty(ScitySrc)) {
//                    citySrc.setError("אנא בחר/י עיר מקור");
//                    return;
//                }
//                if (TextUtils.isEmpty(SstreetSrc)) {
//                    streetSrc.setError("אנא בחר/י רחוב מקור");
//                    return;
//                }
//                if (TextUtils.isEmpty(StimeSrc1)) {
//                    dateSrc.setError("אנא בחר/י תאריך מקור");
//                    return;
//                }
//                if (TextUtils.isEmpty(StimeSrc2)) {
//                    timeSrc.setError("אנא בחר/י שעת מקור");
//                    return;
//                }
//                if (TextUtils.isEmpty(ScityDst)) {
//                    cityDst.setError("אנא בחר/י עיר יעד");
//                    return;
//                }
//                if (TextUtils.isEmpty(SstreetDst)) {
//                    streetDst.setError("אנא בחר/י רחוב יעד");
//                    return;
//                }
//                if (TextUtils.isEmpty(StimeDst1)) {
//                    dateDst.setError("אנא בחר/י תאריך יעד");
//                    return;
//                }
//                if (TextUtils.isEmpty(StimeDst2)) {
//                    timeDst.setError("אנא בחר/י שעת יעד");
//                    return;
//                }
//                if (Sproduct.equals("סוג מוצר...")) {
//                    TextView errorText =(TextView)product.getSelectedView();
//                    errorText.setText("אנא בחר/י סוג מוצר");
//                    return;
//                }
                int price = cal_price(ScitySrc,ScityDst,Sproduct);
                Package pac = new Package(ScitySrc,SstreetSrc,StimeSrc1,StimeSrc2,ScityDst,SstreetDst,StimeDst1,StimeDst2,Snote,Sproduct,pacID,userId,"ממתין למשלוח","","",price);
                dbUserPac.child(pacID).setValue(pac);

                sendTo();
            }
        });




        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
        dateButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButtonD();
            }
        });
        timeButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButtonD();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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
    public void sendTo()
    {
        Toast.makeText(Client.this, "המשלוח הועלה בהצלחה ומחכה לאישור שליח", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextView=dateText;
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();




    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i(TAG, "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeTextView=dateText;
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

    }

    private void handleDateButtonD() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextViewD=dateText;
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();




    }

    private void handleTimeButtonD() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i(TAG, "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeTextViewD=dateText;
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

    }
    public int cal_price(String x, String y, String product) {
        int price = 0;
        boolean big = false;
        boolean middle = false;
        boolean little = false;
        if (product.equals("ארון"))
        {
            big = true;
        }
        if (product.equals("תיק גב"))
        {
            little = true;
        }
        if (product.equals("בגדים"))
        {
            little = true;
        }
        if (product.equals("כיסא"))
        {
            middle = true;
        }
        if (product.equals("מחשב"))
        {
            middle = true;
        }
        if (product.equals("מטען"))
        {
            little = true;
        }
        if (product.equals("מיקרוגל"))
        {
            big = false;
        }
        if (product.equals("פרחים"))
        {
            little = true;
        }
        if (product.equals("תמונה"))
        {
            middle = true;
        }

        if (big == true)
        {
            price = 50;
        }
        if (middle == true)
        {
            price = 35;
        }
        if (little == true)
        {
            price = 20;
        }
        return price;
    }

}