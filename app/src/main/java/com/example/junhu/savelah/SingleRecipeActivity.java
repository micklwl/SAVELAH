package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mashape.p.spoonacularrecipefoodnutritionv1.SpoonacularAPIClient;
import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.APICallBack;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpContext;
import com.mashape.p.spoonacularrecipefoodnutritionv1.models.DynamicResponse;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleRecipeActivity extends AppCompatActivity implements ChangeQuantityDialog.ChangeQuantityDialogListener {
    private TextView instructions;
    private TextView Textv;
    private ImageView recipeImage;
    private TextView recipeTime;
    private TextView ingredients;
    private TextView servings;
    private Button saveButton;
    private Button addButton;
    private Button editButton;
    private String suffix;
    private String recipeName;
    private Recipe_Full singleRecipe;
    private boolean type; // true for accessing from SavedRecipes while false for accessing from RecipesSearch
    private int id = 0;
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference mRecipes;
    private String save = "Save";
    private String unSave = "UnSave";
    private Recipe_DB rDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        Intent intent = getIntent();
        Recipe extras = intent.getParcelableExtra("Recipe");
        Bundle extras_plus = intent.getExtras();
        if(extras_plus != null) {
            type = Boolean.valueOf(extras_plus.getString("type"));
            recipeName = extras.getTitle();
            suffix = extras.getImage();
            id = extras.getId();
        }

        Textv = (TextView)findViewById(R.id.recipeTitle);
        recipeImage = (ImageView) this.findViewById(R.id.recipeImage);
        instructions = (TextView)findViewById(R.id.recipeInstructionsList);
        recipeTime = (TextView)findViewById(R.id.recipeTime);
        servings = (TextView)findViewById(R.id.recipeServes);
        ingredients = (TextView)findViewById(R.id.recipeIngredientsList);
        saveButton = (Button)findViewById(R.id.saveRecipe);
        addButton = (Button)findViewById(R.id.addList);
        editButton = (Button)findViewById(R.id.editRecipe);

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
                        startActivity(new Intent(SingleRecipeActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SingleRecipeActivity.this, ProfileActivity.class));
                        break;
                    case R.id.sharing:
                        startActivity(new Intent(SingleRecipeActivity.this, SharingActivity.class));
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAdd();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type){
                    Intent intent1 = new Intent(SingleRecipeActivity.this, AddRecipeActivity.class);
                    intent1.putExtra("view",false);
                    intent1.putExtra("data",rDB);
                    Toast.makeText(SingleRecipeActivity.this,"Changing the title to one already present in your list will erase the other recipe! Please check!", Toast.LENGTH_LONG).show();
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(SingleRecipeActivity.this,"Save the recipe, then open it from your saved recipes list to edit it!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clickAdd() {
        if (type){
            HashMap<String, Ingredient> ingList = rDB.getIngList();
            for (Map.Entry<String, Ingredient> entry : ingList.entrySet()) {
                Ingredient value = entry.getValue();
                addToList(value);
            }
            Toast.makeText(this, "Ingredients added", Toast.LENGTH_SHORT).show();
        }
        else {
            List<Ingredient_Full> ingredientsFull = singleRecipe.getExtendedIngredients();
            for (Ingredient_Full ing: ingredientsFull){
                Ingredient ing1 = new Ingredient(ing);
                addToList(ing1);
            }
            Toast.makeText(this, "Ingredients added", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickSave() {
        if (type) {
            if (saveButton.getText().toString().equalsIgnoreCase("unSave")) {
                mDatabase.child("recipes").child(recipeName).removeValue();
                Toast.makeText(SingleRecipeActivity.this,"This recipe has been removed from your list.", Toast.LENGTH_SHORT).show();
                saveButton.setText(save);
            } else if (saveButton.getText().toString().equalsIgnoreCase("Save")) {
                Toast.makeText(SingleRecipeActivity.this,"This recipe has been added to your list.", Toast.LENGTH_SHORT).show();
                mDatabase.child("recipes").child(rDB.getTitle()).setValue(rDB);
                saveButton.setText(unSave);
            }
        } else {
            if (saveButton.getText().toString().equalsIgnoreCase("Save")) {
                if (singleRecipe.getTitle().length() > 85){
                    Toast.makeText(SingleRecipeActivity.this,"Sorry! The recipe's name is too long and cannot be saved!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Recipe_DB recipe_db = new Recipe_DB(singleRecipe, suffix);
                    mDatabase.child("recipes").child(recipe_db.getTitle()).setValue(recipe_db);
                    Toast.makeText(SingleRecipeActivity.this, "This recipe has been added to your list.", Toast.LENGTH_SHORT).show();
                    saveButton.setText(unSave);
                }
            } else if (saveButton.getText().toString().equalsIgnoreCase("unSave")) {
                mDatabase.child("recipes").child(recipeName).removeValue();
                Toast.makeText(SingleRecipeActivity.this,"This recipe has been removed from your list.", Toast.LENGTH_SHORT).show();
                saveButton.setText(save);
            }
        }
    }

    private void searchFromDatabase() {
        mRecipes = mDatabase.child("recipes").child(recipeName);
        mRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rDB = dataSnapshot.getValue(Recipe_DB.class);
                Textv.setText(recipeName);

                if (!rDB.getImageUrl().equals("")) {
                    Picasso.get().load(rDB.getImageUrl()).into(recipeImage);
                }

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
                else {
                    result += value.getAmount() + " " + value.getName() + "\n";
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

                Map<String, String> example = response.getHeaders();
                for (Map.Entry<String, String> entry : example.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Log.d("single recipe", key + " " + value);
                }

                String valid = null;
                try {
                    valid = IOUtils.toString(response.getRawBody(), "UTF-8");
                    Log.d("initial", valid);
                } catch (IOException e) {
                    Toast.makeText(SingleRecipeActivity.this,"Error in conversion! Press back and try again.", Toast.LENGTH_SHORT).show();
                }

                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(valid);
                } catch (JSONException e) {
                    Toast.makeText(SingleRecipeActivity.this,"Error in conversion! Press back and try again.", Toast.LENGTH_SHORT).show();
                }

                singleRecipe = new Recipe_Full(jObject);
                showAPIOnScreen(singleRecipe);
            }
            /*    try {

                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    singleRecipe = gson.fromJson(response.parseAsString(), Recipe_Full.class);
                         DEBUG: TEST GSON:
                         System.out.println("RESPONSE-GSON: " + view_single_recipe.getTitle());
                         END-DEBUG
                    showAPIOnScreen(singleRecipe);
                } catch (ParseException e) {
                    Log.e("hello", "Parsing recipe information failed!\n" + e.getLocalizedMessage());
                    Toast.makeText(SingleRecipeActivity.this,"There was some error in showing the recipe :(. Try again later!", Toast.LENGTH_SHORT).show();
                }

            DEBUG: TEST GSON:
            System.out.println("RESPONSE-HEADERS: " + response.getHeaders());
            System.out.println("RESPONSE-STRING: " + response.parseAsString());
            System.out.println("RESPONSE-GSON: " + view_single_recipe.getTitle());
            END-DEBUG */

            @Override
            public void onFailure(HttpContext context, Throwable error) {
                Log.e("hello", "Getting recipe information failed! See below:\n" + error.getLocalizedMessage());
                Toast.makeText(SingleRecipeActivity.this,"There was some error in reaching the server :(. Try again later!", Toast.LENGTH_SHORT).show();
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
                        openDialog(ingredientDB, ing);
                    }
                }
                else {
                    mDatabase.child("list").child(ing.getName()).setValue(ing);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SingleRecipeActivity.this,"Error adding to list! Try again!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openDialog(Ingredient ingredientDB, Ingredient ingredientAdd) {
        ChangeQuantityDialog changeQuantityDialog = new ChangeQuantityDialog();
        String ingDB;

        if (ingredientDB.getUnit() == null || ingredientDB.getUnit().isEmpty()){
            ingDB = "Current list has " + String.valueOf(ingredientDB.getAmount())+ " of " + ingredientDB.getName() + ".";
        }
        else {
            ingDB = "Current list has " + String.valueOf(ingredientDB.getAmount()) + " " + ingredientDB.getUnit() + " of " + ingredientDB.getName() + ".";
        }

        String ingAdd;
        if (ingredientAdd.getUnit() == null || ingredientAdd.getUnit().isEmpty()){
            ingAdd = "You want to add " + String.valueOf(ingredientAdd.getAmount())+ " of " + ingredientDB.getName() + ".";
        }
        else {
            ingAdd = "You want to add " + String.valueOf(ingredientAdd.getAmount()) + " " + ingredientAdd.getUnit() + " of " + ingredientDB.getName() + ".";
        }

        Bundle bundle = new Bundle();
        bundle.putString("Database",ingDB);
        bundle.putString("Adding",ingAdd);
        bundle.putString("Name",ingredientDB.getName());
        changeQuantityDialog.setArguments(bundle);
        changeQuantityDialog.show(getSupportFragmentManager(),"change quantity dialog");
    }

    @Override
    public void applyTexts(String quantityResult, String unitResult, String name) {
        if (quantityResult.isEmpty() || Float.valueOf(quantityResult) == 0) {
            Toast.makeText(this,"Missing or Wrong Values! Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        //set the final amount inside database
        mDatabase.child("list").child(name).child("amount").setValue(Float.valueOf(quantityResult));
        if (unitResult.isEmpty()){
            mDatabase.child("list").child(name).child("unit").setValue("");
        }
        else{
            mDatabase.child("list").child(name).child("unit").setValue(unitResult);
        }
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

