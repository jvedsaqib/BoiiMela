package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private EditText editTextPwdCur,editTextPwdNew,editTextPwdConfirm;
    private TextView textViewAuthenticated;
    private Button buttonChangePwd,buttonReAuthenticate;
    private ProgressBar progressBar;
    private String userPwdCur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextPwdCur=findViewById(R.id.editText_change_pwd_current);
        editTextPwdNew=findViewById(R.id.editText_change_pwd_new);
        editTextPwdConfirm=findViewById(R.id.editText_change_pwd_new_confirm);
        textViewAuthenticated=findViewById(R.id.textView_change_pwd_authenticated);
        progressBar=findViewById(R.id.progressBar_pwd);
        buttonReAuthenticate=findViewById(R.id.button_change_pwd_authenticate);
        buttonChangePwd=findViewById(R.id.button_change_pwd);

        //disable edittext for new,confirm password and make change password button disable until user is authenticated
        editTextPwdCur.setEnabled(true);
        editTextPwdNew.setEnabled(false);
        editTextPwdConfirm.setEnabled(false);

        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();
        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordActivity.this, "user not found", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ChangePasswordActivity.this,ProfileFragment.class);
            startActivity(intent);
            finish();
        }else {
            reAuthenticateUser(firebaseUser);
        }

    }

    //reauthenticate user
    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonReAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwdCur=editTextPwdCur.getText().toString();
                if (TextUtils.isEmpty(userPwdCur)){
                    Toast.makeText(ChangePasswordActivity.this, "password is needed", Toast.LENGTH_SHORT).show();
                    editTextPwdCur.setError("please enter your password");
                    editTextPwdCur.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    //reauthenticate user
                    AuthCredential credential= EmailAuthProvider.getCredential(firebaseUser.getEmail(),userPwdCur);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                //disable edittext for current password,enable edittext for new and confirm password
                                editTextPwdCur.setEnabled(false);
                                editTextPwdNew.setEnabled(true);
                                editTextPwdConfirm.setEnabled(true);

                                //enable change password button,disable reauthenticate
                                buttonChangePwd.setEnabled(true);
                                buttonReAuthenticate.setEnabled(false);
                                textViewAuthenticated.setText("Now you can change your password");
                                Toast.makeText(ChangePasswordActivity.this, "change your password", Toast.LENGTH_SHORT).show();

                                buttonChangePwd.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordActivity.this,R.color.green));

                                buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        changePwd(firebaseUser);
                                    }
                                });
                            }else {
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void changePwd(FirebaseUser firebaseUser) {
        String userPwdNew=editTextPwdNew.getText().toString();
        String userPwdConfirm=editTextPwdConfirm.getText().toString();
        if (TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(ChangePasswordActivity.this, "New password is required", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("please enter new password");
            editTextPwdNew.requestFocus();
        } else if (TextUtils.isEmpty(userPwdConfirm)) {
            Toast.makeText(ChangePasswordActivity.this, "please confirm new password", Toast.LENGTH_SHORT).show();
            editTextPwdConfirm.setError("please confirm new password");
            editTextPwdConfirm.requestFocus();
        } else if (!userPwdNew.matches(userPwdConfirm)) {
            Toast.makeText(ChangePasswordActivity.this, "password not matched", Toast.LENGTH_SHORT).show();
            editTextPwdConfirm.setError("please enter same password");
            editTextPwdConfirm.requestFocus();
        } else if (userPwdCur.matches(userPwdNew)) {
            Toast.makeText(ChangePasswordActivity.this, "New password is required", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("please enter new password");
            editTextPwdNew.requestFocus();
        }else {
            progressBar.setVisibility(View.VISIBLE);

            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChangePasswordActivity.this, "password has been changed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ChangePasswordActivity.this,ProfileFragment.class);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(ChangePasswordActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}