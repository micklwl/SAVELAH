package com.example.junhu.savelah.dataObjects;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Recipe_Short implements Serializable {
    public int id;
    public String title;
    public int readyInMinutes;
    public String image;
    public List<String> imageUrls;

    public Recipe_Short(){};

    public Recipe_Short(int id, String image, List<String> imageUrls, int readyInMinutes, String title) {
        this.id = id;
        this.image = image;
        this.imageUrls = imageUrls;
        this.readyInMinutes = readyInMinutes;
        this.title = title;
    }

    public Recipe_Short(String json) {
        Gson gson = new Gson();
        Recipe_Short recipe_short = gson.fromJson(json, Recipe_Short.class);
        this.id = recipe_short.getId();
        this.image = recipe_short.getImage();
        this.imageUrls = recipe_short.getImageUrls();
        this.readyInMinutes = recipe_short.getReadyInMinutes();
        this.title = recipe_short.getTitle().replaceAll("\\s\\p{Pd}\\s", "-");
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }


    public List<String> getImageUrls() {
        return imageUrls;
    }


    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getTitle() {
        return title.replaceAll("\\s\\p{Pd}\\s", "-");
    }

    @Override
    public String toString() {
        return "Recipe_Short{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", image='" + image + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
