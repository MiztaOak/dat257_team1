package com.dat257.team1.LFG.view.chatList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListCard>{

    private MutableLiveData<List<ChatListItem>> chatListItems;
    private ICardViewHolderClickListener cardViewHolderClickListener;

    public ChatListAdapter(MutableLiveData<List<ChatListItem>> chatListItems, ICardViewHolderClickListener cardViewHolderClickListener){
        this.chatListItems = chatListItems;
        this.cardViewHolderClickListener = cardViewHolderClickListener;
    }

    public static class ChatListCard extends RecyclerView.ViewHolder{
        public TextView chatTitle;

        public ChatListCard(@NonNull View itemView) {
            super(itemView);
            chatTitle = (TextView) itemView.findViewById(R.id.chatTitle);
        }
    }

    @NonNull
    @Override
    public ChatListAdapter.ChatListCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_chat_conversation,parent,false);
        return new ChatListAdapter.ChatListCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatListCard holder, int position) {
        holder.chatTitle.setText(chatListItems.getValue().get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewHolderClickListener.onCardClicked(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(chatListItems.getValue() == null)
            return 0;
        return chatListItems.getValue().size();
    }
}
