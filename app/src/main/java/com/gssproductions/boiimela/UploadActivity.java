package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

class UploadDataUserDetails{
    private String uid;
    private String title;
    private String authorName;
    private String publisherName;
    private String description;

    private String address;

    private String price;

    private String phoneNumber;

    private String coverType;

    private String imgUrl0;
    private String imgUrl1;
    private String imgUrl2;



    public UploadDataUserDetails() {
    }

    public UploadDataUserDetails(String uid, String title, String authorName, String publisherName, String description, String address, String price, String phoneNumber, String coverType, String imgUrl0, String imgUrl1, String imgUrl2) {
        this.uid = uid;
        this.title = title;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.description = description;
        this.address = address;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.coverType = coverType;
        this.imgUrl0 = imgUrl0;
        this.imgUrl1 = imgUrl1;
        this.imgUrl2 = imgUrl2;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
    }

    public String getImgUrl0() {
        return imgUrl0;
    }

    public void setImgUrl0(String imgUrl0) {
        this.imgUrl0 = imgUrl0;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }
}
public class UploadActivity extends AppCompatActivity {

    String cover;

    EditText etTitle, etAuthorName, etPublisherName, etDescription, etPrice, etPhoneNumber, etAddress;

    RadioGroup radioGroupCoverType;

    Button btnSelectImage_side, btnUploadData;
    int position = 0;

    ImageButton arrow_right, arrow_left;
    ImageSwitcher imageView;

    FirebaseDatabase firebaseDb;
    DatabaseReference firebaseDbRef;

    FirebaseStorage storageDb;
    StorageReference storageDbRef;

    //private Uri filePath;
    ArrayList<Uri> filePath;

    private final int PICK_IMAGE_REQUEST = 123;
    private final int PICK_IMAGE_MULTIPLE = 1;

    UploadDataUserDetails ob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().hide();

        filePath = new ArrayList<Uri>();

        etTitle = findViewById(R.id.etTitle);
        etAuthorName = findViewById(R.id.etAuthorName);
        etPublisherName = findViewById(R.id.etPublisherName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);

        radioGroupCoverType = findViewById(R.id.radioGroupCoverType);

        imageView = findViewById(R.id.imageView);
        btnSelectImage_side = findViewById(R.id.btnSelectImage);
        btnUploadData = findViewById(R.id.btnUploadData);

        arrow_right = findViewById(R.id.arrow_right);
        arrow_left = findViewById(R.id.arrow_left);

        firebaseDb = FirebaseDatabase.getInstance();
        firebaseDbRef = firebaseDb.getReference("bookData");

        storageDb = FirebaseStorage.getInstance();
        storageDbRef = storageDb.getReference();

        btnSelectImage_side.setOnClickListener(view -> {
            selectImage();
        });

        btnUploadData.setOnClickListener(view -> {
            uploadData();
        });


        imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(getApplicationContext());
                return imageView1;
            }
        });

        arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < filePath.size() - 1) {
                    // increase the position by 1
                    position++;
                    imageView.setImageURI(filePath.get(position));
                } else {
                    Toast.makeText(UploadActivity.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click here to view previous image
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    // decrease the position by 1
                    position--;
                    imageView.setImageURI(filePath.get(position));
                }
            }
        });

    }


    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");  // All types of images

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent,
                "Select Picture"), PICK_IMAGE_MULTIPLE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Check karenge agr image choose karna success hgya h ya nahi, English bol Saqib English !
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    filePath.add(imageurl);
                }
                // setting 1st selected image into image switcher
                imageView.setImageURI(filePath.get(0));
                position = 0;
            } else {
                Uri imageurl = data.getData();
                filePath.add(imageurl);
                imageView.setImageURI(filePath.get(0));
                position = 0;
            }

        }
    }

    private void uploadData() {

        radioGroupCoverType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.HardCoverRadioButton:
                        cover="HARD COVER";
                        break;

                    case R.id.PaperBackRadioButton:
                        cover="PAPER BACK";
                        break;

                    case R.id.SoftCoverRadioButton:
                        cover="SOFT COVER";
                        break;
                    default:
                        cover="";
                }
            }
        });

        ob = new UploadDataUserDetails(FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),
                etTitle.getText().toString(),
                etAuthorName.getText().toString(),
                etPublisherName.getText().toString(),
                etDescription.getText().toString(),
                etAddress.getText().toString(),
                etPrice.getText().toString(),
                etPhoneNumber.getText().toString(),
                cover,
                "","","");
        for(int i = 0; i < 3; i++){
            if(filePath.get(i) != null){
                ProgressDialog pd = new ProgressDialog(this);
                pd.setTitle("Uploading Data");
                pd.show();



                StorageReference ref = storageDbRef.child("bookImage/"+ ob.getUid()+"/"+ ob.getTitle()+"/" + UUID.randomUUID().toString());

                int finalI = i;
                ref.putFile(filePath.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> dwnldUrl = ref.getDownloadUrl();

                                dwnldUrl.addOnSuccessListener(uri -> {
                                    Log.d("Image Url #" + finalI, uri.toString());
                                    FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()+"/"+ob.getTitle()+"/imgUrl"+finalI).setValue(uri.toString());

                                });

//                                FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()).child(ob.getTitle()).setValue(ob);
                                pd.dismiss();
                                Toast.makeText(UploadActivity.this, "Upload Done! #"+finalI, Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(UploadActivity.this, BaseActivity.class));
//                                finish();
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
        FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()).child(ob.getTitle()).setValue(ob);
        startActivity(new Intent(UploadActivity.this, BaseActivity.class));
    }

}