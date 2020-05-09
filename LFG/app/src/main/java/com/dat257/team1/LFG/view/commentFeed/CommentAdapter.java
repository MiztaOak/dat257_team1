package com.dat257.team1.LFG.view.commentFeed;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Comment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private MutableLiveData<List<Comment>> comments;

    public CommentAdapter(MutableLiveData<List<Comment>> comments){
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public TextView commentText;
        public TextView timeStamp;
        public TextView commentUserName;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            commentUserName = (TextView) itemView.findViewById(R.id.commentUserName);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.getValue().get(position);
        holder.commentText.setText(comment.getCommentText());
        Format formatter = new SimpleDateFormat("HH:mm dd-MM-yy");
        String date =  formatter.format(comment.getCommentDate());
        holder.timeStamp.setText(date);
        String userName = FireStoreHelper.getInstance().getIdToNameDictionary().get(comment.getCommenterRef());
        holder.commentUserName.setText(userName);
    }

    @Override
    public int getItemCount() {
        if(comments.getValue() == null)
            return 0;
        return comments.getValue().size();
    }


}
