package com.example.junhu.savelah;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer {

    private String email;
    private ArrayList<Recipe> recipes;

    public Customer() {}

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<Recipe> getRecipes() {
        return this.recipes;
    }
    public void setRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

}


