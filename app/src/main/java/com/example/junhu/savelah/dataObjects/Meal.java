package com.example.junhu.savelah.dataObjects;

import com.example.junhu.savelah.dataObjects.Recipe;

public class Meal {
    Recipe recipe;
    int hour;
    int minute;

    public void setTime(int hour, int min) {
        this.hour = hour;
        this.minute = min;
    }

    public Meal(Recipe recipe, int hour, int minute) {
        this.recipe = recipe;
        this.hour = hour;
        this.minute = minute;
    }

    public void setRecipe(Recipe rec) {
        this.recipe = rec;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public int getHour() {return this.hour;}

    public int getMinute() {
        return this.minute;
    }

    public String getTime() {
        return this.hour + ":" + this.minute;
    }

}
