package com.example.deliverme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;
import java.security.Permission;

import dagger.multibindings.ElementsIntoSet;

public class Prof extends AppCompatActivity {

    TextView all_name;
    TextView email;
    TextView phone;
    TextView title;
    ImageView image;
    ImageView picture;
    DatabaseReference dbUserpicture;

    Uri imageURI;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        all_name = (TextView)findViewById(R.id.fullname);
        email = (TextView)findViewById(R.id.fullemail);
        phone = (TextView)findViewById(R.id.fullphone);
        title = (TextView)findViewById(R.id.titlename);
        picture = (ImageView)findViewById(R.id.upload_img);
        image = (ImageView)findViewById(R.id.upload_img);

        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();

        String userId= take_id.getUid();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user1 = user.child(userId);
        DatabaseReference allname = user1.child("allName");
        DatabaseReference allemail = user1.child("mail");
        DatabaseReference allphone = user1.child("phone");
        DatabaseReference allpicture = user1.child("picture");
        dbUserpicture= FirebaseDatabase.getInstance().getReference("users").child(userId).child("picture");


        image.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View v) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    pickImageFromGallery();
                }
            }
               pickImageFromGallery();
                                     }
                                 }

        );
        allname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                all_name.setText(x);
                title.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allemail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                email.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                phone.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allpicture.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
//                Picasso.get().load(x).into(picture);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else
                    Toast.makeText(this, "Perimission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageURI = data.getData();
//            image.setImageURI(data.getData());
            Picasso.get().load(imageURI).into(image);
            dbUserpicture.setValue(imageURI.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}