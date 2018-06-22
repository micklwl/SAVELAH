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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(RecipeSearchActivity.this, ProfileActivity.class));
                        break;
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
                        doSearch(false);
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
                intent.putExtra("title",selectedRecipe.getTitle());
                intent.putExtra("suffix",selectedRecipe.getImage());
                intent.putExtra("search_id",selectedRecipe.getIdString());
                intent.putExtra("type", "false");
                Log.d("hello", selectedRecipe.getIdString());
                startActivity(intent);
            }
        });
    }

    private void doSearch(boolean nextSet) {
        final SpoonacularAPIClient client = new SpoonacularAPIClient();
        APIController clientController = client.getClient();
        progressBar.setVisibility(View.VISIBLE);
        // key-value map for optional query parameters
        Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
        // Invoking the API call with sample inputs
        query = searchItem.getText().toString();
        cuisine = "";
        dietQuery = "";
        excludeIngredients = "";
        intolerances = "";
        offset = 0;
        type = "";
        clientController.searchRecipesAsync(query, cuisine, dietQuery, excludeIngredients, intolerances, limitLicense, resultNumber, offset, type, queryParams, new myAPICallBack(nextSet));
    }


    private class myAPICallBack implements APICallBack<DynamicResponse> {

        // Boolean for the type of query we are making (new or next).
        private boolean nextSet;

        myAPICallBack(boolean nextSet) {
            this.nextSet = nextSet;
        }

        @Override
        public void onSuccess(HttpContext context, DynamicResponse response) {
            // Hide spinner:
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_SHORT).show();
            try {
                // Convert resulting query into a set of Http result data (matching API):
                HTTP_RecipeShort handler = new HTTP_RecipeShort(response.parseAsString());

                // Get the recipe results:
                List<Recipe_Short> data = handler.getResults();

                List<Recipe> temp = new ArrayList<Recipe>();
                results.clear();
                for (Recipe_Short i : data) {
                    String title = i.getTitle();
                    String id = String.valueOf(i.getId());
                    String suffix = i.getImage();
                    String urlFinal = "https://spoonacular.com/recipeImages/";
                    if (suffix.endsWith(".jpg")) {
                        urlFinal = urlFinal + id + "-556x370.jpg";
                    }
                    else if (suffix.endsWith(".png")){
                        urlFinal = urlFinal + id + "-556x370.png";
                    }
                    else if (suffix.endsWith(".jpeg")){
                        urlFinal = urlFinal+ id + "-556x370.jpeg";
                    }
                    Log.d("lol",title);
                    temp.add(new Recipe(title, urlFinal,i.getId()));
                }
                results.addAll(temp);
                adapter.notifyDataSetChanged();
                // Load data in handler:
             //   PocketKitchenData pkData = PocketKitchenData.getInstance();
                if (!nextSet) {
               //     pkData.setRecipesDisplayed(data);
                } else {
              //      pkData.addRecipesDisplayed(data);
                }

                // Provides proper feedback when no results are returned:
                if (data.size() == 0) {
                    ((TextView) recipeResults.getEmptyView()).setText("No Results");
                }
            } catch (ParseException e) { // Thrown by parseAsString()
                Toast.makeText(getApplicationContext(), "Failed to parse", Toast.LENGTH_SHORT).show();
            }

            }
        @Override
        public void onFailure(HttpContext context, Throwable error) {
            // Unknown error. Originating from API or Mashape Key most likely.
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Recipe query failed", Toast.LENGTH_SHORT).show();
        }
    }
}
