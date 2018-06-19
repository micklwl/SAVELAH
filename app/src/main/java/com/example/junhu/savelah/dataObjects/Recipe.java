package com.example.junhu.savelah.dataObjects;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe {
    private String title;
    private String image;
    private String instructions;
    private int id;
    private List<Ingredient> extendedIngredients;

    public Recipe() {}

    public Recipe(String title, String image, int id) {
        this.title = title;
        this.image = image;
        this.id = id;
        Log.d("hello", title) ;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImage() {
        return this.image;
    }

    public String getInstructions() { return instructions; }

    public int getId() {
        return id;
    }
    public String getIdString() { return String.valueOf(id);}

    public void setTitle(String name) {
        this.title = name;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public void setId(int id) {
        this.id = id;
    }

}
