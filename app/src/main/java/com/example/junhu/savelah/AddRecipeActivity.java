package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhu.savelah.adapter.IngredientAdapter;
import com.example.junhu.savelah.dataObjects.Ingredient;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText titleText;
    private EditText servingsText;
    private EditText timeText;
    private EditText instructionsText;
    private String error_msg = "";
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private IngredientAdapter ingredientAdapter;


    private Recipe_DB recipeDb;
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        titleText = (EditText)findViewById(R.id.edit_add_custom_recipe_title);
        servingsText = (EditText)findViewById(R.id.edit_add_custom_recipe_servings);
        timeText = (EditText)findViewById(R.id.edit_add_custom_recipe_minutes);
        mRecyclerView   = (RecyclerView) findViewById(R.id.rv_add_custom_recipe_ingredients);
        progressBar = (ProgressBar)findViewById(R.id.save_recipe_progress);
        instructionsText = (EditText)findViewById(R.id.edit_add_custom_recipe_instructions);
        ingredientAdapter     = new IngredientAdapter(this);
        mRecyclerView.setAdapter(ingredientAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_custom_recipe_ing).setOnClickListener(this);
        findViewById(R.id.lbl_add_custom_recipe_ingredients).setOnClickListener(this);
        findViewById(R.id.btn_add_custom_recipe).setOnClickListener(this);
        findViewById(R.id.lbl_add_custom_recipe_instructions).setOnClickListener(this);

    /*    BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        startActivity(new Intent(AddRecipeActivity.this, GroceryActivity.class)) ;
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(AddRecipeActivity.this, RecipeActivity.class)) ;
                        break;
                    case R.id.navigation_calendar:
                        startActivity(new Intent(AddRecipeActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(AddRecipeActivity.this, ProfileActivity.class)) ;
                        break;
                }
                return false;
            }
        }); */
    }

    @Override
    public void onClick(View view) {
        View gist;
        switch(view.getId()){
            case R.id.lbl_add_custom_recipe_ingredients:
                ingredientAdapter.addBlankItem();
                break;

            case R.id.btn_add_custom_recipe:
                progressBar.setVisibility(View.VISIBLE);
                saveRecipe();
                break;

            case R.id.lbl_add_custom_recipe_instructions:
                gist = findViewById(R.id.edit_add_custom_recipe_instructions);
                if (gist.isShown()) {
                    ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
                    gist.setVisibility(View.GONE);
                } else {
                    ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                    gist.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_custom_recipe_ing:
                gist = findViewById(R.id.rv_add_custom_recipe_ingredients);
                if (gist.isShown()) {
                    ((ImageButton) view).setImageResource(R.drawable.ic_arrow_up);
                    gist.setVisibility(View.GONE);
                } else {
                    ((ImageButton) view).setImageResource(R.drawable.ic_arrow_down);
                    gist.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void saveRecipe() {
        String title = titleText.getText().toString().trim();
        String servings = servingsText.getText().toString().trim();
        String time = timeText.getText().toString().trim();
        String instructions = instructionsText.getText().toString().trim();
        List<Ingredient> ingredients = getRecipeIngredients();
        HashMap<String, Ingredient> ingList = new HashMap<>();
        if (ingredients!= null) {
            for (Ingredient i : ingredients) {
                ingList.put(i.getName(),i);
            }
        }

        if (title.length() < 5)
            error_msg += "Error: You need to provide a descriptive TITLE!\n";

        if (instructions.length() < 5)
            error_msg += "Error: You need to provide some instructions!\n";


        int servingsInt = 0;
        if (servings.length() > 0) {
            try {
                servingsInt = Integer.valueOf(servings);
            } catch (NumberFormatException ex) {
                error_msg += "Error: SERVINGS need to be a whole number!\n";
            }
        } else { error_msg += "Error: We need to know how many SERVINGS this recipe makes!\n"; }

        // Checking for ReadyInMinutes given + of correct format:
        int     readyInMinutes  = 0;
        if (time.length() > 0) {
            try {
                readyInMinutes = Integer.valueOf(time);
            } catch (NumberFormatException ex) {
                error_msg += "Error: TIME REQUIRED needs to be a number of minutes!\n";
            }
        } else { error_msg += "Error: We need to know how long TIME REQUIRED to cook is!\n"; }

        if (error_msg.length() ==0){
            user = FirebaseAuth.getInstance().getCurrentUser();
            recipeDb = new Recipe_DB(title,0,"test",readyInMinutes,servingsInt,ingList,instructions);
            uploadRecipe(recipeDb);
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddRecipeActivity.this,error_msg, Toast.LENGTH_LONG).show();
        }
    }

    private void uploadRecipe(Recipe_DB recipeDb) {
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        initDatabase.child(user.getUid()).child("recipes").child(recipeDb.getTitle()).setValue(recipeDb);
        progressBar.setVisibility(View.GONE);
        finish();
        startActivity(new Intent(AddRecipeActivity.this, RecipeActivity.class)) ;
    }

    private List<Ingredient> getRecipeIngredients() {
        // Prepare a list for our Ingredients:
        List<Ingredient> ingredientList = new ArrayList<>();

        // Loop through RecyclerView and get the Ingredient details:
        for (int i=0; i < mRecyclerView.getChildCount(); i++) {
            IngredientAdapter.ViewHolder vh = (IngredientAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(i);

            Ingredient ingredient = vh.getIngredient();

            if (ingredient == null) {
                return null;
            }

            ingredientList.add(ingredient);
            ///* DEBUG: */ Log.d(TAG, "Added Ingredient: " + ingredient.toString()); /* END-DEBUG */
        }
        return  ingredientList;
    }
}
