package com.gssproductions.boiimela;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.myAdsViewHolder> {


    Context context;

    ArrayList<BookData> list;

    int totalAds;

    public int getTotalAds() {
        return totalAds;
    }

    public MyAdsAdapter(Context context, ArrayList<BookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdsAdapter.myAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_ads_card, parent, false);

        return new MyAdsAdapter.myAdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdsAdapter.myAdsViewHolder holder, int position) {
        BookData bookData = list.get(position);

        if(bookData.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.book_ad_title.setText(bookData.getTitle());
            holder.book_ad_price.setText(bookData.getPrice());

            if (bookData.getImgUrl0().isEmpty()) {
                holder.book_ad_thumbnail.setImageResource(R.drawable.icon_upload);
            } else{
                Glide.with(context).load(bookData.getImgUrl0()).into(holder.book_ad_thumbnail);
            }

            holder.myAdsCard.setOnClickListener(v -> {
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, new BookFragment(bookData))
                        .addToBackStack(null).commit();
            });

            holder.myAdsCard.setOnLongClickListener(v -> {
                DeleteDialogAd deleteDialog = new DeleteDialogAd("bookData/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+bookData.getTitle(),
                        "AD");
                deleteDialog.show(((FragmentActivity)context).getSupportFragmentManager(), "Dialog");

                return true;
            });

        }

    }

    @Override
    public int getItemCount() {

        totalAds = list.size();
        return list.size();
    }

    public static class myAdsViewHolder extends RecyclerView.ViewHolder{

        TextView book_ad_title, book_ad_price;

        ImageView book_ad_thumbnail;

        CardView myAdsCard;

        public myAdsViewHolder(@NonNull View itemView) {
            super(itemView);

            book_ad_title = itemView.findViewById(R.id.book_ad_title);
            book_ad_price = itemView.findViewById(R.id.book_ad_price);

            book_ad_thumbnail = itemView.findViewById(R.id.book_ad_thumbnail);

            myAdsCard = itemView.findViewById(R.id.myAdsCard);

        }
    }
}
