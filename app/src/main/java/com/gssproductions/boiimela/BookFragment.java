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
import android.widget.EditText;
import android.widget.RelativeLayout;
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
            cover_type, tv_bookCondition_data,
            tv_bookCategory_data, tv_bookOtherCategory_data,
            tv_bookUserDesc_data, tv_sellerName_data,
            tv_sellerAddress_data, tv_sellerEmail_data,
            tv_sellerNumber_data, tv_sellerNumber_ask,
            tv_bookTitle_data, tv_bookAuthor_data,
            tv_bookPublisher_data;

    EditText et_offer_price;
    RelativeLayout offer_layout;


    String[] imgUrls;

    Button user_chat_btn, makeOffer_btn,
            negotiate_btn;

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

        offer_layout = view.findViewById(R.id.offer_layout);
        offer_layout.setEnabled(false);
        offer_layout.setVisibility(View.INVISIBLE);

        imageViewPager = view.findViewById(R.id.imageViewPager);
        imageViewPager.setAdapter(adapter);

        book_title = view.findViewById(R.id.book_title);
        book_title.setText(ob.getTitle());

        book_price = view.findViewById(R.id.book_price);
        book_price.setText(ob.getPrice());

        cover_type = view.findViewById(R.id.cover_type);
        cover_type.setText(ob.getCoverType());

        tv_bookCondition_data = view.findViewById(R.id.tv_bookCondition_data);
        tv_bookCondition_data.setText(ob.getCondition());

        tv_bookCategory_data = view.findViewById(R.id.tv_bookCategory_data);
        tv_bookCategory_data.setText(ob.getCategory());

        tv_bookOtherCategory_data = view.findViewById(R.id.tv_bookOtherCategory_data);
        if(ob.getOtherCategory().equals("")){
            tv_bookOtherCategory_data.setVisibility(View.INVISIBLE);
        }else{
            tv_bookOtherCategory_data.setText(ob.getOtherCategory());
        }

        tv_bookUserDesc_data = view.findViewById(R.id.tv_bookUserDesc_data);
        if(ob.getDescription().equals("")){
            tv_bookUserDesc_data.setVisibility(View.INVISIBLE);
        }else{
            tv_bookUserDesc_data.setText(ob.getDescription());
        }

        // ---------------- book details view ----------------

        tv_bookTitle_data = view.findViewById(R.id.tv_bookTitle_data);
        tv_bookTitle_data.setText(ob.getTitle());

        tv_bookAuthor_data = view.findViewById(R.id.tv_bookAuthor_data);
        tv_bookAuthor_data.setText(ob.getAuthorName());

        tv_bookPublisher_data = view.findViewById(R.id.tv_bookPublisher_data);
        tv_bookPublisher_data.setText(ob.getPublisherName());


        // ---------------- seller details view ---------------

        tv_sellerName_data = view.findViewById(R.id.tv_sellerName_data);
        tv_sellerName_data.setText(ob.getSeller_name());

        tv_sellerAddress_data = view.findViewById(R.id.tv_sellerAddress_data);
        tv_sellerAddress_data.setText(ob.getAddress());

//        tv_sellerEmail_data = view.findViewById(R.id.tv_sellerEmail_data);
//        tv_sellerEmail_data.setText("ob.getEmail()");

        tv_sellerNumber_data = view.findViewById(R.id.tv_sellerNumber_data);
        String numbCat = "+91 XXXXXXX"+ob.getPhoneNumber().substring(ob.getPhoneNumber().length() - 3);
        tv_sellerNumber_data.setText(numbCat);

        tv_sellerNumber_ask = view.findViewById(R.id.tv_sellerNumber_ask);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(ob.getUid())){
            tv_sellerNumber_ask.setVisibility(View.INVISIBLE);
        }
        tv_sellerNumber_ask.setOnClickListener(v -> {
            context = getContext();
            String message = "Hey Papi send me your number !";
            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, new UserChatFragment(ob, message))
                        .commit();
            }
            else{
                Toast.makeText(context, "Please verify your email first", Toast.LENGTH_SHORT).show();
            }

        });


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

        makeOffer_btn = (Button) view.findViewById(R.id.makeOffer_btn);
        if(ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            makeOffer_btn.setEnabled(false);
        }



        makeOffer_btn.setOnClickListener(v -> {
            offer_layout.setEnabled(true);
            offer_layout.setVisibility(View.VISIBLE);
            et_offer_price = view.findViewById(R.id.et_offer_price);
            et_offer_price.setText(ob.getPrice());

            negotiate_btn = (Button) view.findViewById(R.id.negotiate_btn);
            negotiate_btn.setOnClickListener(vi -> {
                context = getContext();

                String message = "Hey Papi, can I get it @ Rs."+et_offer_price.getText().toString();

                if(Double.parseDouble(et_offer_price.getText().toString()) < Double.parseDouble(ob.getPrice().toString()) / 2){
                    et_offer_price.setError("Negotiation price must be between " + Double.parseDouble(ob.getPrice().toString()) / 2 + " to " + Double.parseDouble(ob.getPrice().toString()));
                }else{
                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                        AppCompatActivity activity = (AppCompatActivity) context;
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_layout, new UserChatFragment(ob, message))
                                .commit();
                    }
                    else{
                        Toast.makeText(context, "Please verify your email first", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        });


        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_book, container, false);
    }


}