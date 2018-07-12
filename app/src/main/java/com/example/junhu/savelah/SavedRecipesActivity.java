package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.junhu.savelah.adapter.SavedRecipesAdapter;
import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SavedRecipesActivity extends AppCompatActivity implements View.OnClickListener  {
    private ListView recipeResults;
    private ArrayList<Recipe_DB> results;
    private SavedRecipesAdapter adapter;

    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
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
                        startActivity(new Intent(SavedRecipesActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SavedRecipesActivity.this, ProfileActivity.class));
                        break;
                    case R.id.sharing:
                        startActivity(new Intent(SavedRecipesActivity.this, SharingActivity.class));
                }
                return false;
            }
        });


        results = new ArrayList<>();
        adapter = new SavedRecipesAdapter(getApplicationContext(), R.layout.recipe_search_list, results);
        recipeResults = (ListView) findViewById(R.id.listOfRecipes);
        recipeResults.setAdapter(adapter);


        user = FirebaseAuth.getInstance().getCurrentUser();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c != null) {
                    results.clear();
                    HashMap<String, Recipe_DB> map = c.getRecipes();
                    ArrayList<Recipe_DB> temp = new ArrayList<>();
                    if (map != null) {
                        for (Map.Entry<String, Recipe_DB> entry : map.entrySet()) {
                            Recipe_DB one = entry.getValue();
                            //Ingredient value = entry.getValue();
                            temp.add(one);
                        }
                        results.addAll(temp);
                        //Log.d("hello", "onDataChange: " + list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recipeResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SavedRecipesActivity.this, SingleRecipeActivity.class);
                Recipe_DB selectedRecipe =  (Recipe_DB)recipeResults.getItemAtPosition(position);
                Recipe temp = new Recipe(selectedRecipe.getTitle(), selectedRecipe.getImageUrl(), selectedRecipe.getId());
                intent.putExtra("Recipe", temp);
//                intent.putExtra("title",selectedRecipe.getTitle());
//                intent.putExtra("suffix",selectedRecipe.getImageUrl());
//                intent.putExtra("search_id",String.valueOf(selectedRecipe.getId()));
                intent.putExtra("type","true");
               // Log.d("hello", selectedRecipe.getIdString());
                startActivity(intent);
            }
        });

        findViewById(R.id.savedRecipes).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.savedRecipes:
                startActivity(new Intent(SavedRecipesActivity.this, AddRecipeActivity.class));
                break;
        }
    }
}
