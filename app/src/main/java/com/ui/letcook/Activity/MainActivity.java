package com.ui.letcook.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ui.letcook.R;

public class MainActivity extends LocalizationActivity{
     FirebaseAuth mAuth;
     Fragment home = new Fragment_home();
     Fragment add = new Fragment_add();
     Fragment me = new Fragment_me();

    final FragmentManager fm = getSupportFragmentManager();
//    Fragment active = home;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.search);

        Log.e("Tag","abc");

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setItemIconTintList(null);


        bottomNavigationView.setSelectedItemId(R.id.home);
//        Log.e("TAG",getCurrentLanguage().getLanguage());
//
//
//        Fragment_home fragment_home= new Fragment_home();
//        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout,fragment_home,"");
//        fragmentTransaction.commit();

        ((Fragment_me) me).setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                setLanguage(lang);

            }

        });
    }
    private void checkStatus(){
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
        }
        else {
            startActivity(new Intent(MainActivity.this,SignIn.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkStatus();
        super.onStart();
    }
//public void onBackPressed() {
//    moveTaskToBack(true);
//
//}
@Override
public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
        moveTaskToBack(true);
        return;
    }

    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    }, 1000);

}

boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

              switch (menuItem.getItemId()){
                  case R.id.home:


                      fm.beginTransaction().replace(R.id.frameLayout,home, "1").commit();

                      menuItem.setIcon(R.drawable.home2);
//                      fm.beginTransaction().hide(me).show(home).commit();
//                      active = home;

                      return true;
                  case R.id.add:


                      fm.beginTransaction().replace(R.id.frameLayout, add, "2").commit();
                      menuItem.setIcon(R.drawable.add2);
//                      fm.beginTransaction().hide(active).show(add).commit();
//                      active = add;
                      return true;

                  case R.id.acount :
                      fm.beginTransaction().replace(R.id.frameLayout, me, "3").commit();
                      menuItem.setIcon(R.drawable.user);
//                      fm.beginTransaction().hide(active).show(me).commit();
//                      active = me;
                      return true;
              }

              return true;
          }
      };
}
