package com.dat257.team1.LFG.view.commentFeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.view.ActDescriptionFragment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Map<String, Integer>> colorMap;

    public CommentAdapter(MutableLiveData<List<Comment>> comments, MutableLiveData<Map<String, Integer>> colorMap) {
        this.comments = comments;
        this.colorMap = colorMap;
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
        holder.commentProfileContainer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_activityDescriptionView_to_nav_profile, bundle));
        if (userName != null) {
            holder.iconText.setText(String.valueOf(userName.trim().charAt(0)));
            if (colorMap.getValue().get(comment.getCommenterRef()) != null) {
                holder.comment_profile_pic.setColorFilter(colorMap.getValue().get(comment.getCommenterRef()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (comments.getValue() == null)
            return 0;
        return comments.getValue().size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentText;
        TextView timeStamp;
        TextView commentUserName;

        RelativeLayout commentProfileContainer;
        TextView iconText;
        private ImageView comment_profile_pic;


         CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            commentUserName = (TextView) itemView.findViewById(R.id.commentUserName);

            commentProfileContainer = itemView.findViewById(R.id.relayout_profile);
            comment_profile_pic = itemView.findViewById(R.id.comment_profile_pic);
            iconText = itemView.findViewById(R.id.icon_text);
        }
    }


}
