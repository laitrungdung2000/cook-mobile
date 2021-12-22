package com.ui.letcook.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ui.letcook.Model.Comment;
import com.ui.letcook.R;


import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Comment> commentList;

    public CommentAdapter(Context context, int layout, List<Comment> commentList) {
        this.context = context;
        this.layout = layout;
        this.commentList = commentList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

//    public List<Comment> getCommentList() {
//        return commentList;
//    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(layout,null);
        TextView email=(TextView)convertView.findViewById(R.id.emailcm);
        TextView commenttx=(TextView)convertView.findViewById(R.id.cmt);
        ImageView image=(ImageView)convertView.findViewById(R.id.imagecmt);
        Comment comment= commentList.get(position);
        email.setText(comment.getEmailuser());
        Picasso.get().load(comment.getImageuser()).fit().into(image);
        commenttx.setText(comment.getComment());
        return convertView;
    }
}
