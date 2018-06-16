package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.junhu.savelah.dataObjects.Ingredient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;

public class SharedListActivity extends AppCompatActivity {
    private EditText toAdd;
    private ListView groceryList;
    private ArrayList<String> list = new ArrayList<>();
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);
        Intent intent = getIntent();
        String message = intent.getStringExtra(GroceryActivity.EXTRA_MESSAGE);
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(message);
        toAdd = findViewById(R.id.addText);
        groceryList = findViewById(R.id.shared);
        registerForContextMenu(groceryList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        groceryList.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                list.clear();
                list.addAll(new ArrayList<String>(c.getRecipe().keySet()));
                Log.d("hello", "onDataChange: " + list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                    break;
                case R.id.navigation_recipe:
                    startActivity(new Intent(SharedListActivity.this, RecipeActivity.class)) ;
                    break;
                case R.id.navigation_calendar:
                    break;
                case R.id.navigation_profile:
                    startActivity(new Intent(SharedListActivity.this, ProfileActivity.class)) ;
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
        mDatabase.child("recipe").child(str).setValue(new Ingredient(str, "default", 1));
        //  mDatabase.child("recipe").child(nextIndex + "").setValue(str);
        Log.d("hello", "addGrocery: " + str);
    }

    public void addGroceryListener(View view) {
        addGrocery();
    }
}
