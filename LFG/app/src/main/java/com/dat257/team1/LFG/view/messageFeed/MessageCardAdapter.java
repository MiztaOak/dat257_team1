package com.dat257.team1.LFG.view.messageFeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dat257.team1.LFG.R;

import java.util.ArrayList;

public class MessageCardAdapter extends RecyclerView.Adapter<MessageCardAdapter.MessageCardViewHolder> {

    private ArrayList<MessageCard> mesCardsList;

    public class MessageCardViewHolder extends RecyclerView.ViewHolder {

        public ImageView contactImage;
        public TextView messageContent;
        public TextView time;

        private MessageCardViewHolder(View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contact_image);
            messageContent = itemView.findViewById(R.id.message_text);
            time = itemView.findViewById(R.id.time_stamp);
        }
    }

    public MessageCardAdapter(ArrayList<MessageCard> messageCardsList){
        mesCardsList = messageCardsList;

    }

    @Override
    public MessageCardAdapter.MessageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_cards, parent, false);
        MessageCardViewHolder mcvh = new MessageCardViewHolder(v);
        return mcvh;
    }


    @Override
    public void onBindViewHolder(MessageCardAdapter.MessageCardViewHolder holder, int position) {

        MessageCard currentCard = mesCardsList.get(position);

        holder.contactImage.setImageResource(R.drawable.ic_email_black_24dp); //since we don't have a src for user image yet
        holder.messageContent.setText(currentCard.getMessageContent());
        holder.time.setText(System.currentTimeMillis() + ""); //we haven't decided how the timestamp will show

    }

    @Override
    public int getItemCount() {
        return mesCardsList.size();
    }
}
