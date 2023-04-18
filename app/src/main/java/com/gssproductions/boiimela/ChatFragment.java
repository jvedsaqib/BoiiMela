package com.gssproductions.boiimela;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChatFragment extends Fragment {

    Button chat_buy_btn, chat_sell_btn;

    Context context;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }


    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        context = getContext();


        chat_buy_btn = view.findViewById(R.id.chat_buy_btn);
        chat_sell_btn = view.findViewById(R.id.chat_sell_btn);


        chat_sell_btn.setEnabled(false);

        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_layout, new ChatSellFragment())
                .addToBackStack(null).commit();

        chat_sell_btn.setOnClickListener(v -> {
            chat_buy_btn.setEnabled(true);
            chat_sell_btn.setEnabled(false);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chat_layout, new ChatSellFragment())
                    .addToBackStack(null).commit();
        });

        chat_buy_btn.setOnClickListener(v -> {
            chat_sell_btn.setEnabled(true);
            chat_buy_btn.setEnabled(false);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chat_layout, new ChatBuyFragment())
                    .addToBackStack(null).commit();
        });



        return view;
    }
}