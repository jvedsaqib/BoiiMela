package com.gssproductions.boiimela;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class WelcomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private final int[] fictionImg = {R.drawable.fiction2, R.drawable.fiction3};

    private final int[] academicsImg = {R.drawable.academics, R.drawable.academics2};
    private final int[] novelImg = {R.drawable.novel, R.drawable.novel2};
    private final int[] bioImg = {R.drawable.bio1, R.drawable.bio2};

    private final String[] quotesArr = {"A reader lives a thousand lives before he dies . . . The man who never reads lives only one. - George R.R. Martin (American novelist and short-story writer, screenwriter, and television producer.)",
                                        "Until I feared I would lose it, I never loved to read. One does not love breathing. - Harper Lee (American novelist is best known for her 1960 novel To Kill a Mockingbird)",
                                        "You can never get a cup of tea large enough or a book long enough to suit me. - C.S. Lewis (British writer and lay theologian)",
                                        "I find television very educating. Every time somebody turns on the set, I go into the other room and read a book. - Groucho Marx (American comedian, actor, writer, stage, film, radio, and television star)",
                                        "So please, oh please, we beg, we pray, go throw your TV set away, and in its place you can install a lovely bookshelf on the wall. - Roald Dahl (Roald Dahl was a British novelist, short-story writer, poet, screenwriter, and wartime fighter pilot.)",
                                        "The more that you read, the more things you will know. The more that you learn, the more places you’ll go. - Dr. Seuss (American children’s author, political cartoonist, illustrator, poet, animator, and filmmaker)"};


    MaterialCardView category_academics_card, category_StoryBook_card,
            category_novel_card, category_Fiction_card,
            category_Journal_card, category_Biography_card;

    TextView user_name_tv, tv_banner;

    ImageView fiction_iv, academics_iv,
            novel_iv, biography_iv;

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

        fiction_iv = view.findViewById(R.id.fiction_iv);
        academics_iv = view.findViewById(R.id.academics_iv);
        novel_iv = view.findViewById(R.id.novel_iv);
        biography_iv = view.findViewById(R.id.biography_iv);

        tv_banner = view.findViewById(R.id.tv_banner);
        Random r = new Random();
        int i1 = r.nextInt(quotesArr.length);
        tv_banner.setText(quotesArr[i1]);
        tv_banner.setSelected(true);


        Handler fictionHandler = new Handler();
        Handler academicsHandler = new Handler();
        Handler novelHandler = new Handler();
        Handler bioHandler = new Handler();
        fictionHandler.postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {
                fiction_iv.setImageResource(fictionImg[i]);
                i++;
                if(i>fictionImg.length-1)
                {
                    i=0;
                }
                fictionHandler.postDelayed(this, 5000);
            }
        }, 2000);

        academicsHandler.postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {
                academics_iv.setImageResource(academicsImg[i]);
                i++;
                if(i>academicsImg.length-1)
                {
                    i=0;
                }
                academicsHandler.postDelayed(this, 5000);
            }
        }, 4000);

        novelHandler.postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {
                novel_iv.setImageResource(novelImg[i]);
                i++;
                if(i>novelImg.length-1)
                {
                    i=0;
                }
                novelHandler.postDelayed(this, 5000);
            }
        }, 6000);

        bioHandler.postDelayed(new Runnable() {
            int i=0;
            @Override
            public void run() {
                biography_iv.setImageResource(bioImg[i]);
                i++;
                if(i>bioImg.length-1)
                {
                    i=0;
                }
                bioHandler.postDelayed(this, 5000);
            }
        }, 8000);

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

        category_Journal_card = view.findViewById(R.id.category_manga_card);
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