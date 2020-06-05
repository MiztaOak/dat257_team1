package com.dat257.team1.LFG.view.chatList;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
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
        recyclerView.setHasFixedSize(false);
        reLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new ChatListAdapter(chatListItems, this);
        recyclerView.setAdapter(reAdapter);

        viewModel.onCreate();
    }

    @Override
    public void onCardClicked(View view, int pos) {
        Bundle bundle = new Bundle();
        bundle.putString("chatId", chatListItems.getValue().get(pos).getId());
        bundle.putString("chatName", chatListItems.getValue().get(pos).getTitle());
        Navigation.findNavController(view).navigate(R.id.action_nav_messages_to_nav_messageFragment, bundle);
    }

}
