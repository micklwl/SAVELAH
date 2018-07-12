package com.example.junhu.savelah.dataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String uid;
    private String email;
    private HashMap<String, Ingredient> list;
    private HashMap<String, String> members;
    private HashMap<String, String> access;

    private HashMap<String, Recipe_DB> recipes;
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
        list.put("fish", new Ingredient("fish", "default", (float)1,"kg"));
        this.members = new HashMap<>();
        this.members.put(this.uid, "owner");
        this.recipes =  new HashMap<>();
        HashMap<String,Ingredient> ingredientlist = new HashMap<>();
        ingredientlist.put("Name-Jun Hui",new Ingredient("Name-Jun Hui", "1/1/2018", (float)20,"kg"));
        recipes.put("chicken", new Recipe_DB("chicken",12,"https://spoonacular.com/recipeImages/123-556x370.jpg",12,12,ingredientlist,"test"));
    }

    public String getUid(){
        return this.uid;
    }

    public HashMap<String, String> getAccess(){
        return this.access;
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

    public HashMap<String, Recipe_DB> getRecipes() {
        return recipes;
    }

    public void addIngredient(String ingredient, String date, float amount,String unit) {
        list.put(ingredient, new Ingredient(ingredient, date, amount,unit));
    }

}


