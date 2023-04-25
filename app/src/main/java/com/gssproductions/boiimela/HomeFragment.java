package com.gssproductions.boiimela;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    DatabaseReference dbRef;
    BookDataAdapter bookDataAdapter;
    ArrayList<BookData> bookData;

    Toolbar toolbar;

    private MenuItem menuItem;
    private SearchView searchView;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        dbRef = FirebaseDatabase.getInstance().getReference("bookData");

        bookData = new ArrayList<>();
        bookDataAdapter = new BookDataAdapter(getContext(), bookData);


//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot users : snapshot.getChildren()){
//                    for(DataSnapshot bookTitles : users.getChildren()){
//                        BookData ob = bookTitles.getValue(BookData.class);
//                        Log.d("ob#bookData", "Title - " + ob.getTitle());
//                        bookData.add(ob);
//                    }
//                }
//                bookDataAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        FirebaseDatabase.getInstance().getReference("bookData").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    for (DataSnapshot bookTitles : users.getChildren()) {
                        BookData ob = bookTitles.getValue(BookData.class);
                        Log.d("ob#bookData", "Title - " + ob.getTitle());
                        bookData.add(ob);
                    }

                }
                bookDataAdapter.notifyDataSetChanged();
            }

        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

//        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchQuery(query);
                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchQuery(String query) {

        ArrayList<BookData> searchedBooks = new ArrayList<>();

        for(BookData ob : bookData){
            if(ob.getTitle().toLowerCase().contains(query.toLowerCase())
                || ob.getAuthorName().toLowerCase().contains(query.toLowerCase())){
                searchedBooks.add(ob);
            }
        }

        BookDataAdapter adapter2 = new BookDataAdapter(getContext(), searchedBooks);
        recyclerView.setAdapter(adapter2);

    }

    @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_home, container, false);

                toolbar = view.findViewById(R.id.toolbar);

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setTitle("BoiiMela");


                recyclerView = view.findViewById(R.id.recycler_View);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(bookDataAdapter);



                return view;
            }


}