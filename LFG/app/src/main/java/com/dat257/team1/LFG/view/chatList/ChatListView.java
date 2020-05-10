package com.dat257.team1.LFG.view.chatList;

import android.content.Intent;
import android.os.Bundle;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.dat257.team1.LFG.view.MessageView;
import com.dat257.team1.LFG.view.commentFeed.CommentAdapter;
import com.dat257.team1.LFG.viewmodel.ChatListViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatListView extends AppCompatActivity implements ICardViewHolderClickListener {
    private ChatListViewModel viewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;

    private MutableLiveData<List<ChatListItem>> chatListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        getLifecycle().addObserver(viewModel);

        chatListItems = viewModel.getChatListItems();
        viewModel.getChatListItems().observe(this, new Observer<List<ChatListItem>>() {
            @Override
            public void onChanged(List<ChatListItem> chatListItems) {
                reAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.chatListRecycler);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        reLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new ChatListAdapter(chatListItems,this);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    public void onCardClicked(int pos) {
        Intent intent = new Intent(this, MessageView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
