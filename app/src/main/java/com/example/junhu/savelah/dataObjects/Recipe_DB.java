package com.example.junhu.savelah.dataObjects;

import java.util.HashMap;

public class Recipe_DB {
    private String title;
    private int id;
    private int readyInMinutes;
    private int servings;
    public String imageUrl;

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

    private HashMap<String, Ingredient> ingList;
    private String instructions;


}
