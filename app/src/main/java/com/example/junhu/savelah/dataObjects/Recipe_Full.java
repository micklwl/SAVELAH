package com.example.junhu.savelah.dataObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private String creditText;                      // left out
    private List<Ingredient_Full> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private String instructions;
    private List<Steps> analyzedInstructions;


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
   /*     String temp = "";
        for (Steps j:recipeFull.getAnalyzedInstructions()){
            for (SingleStep k: j.getSteps()){
                temp = temp + k.getNumber() + ". " + k.getStep() +"\n";
            }
        }
        this.instructions = temp; */
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
        return instructions.replaceAll("\\s{2,}", "\n");
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
