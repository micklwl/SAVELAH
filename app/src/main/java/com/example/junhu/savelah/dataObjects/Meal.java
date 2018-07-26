package com.example.junhu.savelah.dataObjects;

import android.util.Log;
import android.widget.Toast;

import com.example.junhu.savelah.dataObjects.Recipe;

public class Meal {
    private Recipe recipe;
    private int hour;
    private int minute;

    public void setTime(int hour, int min) {
        this.hour = hour;
        this.minute = min;
    }

    public Meal(Recipe recipe, int hour, int minute) {
        if (recipe.getTitle().length() >= 70){
            String fullTitle = recipe.getTitle();
            String newTitle = fullTitle.substring(0,Math.min(fullTitle.length(),70)) + "...";
            Log.d("correction", newTitle);
            recipe.setTitle(newTitle);
        }
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
        if (this.hour < 10){
            if (this.minute >= 0 && this.minute < 10) {
                return "0" + this.hour + ":0" + this.minute;
            } else {
                return "0" + this.hour + ":" + this.minute;
            }
        }
        else{
            if (this.minute >= 0 && this.minute < 10) {
                return this.hour + ":0" + this.minute;
            } else {
                return this.hour + ":" + this.minute;
            }
        }
    }

}
