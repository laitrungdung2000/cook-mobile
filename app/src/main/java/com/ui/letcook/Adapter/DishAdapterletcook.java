package com.ui.letcook.Adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;
import com.ui.letcook.Model.Dish;
import com.ui.letcook.Activity.Like;
import com.ui.letcook.R;
import com.ui.letcook.Activity.RecipeActivity;
import com.ui.letcook.Activity.SaveDish;


import java.util.ArrayList;
import java.util.List;

public class DishAdapterletcook extends RecyclerView.Adapter<DishAdapterletcook.ImageViewHolder> {
    private Context mContext;
    private List<Dish> mUploads;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    public DishAdapterletcook(Context context, List<Dish> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dish_item_letcook, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

        final ArrayList<SaveDish> arrayList = new ArrayList<>();
        final ArrayList<Like> arrayListlike = new ArrayList<>();
        final Dish dish = mUploads.get(position);
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");



        holder.solike.setText((dish.getSolike()) +" "+ mContext.getString(R.string.like));
        final int[] test = {dish.getSolike()};
        holder.namedish.setText(dish.getNamedish());
//        holder.nameuser.setText(splitemail(dish.getEmailuser()));

        holder.view.setText(dish.getView() +" "+ mContext.getString(R.string.viewers));

        Picasso.get()
                .load(dish.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                .fit()
                .centerCrop()
                .into(holder.imageDish);

        holder.imageDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.view.setText(dish.getView() + 1 +" "+ mContext.getString(R.string.viewers));
                DatabaseReference databaseReferenceview = FirebaseDatabase.getInstance().getReference("Dish").child(dish.getId()).child("view");
//                Dish dish2 = new Dish(dish.getImage(), dish.getNamedish(), dish.getEmailuser(), dish.getImageuser(), dish.getMake(), dish.getDate(), dish.getView() + 1, dish.getId(), dish.getSolike());
                databaseReferenceview.setValue(dish.getView() + 1);
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("id", dish.getId());
                intent.putExtra("email", dish.getEmailuser());

                mContext.startActivity(intent);



            }
        });
        final String[] a = {null};
        final String[] b = {null};



    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nameuser,namedish,date,view,solike;
        public ImageView imageDish,imageUser,save;
        public TextView textlike,textsave;
        LikeButton starButton,likeButton;
        public ImageViewHolder(View itemView) {
            super(itemView);
//                save=itemView.findViewById(R.id.save);
//            nameuser = itemView.findViewById(R.id.nameuser);
            namedish = itemView.findViewById(R.id.namedishletcook);
            imageDish = itemView.findViewById(R.id.imagedishletcook);
//                imageUser = itemView.findViewById(R.id.imageuser);
            view = itemView.findViewById(R.id.soviewletcook);
//            starButton=itemView.findViewById(R.id.star_button);
//            likeButton=itemView.findViewById(R.id.like_button);
            solike=itemView.findViewById(R.id.solikeletcook);

//            textsave=itemView.findViewById(R.id.textsave);
        }
    }
    public String splitemail(String email){
        String[] words = email.split("@");
        return words[0];
    }
}

