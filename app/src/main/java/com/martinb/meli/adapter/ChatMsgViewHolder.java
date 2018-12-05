package com.martinb.meli.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.martinb.meli.R;

public class ChatMsgViewHolder extends RecyclerView.ViewHolder {

    public TextView username;
    public TextView message;

    public ChatMsgViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.message_username);
        message = itemView.findViewById(R.id.message_text);
    }
}
