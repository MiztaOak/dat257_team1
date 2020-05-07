package com.dat257.team1.LFG.view.activityFeed;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;

import java.util.List;


public class ActCardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = ActCardRecyclerAdapter.class.getSimpleName();
    private final ICardViewHolderClickListener iCardViewHolderClickListener;
    private MutableLiveData<List<Activity>> activityList;
    private Context context;


    public ActCardRecyclerAdapter(Context context, MutableLiveData<List<Activity>> activityMutableLiveData, ICardViewHolderClickListener iCardViewHolderClickListener) {
        this.activityList = activityMutableLiveData;
        this.context = context;
        this.iCardViewHolderClickListener = iCardViewHolderClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_fragment, parent, false);
        return new CardViewHolder(v, iCardViewHolderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder cardViewHolder, int position) {
        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() { //TODO Bad performance should be in constructor
            @Override
            public void onClick(View view) {
                iCardViewHolderClickListener.onCardClicked(position);
            }
        });
        ImageView imageView = ((CardViewHolder) cardViewHolder).mImageView;
        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_red_24dp));
        TextView mTitle = ((CardViewHolder) cardViewHolder).mTitle;
        TextView mDescription = ((CardViewHolder) cardViewHolder).mDescription;
        cardViewHolder.itemView.setBackground(context.getDrawable(R.drawable.gradient_1));

        mTitle.setText(activityList.getValue().get(position).getTitle());
        mDescription.setText(activityList.getValue().get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        if (activityList.getValue() == null || activityList.getValue().size() == 0) {
            return 0;
        }
        return activityList.getValue().size();
    }

    /**
     * Inner class that functions as a row in the recyclerview.
     */
    class CardViewHolder extends RecyclerView.ViewHolder {

        //more here all the fields relevant for a card.
        final ICardViewHolderClickListener iCardViewHolderClickListener;
        ImageView mImageView;
        TextView mTitle;
        TextView mDescription;

        public CardViewHolder(@NonNull View itemView, ICardViewHolderClickListener iCardViewHolderClickListener) {
            super(itemView);

            this.iCardViewHolderClickListener = iCardViewHolderClickListener;
            this.mImageView = itemView.findViewById(R.id.activityImage);
            this.mTitle = itemView.findViewById(R.id.titleText);
            this.mDescription = itemView.findViewById(R.id.DescriptionText);

        }
    }
}
