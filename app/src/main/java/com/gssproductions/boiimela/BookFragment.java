package com.gssproductions.boiimela;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class BookFragment extends Fragment implements Serializable {

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
            tv_bookPublisher_data, tv_adUID, adReport;

    EditText et_offer_price;
    RelativeLayout offer_layout;

    ImageView iv_ad_map;

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

        imageViewPager = view.findViewById(R.id.imageViewPage);
        imageViewPager.setAdapter(adapter);

        book_title = view.findViewById(R.id.book_title);
        book_title.setText(ob.getTitle());

        iv_ad_map = view.findViewById(R.id.iv_ad_map);
        iv_ad_map.setOnClickListener(v -> {
            String strUri = "http://maps.google.com/maps?q=loc:" + ob.getLatitude() + "," + ob.getLongitude() + " (" + ob.getTitle() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));

            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

            startActivity(intent);
        });

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
            String message = "Hey, I am interested in this ad, please send me your number for further discussion.";
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
        if(ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && !ob.getSold()){
            user_chat_btn.setText("Edit Ad");
            user_chat_btn.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                intent.putExtra("Object", ob);
                intent.putExtra("EDIT", true);
                startActivity(intent);
            });
        } else if (ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && ob.getSold()) {
            user_chat_btn.setText("Edit Ad");
            user_chat_btn.setEnabled(false);
        } else{
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
        }


        makeOffer_btn = (Button) view.findViewById(R.id.makeOffer_btn);
        if(ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && !ob.getSold()){
            context = getContext();
            makeOffer_btn.setText("Sold");
            makeOffer_btn.setOnClickListener(v -> {
                ob.setSold(true);
                FirebaseDatabase.getInstance().getReference("bookData/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getTitle()+"/sold").setValue(true);
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, new HomeFragment())
                        .commit();
            });
        }else if (ob.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && ob.getSold()) {
            makeOffer_btn.setText("Sold");
            makeOffer_btn.setEnabled(false);
        } else{
            makeOffer_btn.setOnClickListener(v -> {
                offer_layout.setEnabled(true);
                offer_layout.setVisibility(View.VISIBLE);
                et_offer_price = view.findViewById(R.id.et_offer_price);
                et_offer_price.setText(ob.getPrice());

                negotiate_btn = (Button) view.findViewById(R.id.negotiate_btn);
                negotiate_btn.setOnClickListener(vi -> {
                    context = getContext();

                    String message = "Hey, can I get it @ Rs."+et_offer_price.getText().toString();

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
        }

        tv_adUID = view.findViewById(R.id.tv_adUID);
        tv_adUID.setText(ob.getAdUID());

        adReport = view.findViewById(R.id.adReport);
        adReport.setOnClickListener(v -> {
            System.out.println("Ad Report for - " +  ob.getAdUID());
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", ob.getAdUID());
            if (clipboard == null || clip == null) return;
            clipboard.setPrimaryClip(clip);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/Me69bDVHNr9ZJnL49"));
            startActivity(browserIntent);
        });


        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_book, container, false);
    }


}