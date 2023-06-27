package com.gssproductions.boiimela;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ChatFragment extends Fragment {

    TextView chat_buy_btn, chat_sell_btn;
    View sell_indicator, buy_indicator;

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
        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        context = getContext();


        chat_buy_btn = view.findViewById(R.id.chat_buy_btn);
        chat_sell_btn = view.findViewById(R.id.chat_sell_btn);

        sell_indicator = view.findViewById(R.id.sell_indicator);
        buy_indicator = view.findViewById(R.id.buy_indicator);


        buy_indicator.setVisibility(View.INVISIBLE);
        chat_sell_btn.setTextSize(30);

        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_layout, new ChatSellFragment())
                .addToBackStack(null).commit();

        chat_sell_btn.setOnClickListener(v -> {
            chat_sell_btn.setTextSize(30);
            chat_buy_btn.setTextSize(25);
            buy_indicator.setVisibility(View.INVISIBLE);
            sell_indicator.setVisibility(View.VISIBLE);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chat_layout, new ChatSellFragment())
                    .addToBackStack(null).commit();
        });

        chat_buy_btn.setOnClickListener(v -> {
            chat_buy_btn.setTextSize(30);
            chat_sell_btn.setTextSize(25);
            sell_indicator.setVisibility(View.INVISIBLE);
            buy_indicator.setVisibility(View.VISIBLE);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chat_layout, new ChatBuyFragment())
                    .addToBackStack(null).commit();
        });



        return view;
    }

}