package com.dat257.team1.LFG.view.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.model.NotificationForJoiner;

import java.util.List;

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.AcceptReqViewHolder> {

    private MutableLiveData<List<JoinNotification>> requests;
    private MutableLiveData<NotificationForJoiner> mutableNotification;

    public NotificationCardAdapter(MutableLiveData<List<JoinNotification>> requests) {
        this.requests = requests;
    }

    public static class AcceptReqViewHolder extends RecyclerView.ViewHolder {
        public TextView infoText;
        public Button accept;
        public Button decline;

        public AcceptReqViewHolder(@NonNull View itemView) {
            super(itemView);
            infoText = (TextView) itemView.findViewById(R.id.acceptreq_infoText);
            accept = (Button) itemView.findViewById(R.id.acceptreq_accept);
            decline = (Button) itemView.findViewById(R.id.acceptreq_decline);
        }
    }

    @NonNull
    @Override
    public AcceptReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_notification_acceptreq, parent, false);
        return new AcceptReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationCardAdapter.AcceptReqViewHolder holder, int position) {
        JoinNotification joinNotification = requests.getValue().get(position);
        holder.infoText.setText(joinNotification.getUserName() + " wants to join your activity" + joinNotification.getActivityTitle() + ".");
        holder.infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreHelper.getInstance().handleJoinRequest(joinNotification.getuID(), joinNotification.getActivityID(), true);
                //FireStoreHelper.getInstance().updateJoinStatus("Accepted", mutableNotification.getValue().getnId());
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreHelper.getInstance().handleJoinRequest(joinNotification.getuID(), joinNotification.getActivityID(), false);
                // FireStoreHelper.getInstance().updateJoinStatus("Declined", mutableNotification.getValue().getnId());
            }
        });

    }


    @Override
    public int getItemCount() {
        if (requests.getValue() == null)
            return 0;
        return requests.getValue().size();
    }

}
