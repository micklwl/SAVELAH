package com.example.junhu.savelah.dataObjects;

import java.util.HashMap;

public class Customer {
    private String uid;
    private String email;
    private HashMap<String, Ingredient> list;
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

    public void initialiseList() {
        this.list = new HashMap<>();
        list.put("fish", new Ingredient("fish", "default", 1));
        this.members = new HashMap<>();
    }
    public HashMap<String, Ingredient> getList() {
        return this.list;
    }

    public void addIngredient(String ingredient, String date, int amount) {
        list.put(ingredient, new Ingredient(ingredient, date, amount));
    }

}


