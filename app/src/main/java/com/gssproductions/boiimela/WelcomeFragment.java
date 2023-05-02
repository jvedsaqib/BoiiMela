package com.gssproductions.boiimela;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    MaterialCardView category_academics_card, category_StoryBook_card,
            category_novel_card, category_Fiction_card,
            category_Journal_card, category_Biography_card;

    TextView user_name_tv;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        user_name_tv = view.findViewById(R.id.user_name_tv);
        user_name_tv.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        category_academics_card = view.findViewById(R.id.category_academics_card);
        category_academics_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Academics", true))
                    .addToBackStack(null).commit();
        });


        category_StoryBook_card = view.findViewById(R.id.category_StoryBook_card);
        category_StoryBook_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Story Book", true))
                    .addToBackStack(null).commit();
        });

        category_novel_card = view.findViewById(R.id.category_novel_card);
        category_novel_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Novel", true))
                    .addToBackStack(null).commit();
        });

        category_Fiction_card = view.findViewById(R.id.category_Fiction_card);
        category_Fiction_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Fiction", true))
                    .addToBackStack(null).commit();
        });

        category_Journal_card = view.findViewById(R.id.category_Journal_card);
        category_Journal_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Journal", true))
                    .addToBackStack(null).commit();
        });

        category_Biography_card = view.findViewById(R.id.category_Biography_card);
        category_Biography_card.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment("Biography", true))
                    .addToBackStack(null).commit();
        });


        return view;

    }
}