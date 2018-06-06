package com.example.junhu.savelah;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroceryActivity extends AppCompatActivity {
    private EditText toAdd;
    private ListView groceryList;
    private EditText findList;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        // Initalise widgets
        toAdd = findViewById(R.id.addText);
        groceryList = findViewById(R.id.groceryList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        groceryList.setAdapter(adapter);
        registerForContextMenu(groceryList);
    }
    // to create options list to delete grocery
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery, menu);
    }
 // to fill up :
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addGrocery() {
        String str = toAdd.getText().toString().trim();
        if (!str.isEmpty()) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            String listID = mDatabase.push().getKey();
            if (listID != null) {
                mDatabase.child(listID).setValue(str);
            }
        } else {
            Toast.makeText(this,"Please enter grocery name", Toast.LENGTH_SHORT).show();
        }
    }

    ChildEventListener ls = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            list.add(dataSnapshot.getValue(String.class));
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void deleteGrocery(String key) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Grocery");
        mDatabase.child(key).removeValue();
    }

    public void addGroceryListener(View view) {
        addGrocery();
    }

    public void getList(){
        findList = findViewById(R.id.findText);
        String str = findList.getText().toString().trim();
    }

    public void findListListener(View view) {
        getList();
    }
}
