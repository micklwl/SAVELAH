package com.example.junhu.savelah.dataObjects;

import com.google.gson.Gson;

public class Ingredient_Full {
    private int id;
    private String aisle;
    private String image;
    private String name;
    private float amount;
    private String unitShort;
    private String unitLong;
    private String originalString;

    // Standard Ingredient
    public Ingredient_Full(String aisle, float amount, int id, String image, String name, String originalString, String unitLong, String unitShort) {
        this.aisle = aisle;
        this.amount = amount;
        this.id = id;
        this.image = image;
        this.name = name;
        this.originalString = originalString;
        this.unitLong = unitLong;
        this.unitShort = unitShort;
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
        this.unitLong = ingredient.getUnitLong();
        this.unitShort = ingredient.getUnitShort();
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


    public String getUnitShort() {
        return unitShort;
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


    public String getUnitLong() {
        return unitLong;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }
}
