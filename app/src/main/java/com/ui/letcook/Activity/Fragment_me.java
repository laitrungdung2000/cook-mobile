package com.ui.letcook.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
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
import com.ui.letcook.R;


import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class Fragment_me extends Fragment {
    private static final int Pick_IMAGE_REQUEST = 1;
    private static final int STORAGE_REQUEST_CODE = 200;
    String languagePref;

    Uri image_uri;
    String changeavata=null;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    TextView logout,changename;
    FirebaseAuth mAuth;
    ImageView avatar;
    TextView nametv;
    TextView uplpadimage;
    TextView history;
    TextView language;
    TextView save,changepass;
    ProgressDialog pd;
    iLanguages iLanguages;

    public com.ui.letcook.Activity.iLanguages getiLanguages() {
        return iLanguages;
    }

    public void setiLanguages(com.ui.letcook.Activity.iLanguages iLanguages) {
        this.iLanguages = iLanguages;
    }

    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.uiacount, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        storageReference = FirebaseStorage.getInstance().getReference();


        pd = new ProgressDialog(getContext());
        pd.setMessage(mContext.getString(R.string.loadding));

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("email").getValue().toString();
                    String image = ds.child("image").getValue().toString();
                    String name= ds.child("username").getValue().toString();

                        nametv.setText(name);


                    if(image.equals("1")) {
                        Picasso.get().load(R.drawable.photo_camera).fit().centerCrop().into(avatar);
                    }
                    else Picasso.get().load(image).fit().centerCrop().into(avatar);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        choseiamge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                opentfile();
//            }
//
//
//        });
        uplpadimage=(TextView) v.findViewById(R.id.upload);
        uplpadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentfile();
            }
        });
        avatar = (ImageView) v.findViewById(R.id.avatar);
        changename=(TextView) v.findViewById(R.id.changename);
        nametv = (TextView) v.findViewById(R.id.usename);
        logout= (TextView) v.findViewById(R.id.dangxuat);
        language = (TextView) v.findViewById(R.id.choose_langage);

        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
                mydialog.setTitle(mContext.getString(R.string.enter_new_name));

                final EditText weightInput = new EditText(getActivity());

                mydialog.setView(weightInput);

                mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newname=weightInput.getText().toString();
                        databaseReference.child(user.getUid()).child("username").setValue(newname);
                        Toast.makeText(getActivity(), mContext.getString(R.string.change_name_success)
                               , Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });

                mydialog.setNegativeButton(mContext.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                mydialog.show();
            }

        });

        //click on Language
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        boolean checked = menuItem.isChecked();


                        switch(menuItem.getItemId()) {

                            case R.id.english:
                               iLanguages.changeLanguage("en");

                                break;

                            case R.id.vietnamese:



                                iLanguages.changeLanguage("vi");

                                break;

                        }

                        return true;
                    }

                });
                popupMenu.inflate(R.menu.menu_button);
                popupMenu.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                mAuth.signOut();
                checkStatus();
            }
        });

        history=(TextView) v.findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),History.class));
            }
        });
        save= (TextView) v.findViewById(R.id.savedish);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SaveDishActivity.class));
            }
        });
        changepass=v.findViewById(R.id.changepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Changepassword.class));
            }
        });
        return v;

    }

    private void uploadfile() {

        pd.show();
        String filePathAndName = "user/" + "image_" + user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            pd.dismiss();
                            HashMap<String, Object> results = new HashMap<>();
                            results.put("image", downloadUri.toString());

                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), mContext.getString(R.string.update_success), Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), mContext.getString(R.string.update_fail), Toast.LENGTH_LONG).show();
                                        }
                                    });

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
    }


    private void opentfile() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Pick_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_uri = data.getData();
            Picasso.get().load(image_uri).fit().centerCrop().into(avatar);
            uploadfile();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void checkStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        } else {
            startActivity(new Intent(getActivity(), SignIn.class));
        }
    }

    @Override
    public void onStart() {
        checkStatus();
        super.onStart();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

}