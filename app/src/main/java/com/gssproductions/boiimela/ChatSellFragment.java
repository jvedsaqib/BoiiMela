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
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatSellFragment extends Fragment {

    // --------------

    RecyclerView chat_recycler;

    DatabaseReference dbRef;

    ChatSellAdapter chatSellAdapter;

    ArrayList<ChatSell> chatList;

    String book_price = "";
    String bookTitle = "";

    Boolean flag = false;

    TextView tv_no_chat_msg;


    // --------------

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChatSellFragment() {
        // Required empty public constructor
    }


    public static ChatSellFragment newInstance(String param1, String param2) {
        ChatSellFragment fragment = new ChatSellFragment();
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

        chatSellAdapter = new ChatSellAdapter(getContext(), chatList);

        Log.d("db", "Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/sell");

        FirebaseDatabase
                .getInstance()
                .getReference("Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/sell")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot users : dataSnapshot.getChildren()) {
                            Log.d("chat-user", users.getKey().toString());
                            //users.getKey().toString().substring(28);

                            for(DataSnapshot book_title : users.getChildren()){
                                Log.d("book_title", book_title.getKey().toString());

                                String CHILD = "bookData/"+users.getKey().toString().substring(0, 28)+"/"+book_title.getKey().toString()+"/price";


                                bookTitle = book_title.getKey().toString();

                                Log.d("book_price_now", book_price);
                                chatList.add(new ChatSell(bookTitle,
                                        users.getKey().toString().substring(0, 28),
                                        book_price));

                            }
                        }
                        chatSellAdapter.notifyDataSetChanged();
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
        chat_recycler.setAdapter(chatSellAdapter);



        return view;
    }
}