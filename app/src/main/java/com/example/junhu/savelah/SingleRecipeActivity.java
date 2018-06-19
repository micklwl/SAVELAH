package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junhu.savelah.dataObjects.Ingredient_Full;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_Full;
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mashape.p.spoonacularrecipefoodnutritionv1.SpoonacularAPIClient;
import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.APICallBack;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpContext;
import com.mashape.p.spoonacularrecipefoodnutritionv1.models.DynamicResponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.List;

public class SingleRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView instructions;
    TextView Textv;
    ImageView recipeImage;
    TextView recipeTime;
    TextView ingredients;
    TextView servings;
    String suffix;
    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int id = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            recipeName = (String) extras.getString("title");
            suffix = (String) extras.getString("suffix");
            id = Integer.valueOf(extras.getString("search_id"));
        }

        Textv = (TextView)findViewById(R.id.recipe_title);
        recipeImage = (ImageView) this.findViewById(R.id.recipeImage);
        instructions = (TextView)findViewById(R.id.recipe_ins_f);
        recipeTime = (TextView)findViewById(R.id.recipe_time_f);
        servings = (TextView)findViewById(R.id.recipeServes);
        ingredients = (TextView)findViewById(R.id.recipe_ing_f);



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
                    Recipe_Full singleRecipe = gson.fromJson(response.parseAsString(), Recipe_Full.class);
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

        findViewById(R.id.saveRecipe).setOnClickListener(this);
        findViewById(R.id.addList).setOnClickListener(this);


    }

    private void showOnScreen(Recipe_Full singleRecipe) {

        Textv.setText(recipeName);

        String imageUrl = "https://spoonacular.com/recipeImages/";
        if (suffix.endsWith(".jpg")) {
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpg";
        }
        else if (suffix.endsWith(".png")){
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.png";
        }
        else if (suffix.endsWith(".jpeg")){
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpeg";
        }
        Picasso.get().load(imageUrl).into(recipeImage);

        String time = String.valueOf(singleRecipe.getReadyInMinutes())+" Minutes";
        recipeTime.setText(time);

        String serve = String.valueOf(singleRecipe.getServings()) + " People";
        servings.setText(serve);

        ingredients.setText(readIngredients(singleRecipe.getExtendedIngredients()));

        String instruc = singleRecipe.getInstructions();
        if (instruc != null || !instruc.equals("null") ) {
            instructions.setText(instruc.replaceAll("<[^>]*>", "\n").trim());
        }
        else {
            String failInstruc = "No instructions was provided by Spoonacular!:(";
            instructions.setText(failInstruc);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveRecipe:

                break;
            case R.id.addList:

                break;
        }
    }

    private String readIngredients(List<Ingredient_Full> ingredientList) {
        String result = "";
        for (Ingredient_Full i : ingredientList) {
            if (i.getUnitShort().length() > 1) {
                result += i.getAmount() + " " + i.getUnitShort() + " " + i.getName() + "\n";
            } else {
                result += i.getOriginalString() + "\n";
            }
        }
        return result;
    }
}

