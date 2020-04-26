package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageView extends AppCompatActivity {

    private Button menu;
    private Button createNewMessage;
    private RecyclerView chatFeed;
    private MessageCardAdapter msgAdapter;

    private ArrayList<MessageCard> messageCardsViewList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        menu = findViewById(R.id.menu_button);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Open menu");
            }
        });

        createNewMessage = findViewById(R.id.create_message);
        createNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Create new message");
            }
        });

        chatFeed = findViewById(R.id.chat_feed);
        chatFeed.setHasFixedSize(true);
        chatFeed.setLayoutManager(new LinearLayoutManager(this));


        messageCardsViewList = new ArrayList<>();
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        messageCardsViewList.add(new MessageCard("", "This is a test", new Timestamp(System.currentTimeMillis())));
        msgAdapter = new MessageCardAdapter(messageCardsViewList);
        chatFeed.setAdapter(msgAdapter);
    }
}
