package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;

public class MessageView extends AppCompatActivity {

    private Button menu;
    private Button createNewMessage;
    private RecyclerView chatFeed;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_fragment);

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

        chatFeed = findViewById(R.id.comment_feed);
    }
}
