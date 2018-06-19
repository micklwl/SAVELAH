package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.junhu.savelah.dataObjects.Recipe;
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mashape.p.spoonacularrecipefoodnutritionv1.SpoonacularAPIClient;
import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.APICallBack;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpContext;
import com.mashape.p.spoonacularrecipefoodnutritionv1.models.DynamicResponse;

import java.text.ParseException;

public class SingleRecipeActivity extends AppCompatActivity {
    TextView instructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int id = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        TextView Textv = (TextView)findViewById(R.id.recipe_title);
        instructions = (TextView)findViewById(R.id.recipe_ins_f);
        if(extras != null) {
            String j = (String) extras.getString("title");
            id = Integer.valueOf(extras.getString("search_id"));
            Textv.setText(j);
        }

        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_grocery:
                        startActivity(new Intent(SingleRecipeActivity.this, GroceryActivity.class));
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(SingleRecipeActivity.this, RecipeActivity.class));
                        break;
                    case R.id.navigation_calendar:
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SingleRecipeActivity.this, ProfileActivity.class));
                        break;
                }
                return false;
            }
        });

        SpoonacularAPIClient client = new SpoonacularAPIClient();
        APIController clientController = client.getClient();
        clientController.getRecipeInformationAsync(id, new APICallBack<DynamicResponse>() {
            @Override
            public void onSuccess(HttpContext context, DynamicResponse response) {
                try {
                        /* DEBUG: TEST GSON:
                        System.out.println("RESPONSE-HEADERS: " + response.getHeaders());
                        System.out.println("RESPONSE-STRING: " + response.parseAsString());
                        // END-DEBUG */

                    Gson gson = new Gson();
                    Recipe singleRecipe = gson.fromJson(response.parseAsString(), Recipe.class);
                        /* DEBUG: TEST GSON:
                        * System.out.println("RESPONSE-GSON: " + view_single_recipe.getTitle());
                        // END-DEBUG */
                    showOnScreen(singleRecipe);
                } catch (ParseException e) {
                    Log.e("hello", "Parsing recipe information failed!\n" + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(HttpContext context, Throwable error) {
                Log.e("hello", "Getting recipe information failed! See below:\n" + error.getLocalizedMessage());
            }
        });
    }

    private void showOnScreen(Recipe singleRecipe) {
        instructions.setText(singleRecipe.getInstructions());
    }
}

