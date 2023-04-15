package com.gssproductions.boiimela;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BookFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // Saqib's work :

    Context context;

    ViewPager imageViewPager;

    BookData ob;

    TextView book_title, book_price;

    String[] imgUrls;

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

        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_book, container, false);
    }


}