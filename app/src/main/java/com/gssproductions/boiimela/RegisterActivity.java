package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName,editTextRegisterEmail,editTextRegisterDob,editTextRegisterMobile,
            editTextRegisterPwd,editTextRegisterCPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog picker;
    private static final String TAG="RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // getSupportActionBar().hide();

        Toast.makeText(RegisterActivity.this, "you can register now", Toast.LENGTH_SHORT).show();

        editTextRegisterFullName=findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail=findViewById(R.id.editText_register_email);
        editTextRegisterDob=findViewById(R.id.editText_register_dob);
        editTextRegisterMobile=findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd=findViewById(R.id.editText_register_password);
        editTextRegisterCPwd=findViewById(R.id.editText_register_confirm_password);
        radioGroupRegisterGender=findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();
        progressBar=findViewById(R.id.progressbar_reg);

        //set DatePicker
        editTextRegisterDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDob.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        //hide password
      /*  ImageView imageViewpwd=findViewById(R.id.imageView_show_hide_pwd_reg);
        imageViewpwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextRegisterPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //if visible
                    imageViewpwd.setImageResource(R.drawable.ic_hide_pwd);
                    editTextRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editTextRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                imageViewpwd.setImageResource(R.drawable.ic_show_pwd);
            }
        });


        //con hide password
        ImageView imageViewcpwd=findViewById(R.id.imageView_show_hide_cpwd_reg);
        imageViewcpwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextRegisterCPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //if visible
                    imageViewcpwd.setImageResource(R.drawable.ic_hide_pwd);
                    editTextRegisterCPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editTextRegisterCPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                imageViewcpwd.setImageResource(R.drawable.ic_show_pwd);
            }
        });*/


        Button buttonRegister=findViewById(R.id.button_reg);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId=radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected=findViewById(selectedGenderId);

                String textFullName=editTextRegisterFullName.getText().toString();
                String textEmail=editTextRegisterEmail.getText().toString();
                String textDob=editTextRegisterDob.getText().toString();
                String textMobile=editTextRegisterMobile.getText().toString();
                String textPwd=editTextRegisterPwd.getText().toString();
                String textCPwd=editTextRegisterCPwd.getText().toString();
                String textGender;

                //validate mobile number

                String mobileRegex = "^[6-9]\\d{9}$";
//                String mobileRegex = "[6-9][0-9][9]";
                Matcher mobileMatcher;
                Pattern mobilePattern=Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterActivity.this, "please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full name required");
                    editTextRegisterFullName.requestFocus();
                }else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "please enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "please re-enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDob)) {
                    Toast.makeText(RegisterActivity.this, "please enter your date of birth", Toast.LENGTH_LONG).show();
                    editTextRegisterDob.setError("Date of birth is required");
                    editTextRegisterDob.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(RegisterActivity.this, "please enter your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(RegisterActivity.this, "please enter your number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Number is required");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length()!=10) {
                    Toast.makeText(RegisterActivity.this, "please re-enter your number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid number is required");
                    editTextRegisterMobile.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(RegisterActivity.this, "invalid number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid number is required");
                    editTextRegisterMobile.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterActivity.this, "please enter your password", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("password is required");
                    editTextRegisterPwd.requestFocus();
                } else if (textPwd.length()<6) {
                    Toast.makeText(RegisterActivity.this, "password should be at least 6 digits", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("password is too weak");
                    editTextRegisterPwd.requestFocus();
                } else if (TextUtils.isEmpty(textCPwd)) {
                    Toast.makeText(RegisterActivity.this, "please enter password again", Toast.LENGTH_LONG).show();
                    editTextRegisterCPwd.setError("password confirmation is required");
                    editTextRegisterCPwd.requestFocus();
                } else if (!textPwd.equals(textCPwd)) {
                    Toast.makeText(RegisterActivity.this, "please enter same email", Toast.LENGTH_LONG).show();
                    editTextRegisterCPwd.setError("same password is required");
                    editTextRegisterCPwd.requestFocus();
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterCPwd.clearComposingText();
                }else {
                    textGender=radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textDob,textGender,textMobile,textPwd);
                }
            }
        });
    }

    private void registerUser(String textFullName, String textEmail, String textDob, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser=auth.getCurrentUser();

                    //update display name of user
                    UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //data into realtime database
                    ReadwriteUserDetails writeUserDetails=new ReadwriteUserDetails(textFullName,textDob,textGender,textMobile);
                    //extracting user reference from database for 'Registered users'
                    DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                //send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this, "User register successfully.please verify your email", Toast.LENGTH_LONG).show();
                               /*//open user profile after successful registration
                                Intent intent=new Intent(RegisterActivity.this,UserProfileActivity.class);
                                //to prevent user from returning back to register activity on pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();//to close register activity    */
                            }else {
                                Toast.makeText(RegisterActivity.this, "User register failed.please try again", Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                }else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTextRegisterPwd.setError("password is weak");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextRegisterPwd.setError("password is invalid");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e) {
                        editTextRegisterPwd.setError("email already in use");
                        editTextRegisterPwd.requestFocus();
                    }catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}