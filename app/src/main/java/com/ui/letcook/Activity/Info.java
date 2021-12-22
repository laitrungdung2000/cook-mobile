package com.ui.letcook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ui.letcook.R;


public class Info extends LocalizationActivity {
    EditText username;
    Button save;
    FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
        mAuth=FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.usename);
        save = (Button) findViewById(R.id.saveIf);
        loadInformation();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserIF();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, SignIn.class));
        }
    }
    private void loadInformation(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            if(user.getDisplayName() != null){
                username.setText(user.getDisplayName());
            }
        }


    }
    private void saveUserIF(){
        String Username=username.getText().toString();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user !=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(Username).build();

        user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Info.this,"Seccessed",Toast.LENGTH_LONG).show();
                }

            }
        });
        }

    }
}
