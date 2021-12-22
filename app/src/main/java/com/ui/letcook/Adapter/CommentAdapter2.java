package com.ui.letcook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ui.letcook.Model.Comment;
import com.ui.letcook.R;

import java.util.List;

public class CommentAdapter2 extends RecyclerView.Adapter<CommentAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<Comment> mUploads;
    DatabaseReference databaseReferenceduser;


    public CommentAdapter2(Context context, List<Comment> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public CommentAdapter2.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.comment, parent, false);
        return new CommentAdapter2.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter2.ImageViewHolder holder, int position) {
        final Comment comment = mUploads.get(position);
        databaseReferenceduser= FirebaseDatabase.getInstance().getReference("User");

        Query query2 = databaseReferenceduser.orderByChild("email").equalTo(comment.getEmailuser());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String imageusercmt=ds.child("image").getValue().toString();
                    Picasso.get()
                            .load(imageusercmt)
                            .fit()
                            .centerCrop()
                            .into(holder.imageusercmt);

                    holder.textViewemail.setText(ds.child("username").getValue().toString());




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.textViewcmt.setText(comment.getComment());
//        Picasso.get()
//                .load(comment.getImageuser())
//                .fit()
//                .centerCrop()
//                .into(holder.imageusercmt);



    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageusercmt;
        public TextView textViewemail,textViewcmt;


        public ImageViewHolder(View itemView) {
            super(itemView);


            imageusercmt = itemView.findViewById(R.id.imagecmt);
            textViewcmt = itemView.findViewById(R.id.cmt);
            textViewemail = itemView.findViewById(R.id.emailcm);

        }
    }
    private String splitemail(String a){
        String []b=a.split("@");
        return b[0];
    }
}

