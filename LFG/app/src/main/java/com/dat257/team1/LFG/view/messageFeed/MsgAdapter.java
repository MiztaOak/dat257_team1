package com.dat257.team1.LFG.view.messageFeed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Message;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MsgAdapter extends BaseAdapter {

    private Context mContext;
    private MutableLiveData<List<Message>> messageList;
    private FirebaseUser currentUser;

    public MsgAdapter(Context mContext, MutableLiveData<List<Message>> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void setMsgList(MutableLiveData<List<Message>> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return (messageList.getValue() == null) ? 0 : messageList.getValue().size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.getValue().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MsgViewHolder msgViewHolder = new MsgViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messageList.getValue().get(i);

        if (message.getSenderId().equals(currentUser.getUid())) {
            assert layoutInflater != null;
            view = layoutInflater.inflate(R.layout.message_my, null);
            msgViewHolder.txtViewMsg = view.findViewById(R.id.msg_send_content);
            view.setTag(msgViewHolder);
            msgViewHolder.txtViewMsg.setText(message.getContent());
        } else {
            assert layoutInflater != null;
            view = layoutInflater.inflate(R.layout.message_others, null);
            msgViewHolder.imageViewProfile = view.findViewById(R.id.msg_receive_profile_pic);
            Bundle bundle = new Bundle();
            bundle.putString("userId", message.getSenderId());
            msgViewHolder.imageViewProfile.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_messageFragment_to_nav_profile, bundle));
            msgViewHolder.txtViewMsg = view.findViewById(R.id.msg_receive_content);
            msgViewHolder.txtViewSenderName = view.findViewById(R.id.msg_receive_name);
            view.setTag(msgViewHolder);
            msgViewHolder.txtViewMsg.setText(message.getContent());
            //msgViewHolder.imageViewProfile.setImageDrawable(); //TODO
            String userName = FireStoreHelper.getInstance().getIdToNameDictionary().get(message.getSenderId());
            msgViewHolder.txtViewSenderName.setText(userName);
        }
        return view;
    }

    class MsgViewHolder {
        public ImageView imageViewProfile;
        public TextView txtViewSenderName;
        public TextView txtViewMsg;
        public Timestamp timestamp;
    }
}
