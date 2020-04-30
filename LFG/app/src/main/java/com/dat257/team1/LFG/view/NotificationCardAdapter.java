package com.dat257.team1.LFG.view;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.AcceptReqViewHolder>{

    private MutableLiveData<List<Pair<String, Activity>>> requests;

    public NotificationCardAdapter(MutableLiveData<List<Pair<String,Activity>>> requests){
        this.requests = requests;
    }

    public static class AcceptReqViewHolder extends RecyclerView.ViewHolder{
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

        View view = inflater.inflate(R.layout.card_notification_acceptreq,parent,false);
        return new AcceptReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationCardAdapter.AcceptReqViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(requests.getValue() == null)
            return 0;
        return requests.getValue().size();
    }
}
