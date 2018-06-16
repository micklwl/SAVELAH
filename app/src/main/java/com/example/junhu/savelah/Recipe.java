package com.example.junhu.savelah;
import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private String title;
    private String image;

    public Recipe() {}

    public Recipe(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImage() {
        return this.image;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setImage(String str) {
        this.image = str;
    }

}
