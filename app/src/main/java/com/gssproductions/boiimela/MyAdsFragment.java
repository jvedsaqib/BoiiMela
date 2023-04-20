package com.gssproductions.boiimela;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdsFragment extends Fragment {

    FloatingActionButton fab_upload;

    RecyclerView myAdsRecycler;

    MyAdsAdapter myAdsAdapter;

    ArrayList<BookData> bookData;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MyAdsFragment() {
        // Required empty public constructor
    }

    public static MyAdsFragment newInstance(String param1, String param2) {
        MyAdsFragment fragment = new MyAdsFragment();
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
        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        bookData = new ArrayList<>();
        myAdsAdapter = new MyAdsAdapter(getContext(), bookData);

        FirebaseDatabase.getInstance().getReference("bookData/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {


                    for (DataSnapshot bookTitles : dataSnapshot.getChildren()) {
                        BookData ob = bookTitles.getValue(BookData.class);
                        Log.d("ob#bookData", "Title - " + ob.getTitle());
                        bookData.add(ob);
                    }


                myAdsAdapter.notifyDataSetChanged();
            }

        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_ads, container, false);

        fab_upload = view.findViewById(R.id.fab_upload);

        fab_upload.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                startActivity(new Intent(getActivity(), UploadActivity.class));
                getActivity().finish();
            }
            else{
                Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_LONG).show();
            }

        });


        myAdsRecycler = view.findViewById(R.id.ad_recycler);
        myAdsRecycler.setHasFixedSize(true);
        myAdsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myAdsRecycler.setAdapter(myAdsAdapter);


        return view;
    }
}