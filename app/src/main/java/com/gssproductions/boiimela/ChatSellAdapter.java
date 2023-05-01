package com.gssproductions.boiimela;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatSellAdapter extends RecyclerView.Adapter<ChatSellAdapter.ChatSellViewHolder> {

    Context context;

    ArrayList<ChatSell> list;

    Toolbar toolbar;

    public ChatSellAdapter(Context context, ArrayList<ChatSell> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatSellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list, parent, false);

        return new ChatSellAdapter.ChatSellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatSellViewHolder holder, int position) {
        ChatSell ob = list.get(position);

        holder.sender_name.setText(ob.getSenderName());
        holder.book_ad_title.setText(ob.getBookTitle());
        holder.book_ad_price.setText(ob.getBookPrice());

        holder.chatListView.setOnClickListener(v -> {
//            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) context;

//            Log.d("BUY", "Chat/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/buy/"+ob.getSenderName().substring(0, 28)+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getBookTitle());
//            Log.d("SELL", "Chat/"+ob.getSenderName().substring(0, 28)+"/sell/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+ob.getSenderName().substring(0, 28)+"/"+ob.getBookTitle());


            Log.d("sellerUID + buyerUID", ob.getSellerUID() + " + " + ob.getBuyerUID());

            String disName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().substring(0, FirebaseAuth.getInstance().getCurrentUser().getDisplayName().length() - 1);

            Log.d("SELL_PATH", "Chat/"+ob.getSellerUID().substring(0, 28)+"/sell/"+ob.getBuyerUID()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getBookTitle()+"/"+ob.getSenderName());
            Log.d("BUY_PATH", "Chat/"+ ob.getBuyerUID()+"/buy/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+ob.getSellerUID()+"/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+ob.getSenderName());

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout,
                            new UserChatFragment("Chat/"+ ob.getBuyerUID()+"/buy/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"-"+ob.getBuyerUID()+"/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+ob.getSenderName(),
                                    "Chat/"+ob.getSellerUID().substring(0, 28)+"/sell/"+ob.getBuyerUID()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getBookTitle()+"/"+ob.getSenderName(),
                                    "SELL"))
                    .addToBackStack(null).commit();
        });

        holder.chatListView.setOnLongClickListener(longlistener -> {
            DeleteDialog deleteDialog = new DeleteDialog("Chat/"+ob.getSellerUID().substring(0, 28)+"/sell/"+ob.getBuyerUID()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ob.getBookTitle()+"/"+ob.getSenderName(),
                    "SELL");
            deleteDialog.show(((FragmentActivity)context).getSupportFragmentManager(), "Dialog");

            return true;
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ChatSellViewHolder extends RecyclerView.ViewHolder{

        TextView sender_name, book_ad_title, book_ad_price;

        CardView chatListView;

        public ChatSellViewHolder(@NonNull View itemView) {
            super(itemView);

            sender_name = itemView.findViewById(R.id.sender_name);
            book_ad_title = itemView.findViewById(R.id.book_ad_title);
            book_ad_price = itemView.findViewById(R.id.book_ad_price);


            chatListView = itemView.findViewById(R.id.chatListView);



        }
    }


}


