package com.example.junhu.savelah.dataObjects;

public class Ingredient {
    private String date;
    private Integer amount;
    private String name;


    private String unit;

    public Ingredient() {}

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

    public int getAmount() {
        return this.amount;
    }

    public String getUnit() { return unit; }
}


