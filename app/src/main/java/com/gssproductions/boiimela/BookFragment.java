package com.gssproductions.boiimela;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BookFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // Saqib's work :

    Context context;

    ViewPager imageViewPager;

    BookData ob;

    TextView book_title, book_price,
            cover_type, book_desc;

    String[] imgUrls;

    Button user_chat_btn;

    public BookFragment(BookData ob){
        this.ob = ob;
    }

    public BookFragment() {
        // Required empty public constructor
    }


    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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

        imgUrls = new String[3];

        imgUrls[0] = ob.getImgUrl0();
        imgUrls[1] = ob.getImgUrl1();
        imgUrls[2] = ob.getImgUrl2();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        ViewPageAdapter adapter = new ViewPageAdapter(getContext(), imgUrls);

        imageViewPager = view.findViewById(R.id.imageViewPager);
        imageViewPager.setAdapter(adapter);

        book_title = view.findViewById(R.id.book_title);
        book_title.setText(ob.getTitle());

        book_price = view.findViewById(R.id.book_price);
        book_price.setText(ob.getPrice());

        cover_type = view.findViewById(R.id.cover_type);
        cover_type.setText(ob.getCoverType());

        book_desc = view.findViewById(R.id.book_desc);
        book_desc.setText(ob.getDescription());

        user_chat_btn = (Button) view.findViewById(R.id.user_chat_btn);
        if(ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            user_chat_btn.setEnabled(false);
        }
        user_chat_btn.setOnClickListener(v -> {
            Log.d("chat-sender", ob.getUid() + "  -  " + FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

            context = getContext();

            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, new UserChatFragment(ob))
                        .commit();
            }
            else{
                Toast.makeText(context, "Please verify your email first", Toast.LENGTH_SHORT).show();
            }

        });


        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_book, container, false);
    }


}