package com.example.junhu.savelah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        startActivity(new Intent(RecipeActivity.this, GroceryActivity.class)) ;
                        break;
                    case R.id.navigation_recipe:
                        break;
                    case R.id.navigation_calendar:

                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(RecipeActivity.this, ProfileActivity.class)) ;
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.searchButton).setOnClickListener(this);
        findViewById(R.id.myRecipesButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.searchButton:
                startActivity(new Intent(this, RecipeSearchActivity.class));
                break;
            case R.id.myRecipesButton:
                startActivity(new Intent(this, SavedRecipesActivity.class));
                break;
        }
    }
}
