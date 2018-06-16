package com.example.junhu.savelah.dataObjects;

import java.util.HashMap;

public class Customer {
    private String uid;
    private String email;
    private HashMap<String, Ingredient> recipe;
    private HashMap<String, String> members;

    private Customer() {}

    public Customer(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid(){
        return this.uid;
    }

    public HashMap<String, String> getMembers() {
        return this.members;
    }

    public void initialiseRecipe() {
        this.recipe = new HashMap<>();
        recipe.put("fish", new Ingredient("fish", "default", 1));
        this.members = new HashMap<>();
    }
    public HashMap<String, Ingredient> getRecipe() {
        return this.recipe;
    }
    public void addIngredient(String ingredient, String date, int amount) {
        recipe.put(ingredient, new Ingredient(ingredient, date, amount));
    }

}


