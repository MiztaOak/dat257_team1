package com.dat257.team1.LFG.view.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.NotificationForJoiner;

import java.util.List;

public class JoinerNotificationCardAdapter extends RecyclerView.Adapter <JoinerNotificationCardAdapter.StatusViewHolder> {


    private MutableLiveData<List<NotificationForJoiner>> status;
    private String actId;
    private String actName;

    public JoinerNotificationCardAdapter(MutableLiveData<List<NotificationForJoiner>> status){
        this.status = status;
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder{
        public TextView infoText;
        public TextView statusText;


        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            infoText = (TextView) itemView.findViewById(R.id.joinstatusInfoText);
            statusText = (TextView) itemView.findViewById(R.id.statusOfRequest);
        }
    }

    @NonNull
    @Override
    public JoinerNotificationCardAdapter.StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_notification_status,parent,false);
        return new JoinerNotificationCardAdapter.StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinerNotificationCardAdapter.StatusViewHolder holder, int position) {
        NotificationForJoiner notificationForJoiner = status.getValue().get(position);


        String a = FireStoreHelper.getInstance().getActivityTitle(notificationForJoiner.getActivity());

        holder.infoText.setText(" Your status for the activity "+ a + " is:");

        holder.statusText.setText(notificationForJoiner.getStatus());
    }




    @Override
    public int getItemCount() {
        if(status.getValue() == null)
            return 0;
        return status.getValue().size();
    }
}
