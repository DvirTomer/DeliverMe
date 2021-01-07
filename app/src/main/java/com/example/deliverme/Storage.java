package com.example.deliverme;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Storage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    DatabaseReference dbUserpicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticit_storage);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();
        String userId= take_id.getUid();
        dbUserpicture= FirebaseDatabase.getInstance().getReference("users").child(userId).child("picture");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"+userId);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("picture");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Storage.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);


        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null)
        {
            mStorageRef.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return mStorageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
//                        Log.e(TAG, "then: " + downloadUri.toString());


                        Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                downloadUri.toString());

                        mDatabaseRef.push().setValue(upload);
                    } else
                    {
                        Toast.makeText(Storage.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    sendTo();
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void sendTo()
    {
        Intent intent=new Intent(this, Prof.class);
        startActivity(intent);

    }
}
