package com.example.junhu.savelah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {

    private String email;
    private HashMap<String,String> recipe;
    private List<String> members;

    private Customer() {}

    public Customer(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public void addMember(String email){
        this.members.add(email);
    }

    public void initialiseRecipe() {
        this.recipe = new HashMap<>();
        recipe.put("fish", "12 June");
        this.members = new ArrayList<>();
        members.add("junhui096@gmail.com");
    }
    public HashMap<String, String> getRecipe() {
        return this.recipe;
    }
    public void addIngredient(String ingredient) {
        recipe.put(ingredient, "default");
    }

}


