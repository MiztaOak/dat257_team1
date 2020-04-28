package com.dat257.team1.LFG.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;

import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private ArrayList<ActivityCardFragment> mCardsList;

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDescription;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.activityImage);
            mTitle = itemView.findViewById(R.id.titleText);
            mDescription = itemView.findViewById(R.id.DescriptionText);
        }
    }

    public CardAdapter(ArrayList<ActivityCardFragment> cardsList){
        mCardsList = cardsList;

    }



    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_fragment, parent, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        ActivityCardFragment currentCard = mCardsList.get(position);

        holder.mImageView.setImageResource(currentCard.getImageResource());
        holder.mDescription.setText(currentCard.getDescription());
        holder.mTitle.setText(currentCard.getTitle());

    }

    @Override
    public int getItemCount() {
        return mCardsList.size();
    }



}
