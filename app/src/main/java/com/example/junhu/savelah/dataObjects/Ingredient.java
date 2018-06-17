package com.example.junhu.savelah.dataObjects;

public class Ingredient {
    private String date;
    private Integer amount;
    private String name;

    public Ingredient() {}

    public Ingredient(String name, String date, Integer amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
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
}


