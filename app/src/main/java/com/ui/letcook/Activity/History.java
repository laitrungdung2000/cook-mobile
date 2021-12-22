package com.ui.letcook.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ui.letcook.Model.Dish;
import com.ui.letcook.R;

import java.util.ArrayList;
import java.util.List;

public class History extends LocalizationActivity {
    ListView listView;
    ArrayList<String> arrayList;
    DatabaseReference databaseReference,del;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private List<Dish> mUploads;
    Dish dish = new Dish();
    ArrayAdapter arrayAdapter;
    Button back;
    //
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        listView= (ListView) findViewById(R.id.listhistory);
        arrayList= new ArrayList<>();
        del = FirebaseDatabase.getInstance().getReference("Dish");
        loadhistory();
        loaddish();
        back=findViewById(R.id.bachistory);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getdatasavedish(listView.getItemAtPosition(position).toString());
                Intent intent= new Intent(History.this, RecipeActivity.class);

                intent.putExtra("id",dish.getId());

                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getdatasavedish(listView.getItemAtPosition(position).toString());
                final int which_item=position;
                new AlertDialog.Builder(History.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle(getString(R.string.are_u_sure))
                        .setMessage(getString(R.string.do_u_want_delete))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                del.child(dish.getId()).setValue(null);
//                                arrayList.remove(which_item);
//                                arrayAdapter.notifyDataSetChanged();


                            }
                        })
                .setNegativeButton(getString(R.string.no),null)
                .show();
                return true;
            }
        });
    }

    public void loadhistory(){


        databaseReference = FirebaseDatabase.getInstance().getReference("Dish");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Dish dish = postSnapshot.getValue(Dish.class);
                    if(dish.getEmailuser().contains(firebaseUser.getEmail())){
                        arrayList.add(dish.getNamedish());
                    }}


                 arrayAdapter= new ArrayAdapter(History.this,android.R.layout.simple_list_item_1,arrayList);
                arrayAdapter.notifyDataSetChanged();
                 listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
    });}
    public  void  getdatasavedish( final String a){
        for(int i=0;i< mUploads.size();i++){
            if(a.equals(mUploads.get(i).getNamedish())){
                dish=mUploads.get(i);
            }
        }
    }
    public void loaddish(){
        mUploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Dish");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Dish dish = postSnapshot.getValue(Dish.class);
                    mUploads.add(dish);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
