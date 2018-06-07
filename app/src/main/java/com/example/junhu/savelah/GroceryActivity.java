package com.example.junhu.savelah;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroceryActivity extends AppCompatActivity {
    private EditText toAdd;
    private EditText findList;
    private ListView groceryList;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        // Initalise widgets
        toAdd = findViewById(R.id.addText);
        findList = findViewById(R.id.findText);
        groceryList = findViewById(R.id.groceryList);
        registerForContextMenu(groceryList);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//        groceryList.setAdapter(adapter);
        user = FirebaseAuth.getInstance().getCurrentUser();
  //      Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
    }
    // to create options list to delete grocery
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery, menu);
    }

    private void deleteGrocery(String key) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Grocery");
        mDatabase.child(key).removeValue();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delGrocery :
             int position = info.position;
             deleteGrocery(list.get(position));
            default:
                return super.onContextItemSelected(item);
        }
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

    public void addGroceryListener(View view) {
        addGrocery();
    }

    ChildEventListener ls = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            list.add(dataSnapshot.getValue(String.class));
           // adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            list.remove(dataSnapshot.getValue(String.class));
          //  adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void getList(){
        findList = findViewById(R.id.findText);
        String str = findList.getText().toString().trim();
    }

    public void findListListener(View view) {
        getList();
    }
}