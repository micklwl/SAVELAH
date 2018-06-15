package com.example.junhu.savelah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String uid;
    private String email;
    private HashMap<String,String> recipe;
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
        recipe.put("fish", "12 June");
        this.members = new HashMap<>();
    }
    public HashMap<String, String> getRecipe() {
        return this.recipe;
    }
    public void addIngredient(String ingredient) {
        recipe.put(ingredient, "default");
    }

}


