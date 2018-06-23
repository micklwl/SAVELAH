package com.example.junhu.savelah.dataObjects;

public class Ingredient {
    private String date;
    private float amount;
    private String name;
    private String unit;

    public Ingredient() {}

    public Ingredient(Ingredient_Full ingredientFull){
        this.date = "01/01/1996";
        this.amount = ingredientFull.getAmount();
        this.name = ingredientFull.getName();
        this.unit = ingredientFull.getUnit();
    }

    public Ingredient(String name, String date, Integer amount, String unit) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.unit = unit;
    }

    public String getDate() {
        return this.date;
    }

    public String getName() {
        return this.name;
    }

    public float getAmount() {
        return this.amount;
    }

    public String getUnit() { return unit; }
}


