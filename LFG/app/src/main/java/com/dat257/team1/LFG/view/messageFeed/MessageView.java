package com.dat257.team1.LFG.view.messageFeed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Message;
import com.dat257.team1.LFG.viewmodel.MessageViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageView extends AppCompatActivity {

    private Button menu;
    private Button createNewMessage;
    private RecyclerView chatFeed;
    private MessageCardAdapter msgAdapter;

    private ArrayList<MessageCard> messageCardsViewList;
    private MessageViewModel messageViewModel;
    private MutableLiveData<List<Message>> messages;
    private RecyclerView.Adapter reAdapter;

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



        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messages = messageViewModel.getMessages();
        messages.observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                reAdapter.notifyDataSetChanged();

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
