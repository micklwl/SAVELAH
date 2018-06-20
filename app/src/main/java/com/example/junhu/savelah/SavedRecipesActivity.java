package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SavedRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_grocery:
                        startActivity(new Intent(SavedRecipesActivity.this, GroceryActivity.class));
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(SavedRecipesActivity.this, RecipeActivity.class));
                        break;
                    case R.id.navigation_calendar:
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SavedRecipesActivity.this, ProfileActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}
