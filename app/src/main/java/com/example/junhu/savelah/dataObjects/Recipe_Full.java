package com.example.junhu.savelah.dataObjects;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe_Full {
    // Matches: Data > GET Get Recipe Information
    // Usage: Represents a full recipe, used in the process of viewing more info on a recipe.
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    //private boolean sustainable;                  // Left out
    //private int     weightWatcherSmartPoints;     // Left out
    //private String  gaps;                         // Left out
    //private boolean lowFodmap;                    // Left out
    //private boolean ketogenic;                    // Left out
    //private boolean whole30;                      // Left out
    private int servings;
    // private String sourceUrl;                    // Left out
    private String spoonacularSourceUrl;
    //private int aggregateLikes;                   // Left out
    private String creditText;
    //private String sourceName;                    // Left out
    private List<Ingredient_Full> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private String instructions;
    private List<Steps> analyzedInstructions;
    //private String image;                         // Left out
    //private String imageType;                     // Left out


    public Recipe_Full(String json) {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Recipe_Full recipeFull = gson.fromJson(json, Recipe_Full.class);
        this.cheap = recipeFull.isCheap();
        this.dairyFree = recipeFull.isDairyFree();
        this.extendedIngredients = recipeFull.getExtendedIngredients();
        this.glutenFree = recipeFull.isGlutenFree();
        this.id = recipeFull.getId();
        this.readyInMinutes = recipeFull.getReadyInMinutes();
        this.servings = recipeFull.getServings();
        this.spoonacularSourceUrl = recipeFull.getSpoonacularSourceUrl();
        this.creditText = recipeFull.getCreditText();
        this.title = recipeFull.getTitle();
        this.vegan = recipeFull.isVegan();
        this.vegetarian = recipeFull.isVegetarian();
        this.veryHealthy = recipeFull.isVeryHealthy();
        this.veryPopular = recipeFull.isVeryPopular();
        this.analyzedInstructions = recipeFull.getAnalyzedInstructions();
        this.instructions = recipeFull.getInstructions();

    }

    public Recipe_Full(JSONObject obj) {
        try {
            JSONArray array = obj.getJSONArray("extendedIngredients");

            List<Ingredient_Full> resultList = new ArrayList<Ingredient_Full>();
            for(int i = 0 ; i < array.length() ; i++){
                Ingredient_Full temp = new Ingredient_Full();
                JSONObject childJSONObject = array.getJSONObject(i);
                temp.id = childJSONObject.getInt("id");
                temp.aisle = childJSONObject.getString("aisle");
                temp.image = childJSONObject.getString("image");
                temp.name = childJSONObject.getString("name");
                temp.amount = Float.valueOf(childJSONObject.getString("amount"));
                temp.unit = childJSONObject.getString("unit");
                temp.originalString = childJSONObject.getString("originalString");
                resultList.add(temp);
            }
            this.extendedIngredients = resultList;
            this.vegetarian = obj.getBoolean("vegetarian");
            this.vegan = obj.getBoolean("vegan");
            this.glutenFree = obj.getBoolean("glutenFree");
            this.dairyFree = obj.getBoolean("dairyFree");
            this.veryHealthy = obj.getBoolean("veryHealthy");
            this.cheap = obj.getBoolean("cheap");
            this.veryPopular = obj.getBoolean("veryPopular");
            //this.sustainable = obj.getBoolean("sustainable");
            //this.weightWatcherSmartPoints = obj.getInt("weightWatcherSmartPoints");
            //this.gaps = obj.getString("gaps");
            //this.lowFodmap = obj.getBoolean("lowFodmap");
            //this.ketogenic = obj.getBoolean("ketogenic");
            //this.whole30 = obj.getBoolean("whole30");
            this.servings = obj.getInt("servings");
            //this.sourceUrl = obj.getString("sourceUrl");
            this.spoonacularSourceUrl = obj.getString("spoonacularSourceUrl");
            //this.aggregateLikes = obj.getInt("aggregateLikes");
            this.creditText = obj.getString("creditText");
            //this.sourceName = obj.getString("sourceName");
            this.id = obj.getInt("id");
            this.title = obj.getString("title");
            this.readyInMinutes = obj.getInt("readyInMinutes");
            //this.image = obj.getString("image");
            //this.imageType = obj.getString("imageType");
            this.instructions = obj.getString("instructions");
        } catch (JSONException e) {
            Log.d("reason", e.getMessage());
        }
    }

    public String getJson() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public List<Ingredient_Full> getExtendedIngredients() { return extendedIngredients; }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public int getId() {
        return id;
    }

    public String getInstructions() {
        if (instructions != null){
            return instructions.replaceAll("\\s{2,}", "\n");
        }
        else{
            return null;
        }
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public String getCreditText() {
        return creditText;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isVegetarian() { return vegetarian; }

    public boolean isVeryHealthy() { return veryHealthy; }

    public boolean isVeryPopular() { return veryPopular; }

    public List<Steps> getAnalyzedInstructions() { return analyzedInstructions; }


}
