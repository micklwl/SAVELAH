package com.example.junhu.savelah;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.junhu.savelah.adapter.FoodAdapter;
import com.example.junhu.savelah.adapter.CustomListAdapter;
import com.example.junhu.savelah.dataObjects.HTTP_RecipeShort;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_Short;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mashape.p.spoonacularrecipefoodnutritionv1.SpoonacularAPIClient;
import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.APICallBack;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpContext;
import com.mashape.p.spoonacularrecipefoodnutritionv1.models.DynamicResponse;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeSearchActivity extends AppCompatActivity{
    private EditText searchItem;
    private ListView recipeResults;
    private ProgressBar progressBar;
    private String intolerances;
    private String dietQuery;
    private String cuisine;
    private String query;
    private String excludeIngredients;
    private static final boolean    limitLicense = false;
    private static final int    resultNumber = 20;
    private int offset;
    private String type;
    private ArrayList<Recipe> results;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_grocery:
                        startActivity(new Intent(RecipeSearchActivity.this, GroceryActivity.class));
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(RecipeSearchActivity.this, RecipeActivity.class));
                        break;
                    case R.id.navigation_calendar:
                        startActivity(new Intent(RecipeSearchActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(RecipeSearchActivity.this, ProfileActivity.class));
                        break;
                    case R.id.sharing:
                        startActivity(new Intent(RecipeSearchActivity.this, SharingActivity.class));
                }
                return false;
            }
        });
        recipeResults = (ListView) findViewById(R.id.listOfRecipes);
        results = new ArrayList<Recipe>();
        adapter = new CustomListAdapter(getApplicationContext(), R.layout.recipe_search_list, results);
        recipeResults.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        searchItem = (EditText) findViewById(R.id.groceryItem);
        Button queryButton = (Button) findViewById(R.id.queryButton);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.queryButton:
                        if (!searchItem.getText().toString().isEmpty()) {
                            doSearch();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Empty search query! Key in something!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        // Configuration parameters
        com.mashape.p.spoonacularrecipefoodnutritionv1.Configuration.initialize(this.getBaseContext());

        recipeResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecipeSearchActivity.this, SingleRecipeActivity.class);
                Recipe selectedRecipe =  (Recipe)recipeResults.getItemAtPosition(position);
                intent.putExtra("Recipe", new Recipe(selectedRecipe.getTitle(),
                        selectedRecipe.getImage(), selectedRecipe.getId()));
                startActivity(intent);
            }
        });
    }

    private void doSearch() {
        final SpoonacularAPIClient client = new SpoonacularAPIClient();
        APIController clientController = client.getClient();
        progressBar.setVisibility(View.VISIBLE);
        // key-value map for optional query parameters
        Map<String, Object> queryParams = new LinkedHashMap<>();
        // Invoking the API call with sample inputs
        query = searchItem.getText().toString();
        cuisine = "";
        dietQuery = "";
        excludeIngredients = "";
        intolerances = "";
        offset = 0;
        type = "";
        clientController.searchRecipesAsync(query, cuisine, dietQuery, excludeIngredients, intolerances, limitLicense, resultNumber, offset, type, queryParams, new myAPICallBack());
    }


    private class myAPICallBack implements APICallBack<DynamicResponse> {

        @Override
        public void onSuccess(HttpContext context, DynamicResponse response) {
            // Hide spinner:
            progressBar.setVisibility(View.GONE);

            Map<String, String> example = response.getHeaders();
            for (Map.Entry<String, String> entry : example.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d("mapping", key + " " + value);
            }

            String valid = null;
            try {
                valid = IOUtils.toString(response.getRawBody(), "UTF-8");
                Log.d("initial", valid);
            } catch (IOException e) {
                Toast.makeText(RecipeSearchActivity.this,"Error in conversion! Press back and try again.", Toast.LENGTH_SHORT).show();
            }

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(valid);
            } catch (JSONException e) {
                Toast.makeText(RecipeSearchActivity.this,"Error in conversion! Press back and try again.", Toast.LENGTH_SHORT).show();
            }

            HTTP_RecipeShort handler = new HTTP_RecipeShort(jObject);

            // Get the recipe results:
            List<Recipe_Short> data = handler.getResults();
            Log.d("initial", "reached");
            List<Recipe> temp = new ArrayList<>();
            results.clear();
            for (Recipe_Short i : data) {
                Log.d("initial", "empty");
                String title = i.getTitle();
                String id = String.valueOf(i.getId());
                String suffix = i.getImage();
                String urlFinal = "https://spoonacular.com/recipeImages/";
                if (suffix.endsWith(".jpg")) {
                    urlFinal = urlFinal + id + "-556x370.jpg";
                } else if (suffix.endsWith(".png")) {
                    urlFinal = urlFinal + id + "-556x370.png";
                } else if (suffix.endsWith(".jpeg")) {
                    urlFinal = urlFinal + id + "-556x370.jpeg";
                }
                Log.d("lol", title);
                temp.add(new Recipe(title, urlFinal, i.getId()));
            }
            results.addAll(temp);
            adapter.notifyDataSetChanged();

            // Provides proper feedback when no results are returned:
            if (data.size() == 0) {
                String empty = "No results";
                ((TextView) recipeResults.getEmptyView()).setText(empty);
            }
        }
        @Override
        public void onFailure(HttpContext context, Throwable error) {
            // Unknown error. Originating from API or Mashape Key most likely.
            progressBar.setVisibility(View.GONE);
            Log.d("Reason", error.getMessage());
            Toast.makeText(getApplicationContext(), "Recipe query failed", Toast.LENGTH_SHORT).show();
        }
    }


}


