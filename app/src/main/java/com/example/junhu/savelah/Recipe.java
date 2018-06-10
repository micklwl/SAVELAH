package com.example.junhu.savelah;
import java.util.ArrayList;
import java.util.HashMap;

class Recipe {
    private String listName;
    private ArrayList<String> ingredients;
    private ArrayList<String> users;
    private int numOfUsers;

    public Recipe() {}

    public String getListName() {
        return this.listName;
    }
    public ArrayList<String> getIngredients() {
        return this.ingredients;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setListName(String name)  {
        this.listName = name;
    }
    public void addIngredient(String name) {
        this.ingredients.add(name);
    }

    public void addUsers(String user) {
        String s =  numOfUsers + "";
        this.users.add(user);
        numOfUsers ++;
    }

}
