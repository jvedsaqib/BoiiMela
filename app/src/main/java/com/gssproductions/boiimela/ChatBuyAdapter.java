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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatBuyAdapter extends RecyclerView.Adapter<ChatBuyAdapter.ChatBuyViewHolder> {

    Context context;

    ArrayList<ChatSell> list;

    public ChatBuyAdapter(Context context, ArrayList<ChatSell> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatBuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list, parent, false);

        return new ChatBuyAdapter.ChatBuyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBuyViewHolder holder, int position) {
        ChatSell ob = list.get(position);

        holder.sender_name.setText(ob.getSenderName());
        holder.book_ad_title.setText(ob.getBookTitle());
        holder.book_ad_price.setText(ob.getBookPrice());

        holder.chatListView.setOnClickListener(v -> {
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout,
                            new UserChatFragment("Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/"+ob.getSenderName().substring(0, 28)+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getBookTitle(),
                                    "Chat/"+ob.getSenderName().substring(0, 28)+"/sell/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+ob.getSenderName().substring(0, 28)+"/"+ob.getBookTitle(),
                                    "BUY"))
                    .addToBackStack(null).commit();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ChatBuyViewHolder extends RecyclerView.ViewHolder{

        TextView sender_name, book_ad_title, book_ad_price;

        CardView chatListView;

        public ChatBuyViewHolder(@NonNull View itemView) {
            super(itemView);

            sender_name = itemView.findViewById(R.id.sender_name);
            book_ad_title = itemView.findViewById(R.id.book_ad_title);
            book_ad_price = itemView.findViewById(R.id.book_ad_price);


            chatListView = itemView.findViewById(R.id.chatListView);

        }
    }


}


