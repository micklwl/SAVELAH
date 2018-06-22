package com.example.junhu.savelah;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junhu.savelah.dataObjects.Ingredient;
import com.example.junhu.savelah.dataObjects.Ingredient_Full;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.example.junhu.savelah.dataObjects.Recipe_Full;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView instructions;
    private TextView Textv;
    private ImageView recipeImage;
    private TextView recipeTime;
    private TextView ingredients;
    private TextView servings;
    private Button saveButton;
    private String suffix;
    private String recipeName;
    private Recipe_Full singleRecipe;
    private boolean type;
    private int id = 0;
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference mRecipes;
    private StorageReference mStorageRef;
    private String save = "Save";
    private String unSave = "UnSave";
    private Recipe_DB rDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            recipeName = (String) extras.getString("title");
            suffix = (String) extras.getString("suffix");
            id = Integer.valueOf(extras.getString("search_id"));
            type = Boolean.valueOf(extras.getString("type"));
        }

        Textv = (TextView)findViewById(R.id.recipeTitle);
        recipeImage = (ImageView) this.findViewById(R.id.recipeImage);
        instructions = (TextView)findViewById(R.id.recipeInstructionsList);
        recipeTime = (TextView)findViewById(R.id.recipeTime);
        servings = (TextView)findViewById(R.id.recipeServes);
        ingredients = (TextView)findViewById(R.id.recipeIngredientsList);
        saveButton = (Button)findViewById(R.id.saveRecipe);

        user = FirebaseAuth.getInstance().getCurrentUser();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());


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
        if (type){
            searchFromDatabase();
            saveButton.setText(unSave);
        }
        else{
            searchAPI();
        }
        findViewById(R.id.saveRecipe).setOnClickListener(this);
        findViewById(R.id.addList).setOnClickListener(this);
    }

    private void searchFromDatabase() {
        mRecipes = mDatabase.child("recipes").child(recipeName);
        mRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rDB = dataSnapshot.getValue(Recipe_DB.class);
                Textv.setText(recipeName);
                Picasso.get().load(rDB.getImageUrl()).into(recipeImage);

                String time = String.valueOf(rDB.getReadyInMinutes()) + " Minutes";
                recipeTime.setText(time);

                String serve = String.valueOf(rDB.getServings()) + " People";
                servings.setText(serve);

                HashMap<String, Ingredient> ingList = new HashMap<String, Ingredient>();
                ingList = rDB.getIngList();
                ingredients.setText(readIngredientsDB(ingList));

                String instruc = rDB.getInstructions();
                if (instruc != null) {
                    //Log.d("test", instruc);
                    instructions.setText(instruc.replaceAll("<[^>]*>", "\n").trim());
                }
                else {
                    String failInstruc = "No instructions was provided by Spoonacular! :(";
                    instructions.setText(failInstruc);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String readIngredientsDB(HashMap<String, Ingredient> ingList) {
        String result = "";
        if (ingList != null) {
            for (Map.Entry<String, Ingredient> entry : ingList.entrySet()) {
                Ingredient value = entry.getValue();
                if (value.getUnit().length() > 1) {
                    result += value.getAmount() + " " + value.getUnit() + " " + value.getName() + "\n";
                }
            }
        }
        return result;
    }

    private void searchAPI() {
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
                    singleRecipe = gson.fromJson(response.parseAsString(), Recipe_Full.class);
                        /* DEBUG: TEST GSON:
                        * System.out.println("RESPONSE-GSON: " + view_single_recipe.getTitle());
                        // END-DEBUG */
                    showAPIOnScreen(singleRecipe);
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

    private void showAPIOnScreen(Recipe_Full singleRecipe) {
        Textv.setText(recipeName);

        String imageUrl = "https://spoonacular.com/recipeImages/";
        if (suffix.endsWith(".jpg")) {
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpg";
        } else if (suffix.endsWith(".png")) {
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.png";
        } else if (suffix.endsWith(".jpeg")) {
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpeg";
        }
        Picasso.get().load(imageUrl).into(recipeImage);

        String time = String.valueOf(singleRecipe.getReadyInMinutes()) + " Minutes";
        recipeTime.setText(time);

        String serve = String.valueOf(singleRecipe.getServings()) + " People";
        servings.setText(serve);

        ingredients.setText(readIngredients(singleRecipe.getExtendedIngredients()));

        String instruc = singleRecipe.getInstructions();

        if (instruc != null) {
            Log.d("test", instruc);
            instructions.setText(instruc.replaceAll("<[^>]*>", "\n").trim());
        }
        else {
            String failInstruc = "No instructions was provided by Spoonacular! :(";
            instructions.setText(failInstruc);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveRecipe:
                if (type){
                    if (saveButton.getText().toString().equalsIgnoreCase("unSave")){
                        mDatabase.child("recipes").child(recipeName).removeValue();
                        saveButton.setText(save);
                        break;
                    }
                    else if (saveButton.getText().toString().equalsIgnoreCase("Save")) {
                        mDatabase.child("recipes").child(rDB.getTitle()).setValue(rDB);
                        saveButton.setText(unSave);
                        break;
                    }
                }
                else {
                    if (saveButton.getText().toString().equalsIgnoreCase("Save")) {
                        Recipe_DB recipe_db = new Recipe_DB(singleRecipe, suffix);
                        mDatabase.child("recipes").child(recipe_db.getTitle()).setValue(recipe_db);
                        saveButton.setText(unSave);
                        break;
                    }
                    else if (saveButton.getText().toString().equalsIgnoreCase("unSave")){
                        mDatabase.child("recipes").child(recipeName).removeValue();
                        saveButton.setText(save);
                        break;
                    }
                }
            case R.id.addList:
                if (type){
                    HashMap<String, Ingredient> ingList = rDB.getIngList();
                    for (Map.Entry<String, Ingredient> entry : ingList.entrySet()) {
                        Ingredient value = entry.getValue();
                        addToList(value);
                    }
                    break;
                }
                else {
                    List<Ingredient_Full> ingredientsFull = singleRecipe.getExtendedIngredients();
                    for (Ingredient_Full ing: ingredientsFull){
                        Ingredient ing1 = new Ingredient(ing);
                        addToList(ing1);
                    }
                    break;
                }
        }
    }

    private void addToList(final Ingredient ing) {
        mDatabase.child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ing.getName()).exists()){
                    Ingredient ingredientDB = dataSnapshot.child(ing.getName()).getValue(Ingredient.class);
                    if (ingredientDB.getUnit().equals(ing.getUnit())){
                        float endAmount = ing.getAmount()+ ingredientDB.getAmount();
                        mDatabase.child("list").child(ing.getName()).child("amount").setValue(endAmount);
                    }
                    else {
                        // ingredient present but unit on list and unit inside DB diff

                    }
                }
                else {
                    mDatabase.child("list").child(ing.getName()).setValue(ing);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String readIngredients(List<Ingredient_Full> ingredientList) {
        String result = "";
        for (Ingredient_Full i : ingredientList) {
            if (i.getUnit().length() > 1) {
                result += i.getAmount() + " " + i.getUnit() + " " + i.getName() + "\n";
            } else {
                result += i.getOriginalString() + "\n";
            }
        }
        return result;
    }
}

