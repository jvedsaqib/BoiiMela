package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private String condition;
    private String category;
    private String otherCategory;
    private String imgUrl0;
    private String imgUrl1;
    private String imgUrl2;
    private String seller_name;
    private Boolean isSold;

    private String adUID;

    private double latitude, longitude;

    public UploadDataUserDetails() {
    }

    public UploadDataUserDetails(String uid, String title, String authorName, String publisherName, String description, String address, String price, String phoneNumber, String coverType, String condition, String category, String otherCategory, String imgUrl0, String imgUrl1, String imgUrl2, String seller_name, Boolean isSold, String adUID, double latitude, double longitude) {
        this.uid = uid;
        this.title = title;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.description = description;
        this.address = address;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.coverType = coverType;
        this.condition = condition;
        this.category = category;
        this.otherCategory = otherCategory;
        this.imgUrl0 = imgUrl0;
        this.imgUrl1 = imgUrl1;
        this.imgUrl2 = imgUrl2;
        this.seller_name = seller_name;
        this.isSold = isSold;
        this.adUID = adUID;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOtherCategory() {
        return otherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        this.otherCategory = otherCategory;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public String getAdUID() {
        return adUID;
    }

    public void setAdUID(String adUID) {
        this.adUID = adUID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
public class UploadActivity extends AppCompatActivity implements Serializable {

    private int GPSoff = 0;

    String cover, condition, category, otherCategory;
    EditText etTitle, etAuthorName, etPublisherName, etDescription, etPrice, etPhoneNumber, etAddress, etCategory;
    RadioGroup radioGroupCoverType, radioGroupBookCondition, radioGroupBookCategory;
    RadioButton radioButtonCoverType, radioButtonBookCondition, radioButtonBookCategory;
    Button btnSelectImage_side, btnUploadData, btnClearImage;

    boolean edit = false;
    BookData bookEditOb;

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

    Boolean canUpload = true;

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);
        // getSupportActionBar().hide();

        try {
            GPSoff = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (GPSoff == 0) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            AlertDialog.Builder builder = alertDialogBuilder
                    .setTitle("Location is disabled")
                    .setMessage("This page requires your location, please turn it on.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            {
                                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(onGPS);
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.show();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder = new Geocoder(UploadActivity.this, Locale.getDefault());

                                try {
                                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                                            location.getLongitude(), 1);

                                    latitude = addressList.get(0).getLatitude();
                                    longitude = addressList.get(0).getLongitude();
                                    Log.d("Location ", latitude + " ---- " + longitude);


                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }
        }

        filePath = new ArrayList<Uri>();

        etTitle = findViewById(R.id.etTitle);
        etAuthorName = findViewById(R.id.etAuthorName);
        etPublisherName = findViewById(R.id.etPublisherName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etCategory = findViewById(R.id.etCategory);

        radioGroupCoverType = findViewById(R.id.radioGroupCoverType);
        radioGroupBookCondition = findViewById(R.id.radioGroupBookCondition);
        radioGroupBookCategory = findViewById(R.id.radioGroupBookCategory);

        imageView = findViewById(R.id.imageView);
        btnSelectImage_side = findViewById(R.id.btnSelectImage);
        btnUploadData = findViewById(R.id.btnUploadData);
        btnClearImage = findViewById(R.id.btnClearImage);

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

        btnClearImage.setOnClickListener(v ->{
            if(!filePath.isEmpty()){
                imageView.setImageURI(null);
                filePath.clear();
            }
            else{
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
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

        if(getIntent().getExtras() != null){
            bookEditOb = (BookData) getIntent().getSerializableExtra("Object");
            edit = getIntent().getBooleanExtra("EDIT", true);
        }

        if(edit){
            etTitle.setText(bookEditOb.getTitle());
            etAuthorName.setText(bookEditOb.getAuthorName());
            etPublisherName.setText(bookEditOb.getPublisherName());
            etCategory.setText(bookEditOb.getOtherCategory());
            etDescription.setText(bookEditOb.getDescription());
            etPrice.setText(bookEditOb.getPrice());
            etPhoneNumber.setText(bookEditOb.getPhoneNumber());
            etAddress.setText(bookEditOb.getAddress());
        }

    }

    public void onBackPressed(){
        startActivity(new Intent(UploadActivity.this, BaseActivity.class));
        finish();
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
//        ProgressDialog pdUpload = new ProgressDialog(UploadActivity.this);
//        Handler uploadHandler = new Handler();
//
//        uploadHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                pdUpload.setTitle("Uploading Data");
//                pdUpload.show();
//
//            }
//        }, 10000);
//
//        pdUpload.dismiss();
        int checkedId = radioGroupCoverType.getCheckedRadioButtonId();
        radioButtonCoverType = findViewById(checkedId);
        cover = radioButtonCoverType.getText().toString();

        checkedId = radioGroupBookCondition.getCheckedRadioButtonId();
        radioButtonBookCondition = findViewById(checkedId);
        condition = radioButtonBookCondition.getText().toString();

        checkedId = radioGroupBookCategory.getCheckedRadioButtonId();
        radioButtonBookCategory = findViewById(checkedId);
        category = radioButtonBookCategory.getText().toString();

        otherCategory = etCategory.getText().toString();



        Log.d("Location before uploading", latitude + " ---- " + longitude);
        ob = new UploadDataUserDetails(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                etTitle.getText().toString(),
                etAuthorName.getText().toString(),
                etPublisherName.getText().toString(),
                etDescription.getText().toString(),
                etAddress.getText().toString(),
                etPrice.getText().toString(),
                etPhoneNumber.getText().toString(),
                cover,
                condition,
                category,
                otherCategory,
                "","","",
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                false,
                UUID.randomUUID().toString().substring(0, 12),
                latitude,
                longitude);


        if(filePath.size() >= 3){
            for(int i = 0; i < 3; i++){
                if(filePath.isEmpty()){
                    Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                    canUpload = false;
                    break;
                }
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
                                        FirebaseDatabase.getInstance()
                                                .getReference("bookData")
                                                .child(ob.getUid()+"/"+ob.getTitle()+"/imgUrl"+finalI)
                                                .setValue(uri.toString());
                                    });

//                                FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()).child(ob.getTitle()).setValue(ob);
                                    pd.dismiss();
                                    // Toast.makeText(UploadActivity.this, "Upload Done! #"+finalI, Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(UploadActivity.this, BaseActivity.class));
//                                finish();
                                }
                            })
                            // What?
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
                    break;
                }
            }
        }else{
            for(int i = 0; i < filePath.size(); i++){
                if(filePath.isEmpty()){
                    Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                    canUpload = false;
                    break;
                }
                if(filePath.get(i) != null){
                    ProgressDialog pd = new ProgressDialog(this);
                    pd.setTitle("Uploading Data");
                    pd.show();

                    StorageReference ref = storageDbRef
                            .child("bookImage/"+ ob.getUid()+"/"+ ob.getTitle()+"/" + UUID.randomUUID().toString());

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
                                    //Toast.makeText(UploadActivity.this, "Upload Done! #"+finalI, Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(UploadActivity.this, BaseActivity.class));
//                                finish();
                                }
                            })
                            // What?
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
                    break;
                }
            }
        }
        if(canUpload){
            Log.d("ob before" ,ob.getLatitude() +" "+ ob.getLongitude());
            ob.setLatitude(latitude);
            ob.setLongitude(longitude);
            Log.d("ob after" ,ob.getLatitude() +" "+ ob.getLongitude());
            FirebaseDatabase.getInstance().getReference("bookData").child(ob.getUid()).child(ob.getTitle()).setValue(ob);
            startActivity(new Intent(UploadActivity.this, BaseActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode == REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}