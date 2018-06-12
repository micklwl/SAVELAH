//package com.example.junhu.savelah;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//
//import java.util.ArrayList;
//
//public class SharedListActivity extends AppCompatActivity {
//    private int nextIndex;
//    private EditText toAdd;
//    private EditText findList;
//    private ListView groceryList;
//    private ArrayList<String> list = new ArrayList<>();
//    private DatabaseReference mDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shared_list);
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(GroceryActivity.EXTRA_MESSAGE);
//        // Initalise widgets
//        toAdd = findViewById(R.id.addText);
//        findList = findViewById(R.id.findText);
//        groceryList = findViewById(R.id.groceryList);
//        registerForContextMenu(groceryList);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//        groceryList.setAdapter(adapter);
//    }
//
//    private void addGrocery() {
//        final String str = toAdd.getText().toString().trim();
//        mDatabase.child("recipe").child(nextIndex + "").setValue(str);
//        Log.d("hello", "addGrocery: " + str);
//    }
//
//    public void addGroceryListener(View view) {
//        addGrocery();
//    }
//}
