package com.example.junhu.savelah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroceryActivity extends AppCompatActivity {
    private EditText toAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        // Initalise widgets
        toAdd = findViewById(R.id.addText);
    }

    private void addGrocery() {
        String str = toAdd.getText().toString().trim();
        if (!str.isEmpty()) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Grocery");
            mDatabase.setValue(str);
        } else {
            Toast.makeText(this,"Please enter grocery name", Toast.LENGTH_SHORT).show();
        }
    }

    public void addGroceryListener(View view) {
        addGrocery();
    }
}
