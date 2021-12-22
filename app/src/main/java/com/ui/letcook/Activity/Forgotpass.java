package com.ui.letcook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.ui.letcook.R;

public class Forgotpass extends LocalizationActivity {
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    EditText email;
    ProgressDialog pd;
    Button send,back;
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
            email=findViewById(R.id.emailforgot);
            send=findViewById(R.id.send);
            back=findViewById(R.id.bachquenmk);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            pd=new ProgressDialog(Forgotpass.this);
            pd.setMessage(getString(R.string.loadding));
           auth = FirebaseAuth.getInstance();
           firebaseUser=auth.getCurrentUser();

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd.show();
                    auth.sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Toast.makeText(Forgotpass.this, getString(R.string.email_test), Toast.LENGTH_SHORT).show();
                                    } else {
                                        pd.dismiss();
                                Toast.makeText(Forgotpass.this, getString(R.string.account_fail), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });


    }
}
