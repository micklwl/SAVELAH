package com.example.junhu.savelah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SharedListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);
        Intent intent = getIntent();
        String message = intent.getStringExtra(GroceryActivity.EXTRA_MESSAGE);
    }

    public void addGroceryListener(View view) {
    }
}
