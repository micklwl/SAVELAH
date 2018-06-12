package com.example.junhu.savelah;

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
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;
import java.util.HashMap;

public class GroceryActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.junhu.savelah.GroceryActivity.MESSAGE";
    private EditText toAdd;
    private EditText findList;
    private ListView groceryList;
    private ArrayList<String> list = new ArrayList<>();
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        // Initalise widgets
        toAdd = findViewById(R.id.addText);
        findList = findViewById(R.id.findText);
        groceryList = findViewById(R.id.groceryList);
        registerForContextMenu(groceryList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        groceryList.setAdapter(adapter);
        Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
             //   nextIndex = c.getRecipe().size();
                list.clear();
                list.addAll(new ArrayList<String>(c.getRecipe().keySet()));
                Log.d("hello", "onDataChange: " + list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   // Toast.makeText(this, user.getEmail(), Toast.LENGTH_LONG).show();
        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(GroceryActivity.this, RecipeActivity.class)) ;
                        break;
                    case R.id.navigation_calendar:
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(GroceryActivity.this, ProfileActivity.class)) ;
                        break;
                }
                return false;
            }
        });
    }
    // to create options list to delete grocery
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery, menu);
    }

    private void deleteGrocery(int key) {
        String item = list.get(key);
        mDatabase.child("recipe").child(item).removeValue();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delGrocery :
             int position = info.position;
             deleteGrocery(position);
             return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void addGrocery() {
        final String str = toAdd.getText().toString().trim();
        mDatabase.child("recipe").child(str).setValue("default");
      //  mDatabase.child("recipe").child(nextIndex + "").setValue(str);
        Log.d("hello", "addGrocery: " + str);
    }

    public void addGroceryListener(View view) {
        addGrocery();
    }

    public void findListListener(View view) {
        final String sharedEmail = findList.getText().toString().trim();
        Query query = mDatabase.child("members").orderByValue().equalTo(sharedEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("onDataChanges", dataSnapshot.getValue() + "");
                if(dataSnapshot.exists()) {
                    Intent intent = new Intent(GroceryActivity.this, SharedListActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, sharedEmail);
                    startActivity(intent);
                } else{
                    Toast.makeText(GroceryActivity.this,"You do not have access to this email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shareListListener(View view) {
        final String emailToShare = findList.getText().toString().trim();
        Query query = initDatabase.orderByChild("email").equalTo(emailToShare);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Customer c = dataSnapshot.child("Users").getValue(DataSnapshot.class).getValue(Customer.class);
                    String ID = c.getUid();
                    Log.d("shareListLL", c.getEmail());
                //    mDatabase.child("members").child(ID).setValue(emailToShare);
                } else {
                    Toast.makeText(GroceryActivity.this,"No such user in SAVELAH", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}