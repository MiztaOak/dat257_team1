package com.dat257.team1.LFG.view.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.dat257.team1.LFG.view.messageFeed.MessageView;
import com.dat257.team1.LFG.viewmodel.ChatListViewModel;

import java.util.List;

public class ChatListFragment extends Fragment implements ICardViewHolderClickListener {
    private ChatListViewModel viewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;

    private MutableLiveData<List<ChatListItem>> chatListItems;

    public ChatListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        getLifecycle().addObserver(viewModel);

        chatListItems = viewModel.getChatListItems();
        viewModel.getChatListItems().observe(getViewLifecycleOwner(), new Observer<List<ChatListItem>>() {
            @Override
            public void onChanged(List<ChatListItem> chatListItems) {
                reAdapter.notifyDataSetChanged();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.chatListRecycler);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        reLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new ChatListAdapter(chatListItems, this);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    public void onCardClicked(int pos) {
        Intent intent = new Intent(getContext(), MessageView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
