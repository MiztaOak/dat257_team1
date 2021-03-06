package com.dat257.team1.LFG.view.activityFeed;


import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.repository.Repository;
import com.dat257.team1.LFG.view.ICardViewHolderClickListener;
import com.google.firebase.auth.FirebaseAuth;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_act_card, parent, false);
        return new CardViewHolder(v, iCardViewHolderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder cardViewHolder, int position) {
        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() { //TODO Bad performance should be in constructor
            @Override
            public void onClick(View view) {
                Repository.getInstance().setFocusedActivity(activityList.getValue().get(position)); //moved this here to function with the current activities
                iCardViewHolderClickListener.onCardClicked(view, position);
            }
        });

        String id = activityList.getValue().get(position).getCategory().getName().trim().toLowerCase();
        ImageView imageView = ((CardViewHolder) cardViewHolder).mImageView;
        imageView.setImageResource(context.getResources().getIdentifier(id, "drawable", context.getPackageName()));
        TextView mTitle = ((CardViewHolder) cardViewHolder).mTitle;
        TextView mDescription = ((CardViewHolder) cardViewHolder).mDescription;
        TextView mLocation = ((CardViewHolder) cardViewHolder).mLocation;
        TextView mCategory = ((CardViewHolder) cardViewHolder).mCategory;
        TextView mAttendees = ((CardViewHolder) cardViewHolder).mAttendees;
        TextView mDate = ((CardViewHolder) cardViewHolder).mDate;
        mTitle.setText(activityList.getValue().get(position).getTitle());
        mDate.setText(activityList.getValue().get(position).getDateFromTimestamp());
        mDescription.setText(activityList.getValue().get(position).getDescription());
        mCategory.setText(activityList.getValue().get(position).getCategory().getName());
        if(activityList.getValue().get(position).getNumAttendees() != 0)
            mAttendees.setText("Participants: " + activityList.getValue().get(position).getParticipants().size()
                            +" / " + activityList.getValue().get(position).getNumAttendees());
        else
            mAttendees.setText("Participants: " + activityList.getValue().get(position).getParticipants().size());

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            mLocation.setText(activityList.getValue().get(position).getAddressFromLocation(context));
        }
        else
            mLocation.setText("Sign in to see location!");
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
        TextView mLocation;
        TextView mCategory;
        TextView mAttendees;
        TextView mDate;

        public CardViewHolder(@NonNull View itemView, ICardViewHolderClickListener iCardViewHolderClickListener) {
            super(itemView);

            this.iCardViewHolderClickListener = iCardViewHolderClickListener;
            this.mImageView = itemView.findViewById(R.id.activityImage);
            this.mTitle = itemView.findViewById(R.id.titleText);
            this.mDescription = itemView.findViewById(R.id.DescriptionText);
            this.mLocation = itemView.findViewById(R.id.LocationText);
            this.mCategory = itemView.findViewById(R.id.CategoryText);
            this.mAttendees = itemView.findViewById(R.id.AttendeesText);
            this.mDate = itemView.findViewById(R.id.dateText);

        }
    }

    static class RecyclerViewMargin extends RecyclerView.ItemDecoration {
        private final int columns;
        private int margin;

        public RecyclerViewMargin(@IntRange(from = 0) int margin, @IntRange(from = 0) int columns) {
            this.margin = margin;
            this.columns = columns;

        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            int position = parent.getChildLayoutPosition(view);
            //set right margin to all
            outRect.right = margin;
            //set bottom margin to all
            outRect.bottom = margin;
            //we only add top margin to the first row
            if (position < columns) {
                outRect.top = margin;
            }
            //add left margin only to the first column
            if (position % columns == 0) {
                outRect.left = margin;
            }
        }
    }
}
