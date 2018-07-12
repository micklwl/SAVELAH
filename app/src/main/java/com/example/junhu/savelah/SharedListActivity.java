package com.example.junhu.savelah;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.DatePickerFragment;
import com.example.junhu.savelah.dataObjects.Ingredient;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SharedListActivity extends AppCompatActivity {
    private ListView ListOfemails;
    private ArrayList<String> list = new ArrayList<>();
    private DatabaseReference initDatabase;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private HashMap<String,String> emailToUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());
        ListOfemails = findViewById(R.id.userEmails);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListOfemails.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c.getAccess() != null) {
                    list.clear();
                    ArrayList<String> temp = new ArrayList<>();
                    HashMap<String, String> map = c.getAccess();
                    emailToUID = map;
                    for (String s : map.keySet()) {
                        s.replace(",", ".");
                        temp.add(s);
                    }
                    list.addAll(temp);
                    // list.addAll(new ArrayList<String>(c.getList().keySet()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ListOfemails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = (String) ListOfemails.getItemAtPosition(position);
                String decoded = email.replace(".", ",");
                Intent intent = new Intent(SharedListActivity.this, ListOfOtherUser.class);
                intent.putExtra("UID", emailToUID.get(decoded));
                startActivity(intent);
            }
        });
        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_grocery:
                    startActivity(new Intent(SharedListActivity.this, GroceryActivity.class));
                    break;
                case R.id.navigation_recipe:
                    startActivity(new Intent(SharedListActivity.this, RecipeActivity.class)) ;
                    break;
                case R.id.navigation_calendar:
                    startActivity(new Intent(SharedListActivity.this, CalendarActivity.class));
                    break;
                case R.id.navigation_profile:
                    startActivity(new Intent(SharedListActivity.this, ProfileActivity.class)) ;
                    break;
                case R.id.sharing:
                    startActivity(new Intent(SharedListActivity.this, SharingActivity.class));
            }
            return false;
            }
        });
    }


}
