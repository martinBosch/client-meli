package com.martinb.meli.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.martinb.meli.R;
import com.martinb.meli.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMsgRecyclerViewAdapter extends RecyclerView.Adapter<ChatMsgViewHolder> {

    private List<ChatMessage> chatMessages = new ArrayList<>();
    private Context context;

    public ChatMsgRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ChatMsgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_message, null);
        return new ChatMsgViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMsgViewHolder holder, int i) {
        ChatMessage msg = chatMessages.get(i);

        holder.username.setText(msg.getUsername());
        holder.message.setText(msg.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void clearMessages() {
        this.chatMessages.clear();
    }

    public void addChatMsg(ChatMessage chatMsg) {
        this.chatMessages.add(chatMsg);
    }
}
