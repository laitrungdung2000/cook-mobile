package com.ui.letcook.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.ui.letcook.Model.Comment;
import com.ui.letcook.Adapter.CommentAdapter2;
import com.ui.letcook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeActivity extends LocalizationActivity {
    DatabaseReference databaseReferenceup;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    ImageView imageDish;
    ImageView imageuser;
    TextView nameuser,soviewre;
    TextView making,solikere;
    TextView nguyenlieu,mota;
    DatabaseReference databaseReferencedish;
    DatabaseReference databaseReferenceduser;
    TextView namedish;
    private RecyclerView recyclerView;

    String emailuser,imagedish,imageuser1,make,namedish1,namedish2;
    String id,date;
    int view,solike;
    ArrayList<Comment> listcmt;
    CommentAdapter2 commentAdapter;
    ImageButton send,backhome;
    EditText editcmt;
    ImageView imageviewcm;
    ScrollView scrollView;
    String emailcmt,iamgecusermt;
    Button back;
    LikeButton starButton,likeButton;
    String emailuser2;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });

        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        imageuser=(ImageView) findViewById(R.id.imageuserdish);
        imageDish=findViewById(R.id.imagedish);
        nameuser= findViewById(R.id.nameuserdish);
        making=findViewById(R.id.making);
        namedish= findViewById(R.id.namedish);

        //lie,save
        starButton=findViewById(R.id.save_button);
        likeButton=findViewById(R.id.like_button);
        solikere=findViewById(R.id.solikerecipe);
        //
        nguyenlieu=findViewById(R.id.nguyenlieure);
        mota=findViewById(R.id.motachungre);

        id=getIntent().getStringExtra("id");
        emailuser2=getIntent().getStringExtra("email");

        //set
      
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //comment
        editcmt=findViewById(R.id.editcmt);
        send=findViewById(R.id.uploadcmt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment();
            }
        });
        imageviewcm=findViewById(R.id.usercmt);
        soviewre=findViewById(R.id.viewre);
        scrollView=findViewById(R.id.scroll);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewcmt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        databaseReferenceup=FirebaseDatabase.getInstance().getReference();
        databaseReferencedish = FirebaseDatabase.getInstance().getReference("Dish");
        databaseReferenceduser = FirebaseDatabase.getInstance().getReference("User");
        firebaseDatabase= FirebaseDatabase.getInstance();

        //demview

        Query query3=databaseReferenceduser.orderByChild("email").equalTo(emailuser2);
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String imageusercmt=ds.child("image").getValue().toString();
                        Picasso.get()
                                .load(imageusercmt)
                                .fit()
                                .centerCrop()
                                .into(imageuser);


                    }
                } catch (NullPointerException e) {
                    System.out.println("Load Image:" + e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("Id:" + databaseReferencedish.orderByChild("id") + " " + id);
        Query query = databaseReferencedish.orderByChild("id").equalTo(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String s) {
                emailuser = ds.child("emailuser").getValue().toString();
                imagedish = ds.child("image").getValue().toString();
                make=ds.child("make").getValue().toString();
                namedish1=ds.child("id").getValue().toString();
                namedish2=ds.child("namedish").getValue().toString();
                solike=Integer.parseInt(ds.child("solike").getValue().toString());
                String nlieu= ds.child("nguyenlieu").getValue().toString();
                String mta=ds.child("mota").getValue().toString();
                mota.setText(mta);
                nguyenlieu.setText(nlieu);
                solikere.setText(solike+"");
                Picasso.get().load(imagedish).into(imageDish);
                nameuser.setText(splitemail(emailuser));
                String makeStepString = "";
                String[] parts = make.split(",");
                Log.v("Dish", make.split(",")[0]);
                for(int i=1; i <= parts.length; i++) {
                    makeStepString = makeStepString + "<b>Bước " +i+": </b>" + parts[i-1] + "<br/>";

                }

                making.setText(Html.fromHtml(makeStepString));
                namedish.setText(namedish2);
                soviewre.setText(ds.child("view").getValue().toString()+"");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query query2 = databaseReferenceduser.orderByChild("uid").equalTo(firebaseUser.getUid());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    emailcmt=ds.child("email").getValue().toString();
                    iamgecusermt=ds.child("image").getValue().toString();
                    Picasso.get().load(iamgecusermt).fit().centerCrop().into(imageviewcm);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        loadComment();
        final ArrayList<SaveDish> arrayList = new ArrayList<>();
        final ArrayList<Like> arrayListlike = new ArrayList<>();
        databaseReferenceduser.child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        SaveDish saveDish = postSnapshot.getValue(SaveDish.class);
                        arrayList.add(saveDish);

                    }
                    String save = "false";

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (id.equals(arrayList.get(i).getIddish().toString())) {
                            save = "true";

                        }
                    }
                    if (save == "true") {
                      starButton.setLiked(true);


                    } else if (save == "false") {
                       starButton.setLiked(false);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        databaseReferenceduser.child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayListlike.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Like like = postSnapshot.getValue(Like.class);
                        arrayListlike.add(like);

                    }

                    String save = "false";

                    for (int i = 0; i < arrayListlike.size(); i++) {
                        if (id.equals(arrayListlike.get(i).getNamedish().toString())) {
                            save = "true";

                        }
                    }
                    if (save == "true") {
                       likeButton.setLiked(true);

                    } else if (save == "false") {
                        likeButton.setLiked(false);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            final String[] a = {null};
            final String[] b = {null};
            final int[] test = {solike};


           starButton.setOnLikeListener(new OnLikeListener() {

                @Override
                public void liked(LikeButton likeButton) {


                    a[0] = databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").push().getKey();
                    databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(a[0]).setValue(new SaveDish(a[0],id,namedish2));
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    SaveDish saveDish= new SaveDish();
                  if(a[0]==null) {
                      for (int i = 0; i < arrayList.size(); i++) {
                          if (namedish2.equals(arrayList.get(i).getNamedish())) {
                               saveDish = arrayList.get(i);
                          }
                      }
                      databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(saveDish.getIdsave()).setValue(null);
                  }
                  if(a[0]!=null){
                      databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("save").child(a[0]).setValue(null);

                  }
                }
            });

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {


                    solikere.setText(solike+1+"");
                    b[0] = databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").push().getKey();
                    databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(b[0]).setValue(new Like(b[0],id));
                    DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(id).child("solike");
                    databaseReferenceview.setValue(solike+1);
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    Like like= new Like();
                    if(b[0]==null) {
                        solikere.setText(solike-1+"");
                        for (int i = 0; i < arrayListlike.size(); i++) {
                            if (id.equals(arrayListlike.get(i).getNamedish())) {
                                like = arrayListlike.get(i);
                            }
                        }
                        DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(id).child("solike");
                        databaseReferenceview.setValue(solike-1);
                        databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(like.getId()).setValue(null);

                    }
                    if(b[0]!=null){
                        solikere.setText((solike+""));

                        databaseReferenceup.child("User").child(firebaseUser.getUid()).child(firebaseUser.getUid()).child("like").child(b[0]).setValue(null);
                        DatabaseReference databaseReferenceview=FirebaseDatabase.getInstance().getReference("Dish").child(id).child("solike");
                        databaseReferenceview.setValue(solike);

                    }
                }
                });


    }
    private void loadComment(){
        listcmt = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listcmt.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
                    if(comment.getIddish().equals(namedish1))
                    listcmt.add(comment);
                }
                 commentAdapter = new CommentAdapter2(RecipeActivity.this,listcmt);
                recyclerView.setAdapter(commentAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RecipeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void uploadComment(){
        databaseReferenceup.child("Comments").push().setValue(new Comment(namedish1,iamgecusermt,emailcmt,editcmt.getText().toString()));
        editcmt.setText("");
    }
 private String splitemail(String a){
        String[]b=a.split("@");
        return b[0];
 }
}
