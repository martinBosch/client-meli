package com.martinb.meli.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martinb.meli.R;
import com.martinb.meli.adapter.ChatMsgRecyclerViewAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.ChatMessage;

import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

public class ChatActivity extends AppCompatActivity {

    private Context context = this;
    private ChatMsgRecyclerViewAdapter chatAdapter;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setupToolbar();
        setupChatMessages();
        setupConnection();
        setupMsgInput();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar15);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupChatMessages() {
        RecyclerView chat = findViewById(R.id.chat_messages);
        chat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatMsgRecyclerViewAdapter(this);
        chat.setAdapter(chatAdapter);
    }

    private void setupConnection() {
        //Todo: Aca deberia ir el id de la compra
        String DATABASE_NAME = "Meli";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbReference = database.getReference(DATABASE_NAME);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayChatMessages(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayChatMessages(DataSnapshot dataSnapshot) {
        chatAdapter.clearMessages();

        for(DataSnapshot item : dataSnapshot.getChildren()) {
            ChatMessage msg = item.getValue(ChatMessage.class);
            chatAdapter.addChatMsg(msg);
        }
        chatAdapter.notifyDataSetChanged();
    }

    private void setupMsgInput() {
        final EditText inputMsg = (EditText) findViewById(R.id.input_message);
        inputMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String message = inputMsg.getText().toString();
                String username = AccountAuthenticator.getUsername(ChatActivity.this);
                ChatMessage data = new ChatMessage(username, message);

                dbReference.child(String.valueOf(new Date().getTime())).setValue(data);
                inputMsg.setText("");
                return true;
            }
        });
    }
}
