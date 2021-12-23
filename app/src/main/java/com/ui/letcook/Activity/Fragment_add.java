package com.ui.letcook.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ui.letcook.Model.Dish;
import com.ui.letcook.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Fragment_add extends Fragment {
    String imageuser = "";
    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencedish;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseUser user;
    public static final int Pick_IMAGE_REQUEST = 1;
    public static final int Pick_IMAGE_STEP= 2;

    Uri image_uri, image_step;
    Button upload;
    Button add, hiddenButton, hiddenButton1, addImage;
    Button chooseimage;
    EditText namedish;
    EditText mota, nguyenlieu;
    TextView textView;
    ProgressDialog pd;
    ImageView imageDish, imgStep;
    TextView make, make1;
    int sobai;
    RelativeLayout b1, b2;
    private Context mContext ;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_add,container,false);
        imageDish= (ImageView) v.findViewById(R.id.ImageDish);
        pd=new ProgressDialog(getActivity());
        pd.setMessage(mContext.getString(R.string.loadding));
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        databaseReferencedish=firebaseDatabase.getReference("Dish");

        mota = v.findViewById(R.id.motachung);
        nguyenlieu = v.findViewById(R.id.nguyenlieu);


        storageReference = FirebaseStorage.getInstance().getReference();

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("email").getValue().toString();
                    String image = ds.child("image").getValue().toString();
                    sobai = Integer.parseInt(ds.child("sobaidang").getValue().toString());
                    imageuser = image;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        make = v.findViewById(R.id.cachlam);
        make1 = v.findViewById(R.id.cachlam1);
        namedish = v.findViewById(R.id.namedish);
        upload = v.findViewById(R.id.upload);
        add = v.findViewById(R.id.add);
        b1 = v.findViewById(R.id.b1);
        b2 = v.findViewById(R.id.b2);
        hiddenButton = v.findViewById(R.id.substract);
        hiddenButton1 = v.findViewById(R.id.substract1);
        addImage = v.findViewById(R.id.addImage);
        imgStep = v.findViewById(R.id.imgStep);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opentfile(Pick_IMAGE_STEP);
            }
        });
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setVisibility(View.GONE);
            }
        });
        hiddenButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setVisibility(View.GONE);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b1.getVisibility() == View.GONE)
                    b1.setVisibility(View.VISIBLE);
                else if(b2.getVisibility() == View.GONE)
                    b2.setVisibility(View.VISIBLE);
            }
        });


        imageDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentfile(Pick_IMAGE_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=namedish.getText().toString();
                String motangan=mota.getText().toString();
                String nguyenlieu1=nguyenlieu.getText().toString();
                String cachlam=make.getText().toString();
                if(ten.isEmpty()){
                    namedish.setError(mContext.getString(R.string.enter_food_name));
                    namedish.requestFocus();
                    return;
                }
                if(motangan.isEmpty()){
                    mota.setError(mContext.getString(R.string.enter_food_description));
                    mota.requestFocus();
                    return;
                }
                if(nguyenlieu1.isEmpty()){
                    nguyenlieu.setError(mContext.getString(R.string.enter_material));
                    nguyenlieu.requestFocus();
                    return;
                }
                if(cachlam.isEmpty()){
                    mota.setError(mContext.getString(R.string.enter_material));
                    make.requestFocus();
                    return;
                } else uploadfile();
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void opentfile(int i) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, i);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Pick_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_uri = data.getData();
            Picasso.get().load(image_uri).fit().centerCrop().into(imageDish);
        } else if(requestCode == Pick_IMAGE_STEP && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_step = data.getData();
            Picasso.get().load(image_step).fit().centerCrop().into(imgStep);
        }
    }
    private void uploadfile() {
        String image = "https://firebasestorage.googleapis.com/v0/b/dcmm-bc67e.appspot.com/o/dish%2Fimage_1573225436453?alt=media&token=a60d47f5-e184-43cf-af00-b0b04ef5d54b";
        pd.show();
        String filePathAndName = "dish/" + "image_" + System.currentTimeMillis();
        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        if (image_uri != null) {
            storageReference2nd.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                pd.dismiss();
                                String id = databaseReferencedish.push().getKey();
                                Dish dish = new Dish(downloadUri.toString(), namedish.getText().toString(), mota.getText().toString(), nguyenlieu.getText().toString(), user.getEmail(), imageuser, make.getText().toString(), TimeDate(), 0, id, 0);
                                databaseReferencedish.child(id).setValue(dish)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), mContext.getString(R.string.post_success), Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(getActivity(), mContext.getString(R.string.fail_connect), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                namedish.setText("");
                                mota.setText("");
                                make.setText("");
                                nguyenlieu.setText("");
                                Picasso.get().load(R.drawable.camera).placeholder(R.drawable.camera).fit().centerCrop().into(imageDish);


                            } else {
                                pd.dismiss();
                                Toast.makeText(getActivity(), "SOME ERROR", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            databaseReference.child(user.getUid()).child("sobaidang").setValue(sobai+1);

        }

        if(image_uri==null){
                            pd.dismiss();
                            Toast.makeText(getActivity(),mContext.getString(R.string.choose_image), Toast.LENGTH_LONG).show();}

    }
    public String TimeDate() {
        String time=null;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd:MM:yyyy");

        time= simpleDateFormat.format(calendar.getTime())+" At " +dateFormat.format(calendar.getTime());


        return time;
    }
}
