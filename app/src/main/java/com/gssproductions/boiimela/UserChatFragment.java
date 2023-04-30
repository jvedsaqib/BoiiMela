package com.gssproductions.boiimela;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserChatFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    // Button
    FloatingActionButton sendFab;

    // EditText
    EditText inputMsg;

    // Firebase
    FirebaseListAdapter<ChatMessage> adapter;

    BookData sender;

    ListView msgList;

    Context context;

    String BUY_CHAT_DB_LOC = "";
    String SELL_CHAT_DB_LOC = "";

    String BUY_PATH, SELL_PATH, MODE, message="";

    public UserChatFragment() {
        // Required empty public constructor
    }

    public UserChatFragment(BookData seller, String message){
        this.sender = seller;
        this.BUY_PATH = "Chat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/"+seller.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+seller.getSeller_name()+"/"+seller.getTitle();
        // BUY_PATH_NAME = "Chat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/+seller.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+
        this.SELL_PATH = "Chat/"+seller.getUid()+"/sell/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+seller.getUid()+"/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+seller.getTitle();
        this.MODE = "BUY";
        this.message = message;
    }

    public UserChatFragment(BookData seller){
        this.sender = seller;
        this.BUY_PATH = "Chat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/"+seller.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+seller.getSeller_name()+"/"+seller.getTitle();
        // BUY_PATH_NAME = "Chat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/+seller.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+
        this.SELL_PATH = "Chat/"+seller.getUid()+"/sell/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+seller.getUid()+"/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+seller.getTitle();
        this.MODE = "BUY";
    }

    public UserChatFragment(String BUY_PATH, String SELL_PATH, String MODE){
        this.BUY_PATH = BUY_PATH;
        this.SELL_PATH = SELL_PATH;
        this.MODE = MODE;
    }

    public static UserChatFragment newInstance(String param1, String param2) {
        UserChatFragment fragment = new UserChatFragment();
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

        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);
        msgList = (ListView) view.findViewById(R.id.msgList);
        sendFab = view.findViewById(R.id.sendFab);



        if(MODE.equals("BUY")){
            displayChatMessages(BUY_PATH);
        }
        else if(MODE.equals("SELL")){
            displayChatMessages(SELL_PATH);
        }

        BUY_CHAT_DB_LOC = BUY_PATH;
        SELL_CHAT_DB_LOC = SELL_PATH;

//        BUY_CHAT_DB_LOC = "Chat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/"+sender.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+sender.getTitle();
//
//        SELL_CHAT_DB_LOC = "Chat/"+sender.uid+"/sell/"+sender.getUid()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+sender.getTitle();

        if(!message.equals("")){
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(BUY_CHAT_DB_LOC)
                    .push()
                    .setValue(
                            new ChatMessage(message,
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                            ));

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(SELL_CHAT_DB_LOC)
                    .push()
                    .setValue(
                            new ChatMessage(message,
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                            ));
        }

        sendFab.setOnClickListener(v -> {
            inputMsg = view.findViewById(R.id.inputMsg);

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(BUY_CHAT_DB_LOC)
                    .push()
                    .setValue(
                    new ChatMessage(inputMsg.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                    ));

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(SELL_CHAT_DB_LOC)
                    .push()
                    .setValue(
                    new ChatMessage(inputMsg.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                    ));

            inputMsg.setText("");
        });


        return view;
    }

    private void displayChatMessages(String PATH) {

        adapter = new FirebaseListAdapter<ChatMessage>((AppCompatActivity) context,
                ChatMessage.class,
                R.layout.message,
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(PATH)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView tvUsername = v.findViewById(R.id.tvUsername);
                TextView tvMessage = v.findViewById(R.id.tvMessage);
                TextView tvTime = v.findViewById(R.id.tvTime);

                FirebaseDatabase.getInstance().getReference().child(PATH)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //tvUsername.setText(snapshot.getValue().toString());
                        tvUsername.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(model.getMessageUserUid().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
                    tvMessage.setTextColor(Color.BLUE);
                    tvMessage.setGravity(Gravity.END);

                }
                // Set their text
                tvMessage.setText(model.getMessageText());

                // Format the date before showing it
                tvTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                        model.getMessageTime()));

            }
        };
        msgList.setAdapter(adapter);
    }

}