package com.gssproductions.boiimela;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookDataAdapter extends RecyclerView.Adapter<BookDataAdapter.bookDataViewHolder> {

    Context context;

    ArrayList<BookData> list;


    public BookDataAdapter(Context context, ArrayList<BookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public bookDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);

        return new bookDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookDataViewHolder holder, int position) {
        BookData bookData = list.get(position);

        // bookData.setSeller_name(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        holder.book_title.setText(bookData.getTitle());
        holder.book_author.setText(bookData.getAuthorName());
        holder.book_price.setText(bookData.getPrice());

//        Picasso.get().load(bookData.getImgUrl0()).into(holder.book_thumbnail);

        if (bookData.getImgUrl0().isEmpty()) {
            holder.book_thumbnail.setImageResource(R.drawable.icon_upload);
        } else{
            Glide.with(context).load(bookData.getImgUrl0()).into(holder.book_thumbnail);
//            Picasso.get().load(bookData.getImgUrl0()).resize(256, 256).into(holder.book_thumbnail);
        }

        holder.bookDataCardView.setOnClickListener(v -> {
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new BookFragment(bookData))
                    .addToBackStack(null).commit();
        });

//        holder.book_thumbnail.setImageURI();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class bookDataViewHolder extends RecyclerView.ViewHolder{

        TextView book_title, book_author, book_price;
        ImageView book_thumbnail;

        CardView bookDataCardView;

        public bookDataViewHolder(@NonNull View itemView) {
            super(itemView);

            book_title = itemView.findViewById(R.id.book_title);
            book_author = itemView.findViewById(R.id.book_author);
            book_price = itemView.findViewById(R.id.book_price);

            book_thumbnail = itemView.findViewById(R.id.book_thumbnail);

            bookDataCardView = itemView.findViewById(R.id.bookDataCardView);

        }
    }

}
