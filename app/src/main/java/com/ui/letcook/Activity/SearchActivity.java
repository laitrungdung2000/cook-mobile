package com.ui.letcook.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ui.letcook.Model.Dish;
import com.ui.letcook.Adapter.DishAdapterSearch;
import com.ui.letcook.R;


import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends LocalizationActivity {
    private RecyclerView mRecyclerView;
    private DishAdapterSearch mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Dish> mUploads;
    String a="";
    String b="";
    EditText search;
    Button back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Fragment_me me = new Fragment_me();
        me.setiLanguages(new iLanguages() {
            @Override
            public void changeLanguage(String lang) {
                Log.e("tag",lang);
                setLanguage(lang);

            }

        });
        LinearLayoutManager layoutManager= new LinearLayoutManager(SearchActivity.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        mRecyclerView = findViewById(R.id.recyclerviewSearch);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        back=findViewById(R.id.backsearch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search= (EditText) findViewById(R.id.searchdish);

        b=getIntent().getStringExtra("name");
        searchDish(b);
        search.setText(b);
        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                System.out.println("AAAA");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                        searchDish(search.getText().toString());
                    return true;
                }
                return false;

            }
        });

//        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if ( (i == EditorInfo.IME_ACTION_DONE) || ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN ))){
//                    searchDish(search.getText().toString());
//                    return true;
//                }
//                else{
//                    return false;
//                }
//            }
//        });
//
//        search.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//                Log.e("search","AAAAA");
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//                        searchDish(search.getText().toString());
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });


    }
    private void searchDish(final String search){
        mUploads = new ArrayList<>();
        mUploads.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Dish");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Dish dish = postSnapshot.getValue(Dish.class);
                    Log.v("Dish: ", dish.getNguyenlieu());
                    if(convert(dish.getNamedish().toLowerCase()).contains(search.toLowerCase())
                            || convert(dish.getNguyenlieu().toLowerCase()).contains(search.toLowerCase())
                            || convert(dish.getMake().toLowerCase()).contains(search.toLowerCase())){
                        mUploads.add(dish);
                    }
                   }

                mAdapter = new DishAdapterSearch(SearchActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static String convert(String str) {
        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???", "a");
        str = str.replaceAll("??|??|???|???|???|??|???|???|???|???|???", "e");
        str = str.replaceAll("??|??|???|???|??", "i");
        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???", "o");
        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???", "u");
        str = str.replaceAll("???|??|???|???|???", "y");
        str = str.replaceAll("??", "d");

        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???", "A");
        str = str.replaceAll("??|??|???|???|???|??|???|???|???|???|???", "E");
        str = str.replaceAll("??|??|???|???|??", "I");
        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???", "O");
        str = str.replaceAll("??|??|???|???|??|??|???|???|???|???|???", "U");
        str = str.replaceAll("???|??|???|???|???", "Y");
        str = str.replaceAll("??", "D");
        return str;
    }

}
