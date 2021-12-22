package com.ui.letcook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ui.letcook.Model.Acount;
import com.ui.letcook.R;


import java.util.ArrayList;
import java.util.HashMap;

public class SignUp extends LocalizationActivity {
    FirebaseAuth mAuth;
    Button sign;
    EditText tk;
    EditText mk,mk2;
    EditText usename;
    DatabaseReference mData;
    ProgressDialog progressDialog;
    TextView signin;
//    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tk = (EditText) findViewById(R.id.tk);
        mk = (EditText) findViewById(R.id.mk);
        mk2 = (EditText) findViewById(R.id.password2);
        sign = (Button) findViewById(R.id.button2);
        signin=(TextView) findViewById(R.id.textsignin);
        usename = (EditText) findViewById(R.id.usename);
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loadding));
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=tk.getText().toString();
                String password=mk.getText().toString();
                String password2=mk2.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tk.setError(getString(R.string.check_format_account));
                    tk.setFocusable(true);
                }
                if(email.isEmpty()){
                    tk.setError(getString(R.string.enter_new_email));
                    tk.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tk.setError(getString(R.string.enter_format_email));
                    tk.requestFocus();
                    return;
                }
                if(password.length()<6){
                    mk.setError(getString(R.string.check_password));
                    mk.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    mk.setError(getString(R.string.enter_password));
                    mk.requestFocus();
                    return;
                }
                if(!password.equals(password2)){
                    mk2.setError(getString(R.string.password_fail));
                    mk2.requestFocus();
                    return;
                }
                else{
                    registerUser(email,password);


                    }
            }
        });
    }
    private void registerUser(String email,String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            tk.setText("");
                            mk.setText("");
                            mk2.setText("");
                            progressDialog.dismiss();
                            FirebaseUser user=mAuth.getCurrentUser();

                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                ArrayList<String> save = new ArrayList();
                                String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object, Acount> hashMap= new HashMap<>();
                            hashMap.put(uid,new Acount(email,uid,"1",splitemail(email),save,0));
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User");
                                reference.child(uid).setValue(new Acount(email,uid,"image",splitemail(email),save,0));
                            }

                            Toast.makeText(SignUp.this,getString(R.string.create_account_success),Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SignUp.this,getString(R.string.create_account_fail),Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
//                Toast.makeText(SignUp.this,""+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private String splitemail(String a){
        String[]b=a.split("@");
        return b[0];
    }
}
