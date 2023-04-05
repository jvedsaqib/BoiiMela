package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

class UploadDataUserDetails{
    private String uid;
    private String title;
    private String authorName;
    private String publisherName;
    private String description;

    private String imgUrl;

    public UploadDataUserDetails() {
    }

    public UploadDataUserDetails(String uid, String title, String authorName, String publisherName, String description, String imgUrl) {
        this.uid = uid;
        this.title = title;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
public class UploadActivity extends AppCompatActivity {

    EditText etTitle, etAuthorName, etPublisherName, etDescription;

    Button btnSelectImage, btnUploadData;

    FirebaseDatabase firebaseDb;
    DatabaseReference firebaseDbRef;

    FirebaseStorage storageDb;
    StorageReference storageDbRef;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 123;

    UploadDataUserDetails ob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().hide();

        etTitle = findViewById(R.id.etTitle);
        etAuthorName = findViewById(R.id.etAuthorName);
        etPublisherName = findViewById(R.id.etPublisherName);
        etDescription = findViewById(R.id.etDescription);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUploadData = findViewById(R.id.btnUploadData);

        firebaseDb = FirebaseDatabase.getInstance();
        firebaseDbRef = firebaseDb.getReference("bookData");

        storageDb = FirebaseStorage.getInstance();
        storageDbRef = storageDb.getReference();

        btnSelectImage.setOnClickListener(view -> {
            selectImage();
        });

        btnUploadData.setOnClickListener(view -> {
            uploadData();
        });


    }


    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");  // All types of images
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(
                Intent.createChooser(
                        intent, "Select Image"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Check karenge agr image choose karna success hgya h ya nahi, English bol Saqib English !
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            filePath = data.getData();  // path of the image on the device

        }

    }

    private void uploadData() {

        ob = new UploadDataUserDetails(FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),
                etTitle.getText().toString(), etAuthorName.getText().toString(), etPublisherName.getText().toString(),
                etDescription.getText().toString(), "");
        if(filePath != null){
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Uploading Data");
            pd.show();



            StorageReference ref = storageDbRef.child("bookImage/" + UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> dwnldUrl = ref.getDownloadUrl();

                            dwnldUrl.addOnSuccessListener(uri -> {
                                Log.d("Image Url", uri.toString());
                                FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()+"/"+ob.getTitle()+"/imgUrl").setValue(uri.toString());

                            });

                            FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()).child(ob.getTitle()).setValue(ob);
                            pd.dismiss();
                            Toast.makeText(UploadActivity.this, "Upload Done!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UploadActivity.this, BaseActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadActivity.this, "Upload Failed!" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pd.setMessage("Uploaded " + (int)progress + "%");
                        }
                    });
        }
        else{
            Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }



}