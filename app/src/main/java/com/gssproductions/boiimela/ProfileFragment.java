package com.gssproductions.boiimela;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

   TextView textViewWelcome,textViewFullName,textViewEmail,textViewDoB,textViewGender,textViewmobile;
    ProgressBar progressBar;
    String fullName,email,mobile,gender,doB;
    ImageView imageView;
    FirebaseAuth authProfile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // --------------
       /* authProfile =  FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
                Toast.makeText(UserProfileActivity.this,"something went wrong user details are not right",Toast.LENGTH_LONG).show();

           }else {
                   progressBar.setVisibility(View.VISIBLE);
                   showUserProfile(firebaseUser);
           }
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_ads, container, false);

        textViewWelcome = view.findViewById(R.id.TextView_show_welcome);
        textViewFullName= view.findViewById(R.id.textView_show_full_name);
        textViewEmail= view.findViewById(R.id.textView_show_email);
        textViewDoB= view.findViewById(R.id.textView_show_dob);
        textViewGender = view.findViewById(R.id.textView_show_gender);
        textViewmobile = view.findViewById(R.id.textView_show_mobile);
        progressBar = view.findViewById(R.id.progressbar_profile);
        return view;

        /* private void showUserProfile (FirebaseUser firebaseUser){
            String userID = firebaseUser.getUid();
            //Extracting user reference from database for " Registered users"

            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference(Registered user);
            referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        ReaderWriterUserDetails readUserDetails = snapshot.getValue(ReaderWriterUserDetails.class);
                        if( readUserDetails != null){
                        fillName = firebaseUser.getDisplayName();
                        email = firebaseUser.getEmail();
                        dob =  readUserDetails.dob;
                        gender =  readUserDetails.gender;
                        mobile =  readUserDetails.mobile;

                        textViewWelcome.setText("Welcome, " + fullName + "!");
                        textViewFullName.setText(fullName);
                        textViewEmail.setText(email);
                        textViewGender.setText(gender);
                        textViewDoB.setText(doB);
                        text.ViewMobile.setText(mobile);

                    }
                    progressBar.setVisibility(View.GONE);
                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error){
                         Toast.makeText(UserProfileActivity.this,"something went wrong!",Toast.LENGTH_LONG).show();
                    }
                  });
     */
    }
}