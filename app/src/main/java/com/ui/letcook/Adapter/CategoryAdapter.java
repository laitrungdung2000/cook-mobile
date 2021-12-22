package com.ui.letcook.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ui.letcook.Activity.RecipeActivity;
import com.ui.letcook.Activity.SearchActivity;
import com.ui.letcook.Model.Category;
import com.ui.letcook.Model.Comment;
import com.ui.letcook.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Category> mUploads;


    public CategoryAdapter(Context context, List<Category> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public CategoryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.category, parent, false);
        return new CategoryAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ImageViewHolder holder, int position) {
        final Category category = mUploads.get(position);

        Picasso.get()
                .load(category.getImage())
                .fit()
                .centerCrop()
                .into(holder.imageCategory);
        holder.name.setText(category.getName());
        holder.imageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("name", category.getCode());
                mContext.startActivity(intent);
            }
        });






    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageCategory;
        public TextView name;


        public ImageViewHolder(View itemView) {
            super(itemView);


            imageCategory = itemView.findViewById(R.id.imageCategory);
            name = itemView.findViewById(R.id.category);


        }
    }

}

