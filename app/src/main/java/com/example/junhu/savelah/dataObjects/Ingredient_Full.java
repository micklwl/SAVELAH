package com.example.junhu.savelah.dataObjects;

import com.google.gson.Gson;

public class Ingredient_Full {
    public int id;
    public String aisle;
    public String image;
    public String name;
    public float amount;
    public String unit;
    public String originalString;

    public Ingredient_Full(){}

    // Standard Ingredient

    public Ingredient_Full(String aisle, float amount, int id, String image, String name, String originalString, String unit) {
        this.aisle = aisle;
        this.amount = amount;
        this.id = id;
        this.image = image;
        this.name = name;
        this.originalString = originalString;
        this.unit = unit;
    }

    public Ingredient_Full(String name, float amount, String unit) {
        this.amount = amount;
        this.name = name;
        this.unit = unit;
    }


    public Ingredient_Full(String json) {
        Gson gson = new Gson();
        Ingredient_Full ingredient = gson.fromJson(json, Ingredient_Full.class);
        this.aisle = ingredient.getAisle();
        this.amount = ingredient.getAmount();
        this.id = ingredient.getId();
        this.image = ingredient.getImage();
        this.name = ingredient.getName();
        this.originalString = ingredient.getOriginalString();
        this.unit = ingredient.getUnit();
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public float getAmount() {
        return amount;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getUnit() {
        return unit;
    }


    public String getAisle() {
        return aisle;
    }


    public String getImage() {
        return image;
    }


    public String getOriginalString() {
        return originalString;
    }


    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

}
