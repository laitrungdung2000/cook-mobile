package com.ui.letcook.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ui.letcook.R;
import com.ui.letcook.Model.Acount;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Acount> mUploads;
    DatabaseReference databaseReferenceduser;


    public TopAdapter(Context context, List<Acount> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public TopAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.topthanhvien, parent, false);
        return new TopAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TopAdapter.ImageViewHolder holder, int position) {

        final Acount acount = mUploads.get(position);

        holder.nametop.setText(acount.getUsername());
        holder.sobaidang.setText(acount.getSobaidang()+ " " +  mContext.getString(R.string.recipe));
        Picasso.get()
                .load(acount.getImage())
                .fit()
                .centerCrop()
                .into(holder.imagetop);



    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagetop;
        public TextView nametop,sobaidang;


        public ImageViewHolder(View itemView) {
            super(itemView);


            imagetop = itemView.findViewById(R.id.avatartop);
            nametop = itemView.findViewById(R.id.nametop);
            sobaidang = itemView.findViewById(R.id.sobaidang);

        }
    }
    private String splitemail(String a){
        String []b=a.split("@");
        return b[0];
    }
}


