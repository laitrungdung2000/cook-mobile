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

public class DishadminAdapter extends RecyclerView.Adapter<DishadminAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Dish> mUploads;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    public DishadminAdapter(Context context, List<Dish> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dish_item_admin, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

        final ArrayList<SaveDish> arrayList = new ArrayList<>();
        final ArrayList<Like> arrayListlike = new ArrayList<>();
        final Dish dish = mUploads.get(position);
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

//        databaseReference.child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    SaveDish saveDish=postSnapshot.getValue(SaveDish.class);
//                    arrayList.add(saveDish);
//
//                }
//                String save="false";
//
//                for (int i=0;i<arrayList.size();i++){
//                    if(dish.getId().equals(arrayList.get(i).getIddish().toString())){
//                        save="true";
//
//                    }
//                }
//                if(save=="true"){
//                    holder.starButton.setLiked(true);
//                    holder.textsave.setTextColor(mContext.getResources().getColor(R.color.black));
//
//                }
//                else if(save=="false") {   holder.starButton.setLiked(false);
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        final Integer[] l = {0};
//        databaseReference.child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayListlike.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Like like=postSnapshot.getValue(Like.class);
//                    arrayListlike.add(like);
//
//                }
//                for(int j=0;j<arrayListlike.size();j++){
//                    if(dish.getId().equals(arrayListlike.get(j).getNamedish())){
//                        l[0]++;
//                    }
//
//                }
//                String save="false";
//
//                for (int i=0;i<arrayListlike.size();i++){
//                    if(dish.getId().equals(arrayListlike.get(i).getNamedish().toString())){
//                        save="true";
//
//                    }
//                }
//                if(save=="true"){
//                    holder.likeButton.setLiked(true);
//
//                }
//                else if(save=="false") {   holder.likeButton.setLiked(false);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        holder.solike.setText((dish.getSolike()) + " "+mContext.getString(R.string.like));
        final int[] test = {dish.getSolike()};
        holder.namedish.setText(dish.getNamedish());
//        holder.nameuser.setText(splitemail(dish.getEmailuser()));

        holder.view.setText(dish.getView() +" "+mContext.getString(R.string.viewers));

        Picasso.get()
                .load(dish.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                    .fit()
                    .centerCrop()
                .into(holder.imageDish);

        holder.imageDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.view.setText(dish.getView() + 1 + " "+mContext.getString(R.string.viewers));
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


//        holder.starButton.setOnLikeListener(new OnLikeListener() {
//
//            @Override
//            public void liked(LikeButton likeButton) {
//                holder.textsave.setTextColor(mContext.getResources().getColor(R.color.black));
//                Toast.makeText(mContext,"Bài viết đã lưu",Toast.LENGTH_SHORT).show();
//                a[0] = databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").push().getKey();
//                databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(a[0]).setValue(new SaveDish(a[0],dish.getId(),dish.getNamedish()));
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//
//                SaveDish saveDish= new SaveDish();
//                if(a[0]==null) {
//                    for (int i = 0; i < arrayList.size(); i++) {
//                        if (dish.getNamedish().equals(arrayList.get(i).getNamedish())) {
//                            saveDish = arrayList.get(i);
//                        }
//                    }
//                    Toast.makeText(mContext,"Bỏ lưu",Toast.LENGTH_SHORT).show();
//                    databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(saveDish.getIdsave()).setValue(null);
//                }
//                if(a[0]!=null){
//                    databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(a[0]).setValue(null);
//                    Toast.makeText(mContext,"Bỏ lưu",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//        holder.likeButton.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//
//
//                holder.solike.setText(test[0]+1+" người thích");
//                b[0] = databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").push().getKey();
//                databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(b[0]).setValue(new Like(b[0],dish.getId()));
//                DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(dish.getId());
//                Dish dish2 =new Dish(dish.getImage(),dish.getNamedish(),dish.getEmailuser(),dish.getImageuser(),dish.getMake(),dish.getDate(),dish.getView(),dish.getId(),test[0]+1);
//                databaseReferenceview.setValue(dish2);
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//
//                Like like= new Like();
//                if(b[0]==null) {
//
//                    holder.solike.setText((test[0]-1+" người thích"));
//                    for (int i = 0; i < arrayListlike.size(); i++) {
//                        if (dish.getId().equals(arrayListlike.get(i).getNamedish())) {
//                            like = arrayListlike.get(i);
//                        }
//                    }
//                    DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(dish.getId());
//                    Dish dish2 =new Dish(dish.getImage(),dish.getNamedish(),dish.getEmailuser(),dish.getImageuser(),dish.getMake(),dish.getDate(),dish.getView(),dish.getId(),test[0]-1);
//                    databaseReferenceview.setValue(dish2);
//                    databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(like.getId()).setValue(null);
//                    test[0]=test[0]-1;
//
//                }
//                if(b[0]!=null){
//                    holder.solike.setText((test[0]+" người thích"));
//                    databaseReference2.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(b[0]).setValue(null);
//                    DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(dish.getId());
//                    Dish dish2 =new Dish(dish.getImage(),dish.getNamedish(),dish.getEmailuser(),dish.getImageuser(),dish.getMake(),dish.getDate(),dish.getView(),dish.getId(),test[0]);
//                    databaseReferenceview.setValue(dish2);
//
//                }
//            }
//        });
//
//
//    }
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
            namedish = itemView.findViewById(R.id.namedishadmin);
            imageDish = itemView.findViewById(R.id.imagedishadmin);
//                imageUser = itemView.findViewById(R.id.imageuser);
            view = itemView.findViewById(R.id.soviewadmin);
//            starButton=itemView.findViewById(R.id.star_button);
//            likeButton=itemView.findViewById(R.id.like_button);
            solike=itemView.findViewById(R.id.solikeadmin);

//            textsave=itemView.findViewById(R.id.textsave);
        }
    }
    public String splitemail(String email){
        String[] words = email.split("@");
        return words[0];
    }
}

