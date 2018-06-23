package com.example.junhu.savelah.dataObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe_DB {
    private String title;
    private int id;
    private int readyInMinutes;
    private int servings;
    public String imageUrl;
    private String instructions;
    private HashMap<String, Ingredient> ingList;

    public Recipe_DB() {}

    public Recipe_DB(String title, int id, String imageUrl, int readyInMinutes, int servings, HashMap<String, Ingredient> ingList, String instructions) {
        this.title = title;
        this.id = id;
        this.imageUrl = imageUrl;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.ingList = ingList;
        this.instructions = instructions;
    }

    public Recipe_DB(Recipe_Full singleRecipe, String suffix){
        this.title = singleRecipe.getTitle();
        this.id = singleRecipe.getId();
        this.readyInMinutes = singleRecipe.getReadyInMinutes();
        this.servings = singleRecipe.getServings();
        this.instructions = singleRecipe.getInstructions();

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
        this.imageUrl = imageUrl;

        List<Ingredient_Full> toUser = singleRecipe.getExtendedIngredients();

        HashMap<String, Ingredient> ingList = new HashMap<>();
        for (Ingredient_Full i : toUser){
            Ingredient k = new Ingredient(i);
            ingList.put(k.getName(),k);
        }
        this.ingList = ingList;

    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public HashMap<String, Ingredient> getIngList() {
        return ingList;
    }

    public String getInstructions() {
        return instructions;
    }


}
