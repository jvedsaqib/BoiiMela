package com.gssproductions.boiimela;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteDialog extends AppCompatDialogFragment {

    String path, MODE;

    DeleteDialog(String path, String MODE){
        this.path = path;
        this.MODE = MODE;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Chat")
                .setCancelable(true)
                .setMessage("Do you want to delete chat?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Message DELETED", Toast.LENGTH_LONG).show();
                        deleteChat(path);
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        if(MODE.equals("SELL")){
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.chat_layout, new ChatSellFragment())
                                    .addToBackStack(null).commit();
                        }
                        else if(MODE.equals("BUY")){
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.chat_layout, new ChatBuyFragment())
                                    .addToBackStack(null).commit();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public void deleteChat(String path){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(path);
        dbRef.removeValue();
    }
}
