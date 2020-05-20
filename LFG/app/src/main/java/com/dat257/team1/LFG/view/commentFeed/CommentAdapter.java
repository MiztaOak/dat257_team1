package com.dat257.team1.LFG.view.commentFeed;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Comment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private MutableLiveData<List<Comment>> comments;

    public CommentAdapter(MutableLiveData<List<Comment>> comments) {
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView commentText;
        public TextView timeStamp;
        public TextView commentUserName;
        public RelativeLayout commentProfilePic;
        public TextView iconText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            commentUserName = (TextView) itemView.findViewById(R.id.commentUserName);
            commentProfilePic = itemView.findViewById(R.id.comment_profile_pic);
            iconText = itemView.findViewById(R.id.icon_text);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.getValue().get(position);
        holder.commentText.setText(comment.getCommentText());
        Format formatter = new SimpleDateFormat("HH:mm dd-MM-yy");
        String date = formatter.format(comment.getCommentDate());
        holder.timeStamp.setText(date);
        String userName = FireStoreHelper.getInstance().getIdToNameDictionary().get(comment.getCommenterRef());
        holder.commentUserName.setText(userName);

        Bundle bundle = new Bundle();
        bundle.putString("userId", comment.getCommenterRef());
        holder.commentProfilePic.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_activityDescriptionView_to_nav_profile, bundle));
        if (userName != null) {
            holder.iconText.setText(String.valueOf(userName.trim().charAt(0)));
        }
    }

    @Override
    public int getItemCount() {
        if (comments.getValue() == null)
            return 0;
        return comments.getValue().size();
    }

}
