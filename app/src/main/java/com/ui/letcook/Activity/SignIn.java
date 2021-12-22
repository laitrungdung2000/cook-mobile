package com.ui.letcook.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ui.letcook.Model.Acount;
import com.ui.letcook.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends LocalizationActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    EditText tk;
    EditText mk;
    Button signin;
    TextView signup;
    TextView forgot;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    private String check="";

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        this.mContext = context;
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        loginButton=findViewById(R.id.login_button);
        callbackManager=CallbackManager.Factory.create();
        loginButton.setReadPermissions("email","public_profile");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
//
                        checkAcountFB(firebaseAuth.getUid());



                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
        tk=(EditText)findViewById(R.id.tksi);
        mk=(EditText) findViewById(R.id.mksi);
        signup= (TextView) findViewById(R.id.textsignup);
        signin=(Button)findViewById(R.id.bSin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        forgot= findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( SignIn.this, Forgotpass.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ;
                String email=tk.getText().toString();
                String password=mk.getText().toString();
                if(email.isEmpty()){
                    tk.setError(getString(R.string.enter_new_email));
                    tk.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tk.setError(getString(R.string.enter_format_email));
                    tk.requestFocus();
                }
                else if(password.isEmpty()){
                    mk.setError(getString(R.string.enter_password));
                    mk.requestFocus();
                }
                else if(password.length()<6){
                    mk.setError(getString(R.string.check_password));
                    mk.requestFocus();

                }
                else loginUser(email,password);
            }
        });
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loadding_login));

    }
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loginUser(String email, final String password){
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();;
                           FirebaseUser user=firebaseAuth.getCurrentUser();
                           startActivity(new Intent(SignIn.this, MainActivity.class));
                           finish();
                        } else {
                            Toast.makeText(SignIn.this, getString(R.string.login_fail),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignIn.this,getString(R.string.check_user_and_pass),Toast.LENGTH_LONG).show();

            }
        });
    }
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            System.out.println(user.getUid());

                                ArrayList<String> save = new ArrayList();
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User");
                                reference.child(user.getUid()).setValue(new Acount(user.getEmail(),user.getUid(),user.getPhotoUrl().toString(),user.getDisplayName(),save,0));
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                                finish();




                        } else {
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkAcountFB(final String uid){


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("uid").getValue().toString().equals(uid)){
                        check="done";

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
