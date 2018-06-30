package com.example.junhu.savelah.dataObjects;

public class Ingredient {
    private String name;
    private String date;
    private float amount;
    private String unit;
    private String alarmID;

    public Ingredient() {}

    public Ingredient(Ingredient_Full ingredientFull){
        this.date = "01/01/1996";
        this.amount = ingredientFull.getAmount();
        this.name = ingredientFull.getName();
        this.unit = ingredientFull.getUnit();
        this.alarmID = 0 + "" ;
    }

    public Ingredient(String name, String date, Float amount, String unit) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.unit = unit;
        this.alarmID = 0 + "";
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

    public String getAlarmID() {
        return this.alarmID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAlarmID(String id) {
        this.alarmID = id;
    }
}


