package com.dat257.team1.LFG.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;

import java.util.ArrayList;

public class FindActivityView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    private Button createActivity;
    private Button menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);


        ArrayList<CardsView> cardsList = new ArrayList<>();

        cardsList.add(new CardsView(R.drawable.ic_android_black_24dp, "Fotboll", "fotboll på heden kl 13:00"));
        cardsList.add(new CardsView(R.drawable.ic_radio_button_unchecked_black_24dp, "Basketspelare sökes", "söker basketspelare till match 14:00"));
        cardsList.add(new CardsView(R.drawable.ic_mood_black_24dp, "tennis", "tennis på heden kl 13:00"));
        cardsList.add(new CardsView(R.drawable.ic_watch_later_black_24dp, "padel", "padel på heden kl 13:00"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CardAdapter(cardsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        createActivity = (Button) findViewById(R.id.createActivity);
        menu = (Button) findViewById(R.id.menu);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelloWorld();
            }
        });

        menu.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHelloWorld();

            }
        }));


    }

    public void openHelloWorld(){

        Intent intent = new Intent(this, HelloWorldView.class
        );
        startActivity(intent);
    }
}
