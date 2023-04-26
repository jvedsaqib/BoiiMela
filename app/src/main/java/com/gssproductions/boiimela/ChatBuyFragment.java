package com.gssproductions.boiimela;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatBuyFragment extends Fragment {

    // --------------

    RecyclerView chat_recycler;

    DatabaseReference dbRef;

    ChatBuyAdapter chatBuyAdapter;

    ArrayList<ChatSell> chatList;

    String book_price = "";
    String bookTitle = "";

    Boolean flag = false;
    String name = "";


    // --------------

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChatBuyFragment() {
        // Required empty public constructor
    }

    public static ChatBuyFragment newInstance(String param1, String param2) {
        ChatBuyFragment fragment = new ChatBuyFragment();
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

        chatList = new ArrayList<>();

        chatBuyAdapter = new ChatBuyAdapter(getContext(), chatList);

        Log.d("db", "Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy");

        FirebaseDatabase
                .getInstance()
                .getReference("Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot users : snapshot.getChildren()) {
                            // Log.d("chat-user", users.getKey());

                            for(DataSnapshot book_title : users.getChildren()){
                                // Log.d("book_title", book_title.getKey().toString());

                                bookTitle = book_title.getKey();

                                for(DataSnapshot book_details : book_title.getChildren()){
                                    // Log.d("book_dets", book_details.getKey());

                                    chatList.add(new ChatSell(bookTitle,
                                            book_details.getKey(),
                                            book_price,
                                            FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),
                                            users.getKey().toString().substring(0, 28)));
                                }

                            }
                        }
                        chatBuyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_sell, container, false);

        chat_recycler = view.findViewById(R.id.chat_recycler);
        chat_recycler.setHasFixedSize(true);
        chat_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chat_recycler.setAdapter(chatBuyAdapter);



        return view;
    }
}