package com.example.junhu.savelah.dataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String uid;
    private String email;
    private HashMap<String, Ingredient> list;
    private HashMap<String, String> members;
    private HashMap<String, Recipe_Full> recipes;
    private Customer() {}

    public Customer(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void initialiseList() {
        this.list = new HashMap<>();
        list.put("fish", new Ingredient("fish", "default", 1));
        this.members = new HashMap<>();
        this.recipes =  new HashMap<>();
        HashMap<String,Ingredient_Full> ingredientlist = new HashMap<>();
        Ingredient_Full testing = new Ingredient_Full("Jun Hui", 99/4, "people");
        ingredientlist.put("Ingredient Lists",testing);
        recipes.put("chicken", new Recipe_Full("chicken",30,2,10,ingredientlist,"eat air"));
    }

    public String getUid(){
        return this.uid;
    }

    public String getEmail() {
        return this.email;
    }

    public HashMap<String, Ingredient> getList() {
        return this.list;
    }

    public HashMap<String, String> getMembers() {
        return this.members;
    }

    private HashMap<String, Recipe_Full> getRecipes(){return this.recipes;}

    public void addIngredient(String ingredient, String date, int amount) {
        list.put(ingredient, new Ingredient(ingredient, date, amount));
    }

}


