package com.example.junhu.savelah.dataObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe_DB implements Parcelable {
    private String title;
    private int id;
    private String imageUrl;
    private int readyInMinutes;
    private int servings;
    private String instructions;
    private HashMap<String, Ingredient> ingList = new HashMap<String, Ingredient>();

    public Recipe_DB() {}

    public Recipe_DB(String title, int id, String imageUrl, int readyInMinutes, int servings, HashMap<String, Ingredient> ingList, String instructions) {
        this.title = title;
        this.id = id;
        this.imageUrl = imageUrl;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.ingList = ingList;
        this.instructions = instructions;
    }

    public Recipe_DB(Recipe_Full singleRecipe, String suffix){
        this.title = singleRecipe.getTitle();
        this.id = singleRecipe.getId();
        this.readyInMinutes = singleRecipe.getReadyInMinutes();
        this.servings = singleRecipe.getServings();
        this.instructions = singleRecipe.getInstructions();

        String imageUrl = "https://spoonacular.com/recipeImages/";
        if (suffix.endsWith(".jpg")) {
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpg";
        }
        else if (suffix.endsWith(".png")){
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.png";
        }
        else if (suffix.endsWith(".jpeg")){
            imageUrl = imageUrl + String.valueOf(singleRecipe.getId()) + "-556x370.jpeg";
        }
        this.imageUrl = imageUrl;

        List<Ingredient_Full> toUser = singleRecipe.getExtendedIngredients();

        for (Ingredient_Full i : toUser){
            Ingredient k = new Ingredient(i);
            ingList.put(k.getName(),k);
        }
        this.ingList = ingList;

    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public HashMap<String, Ingredient> getIngList() {
        return ingList;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeInt(id);
        out.writeString(imageUrl);
        out.writeInt(readyInMinutes);
        out.writeInt(servings);
        out.writeString(instructions);
        final int N = ingList.size();
        out.writeInt(N);
        if (N > 0) {
            for (Map.Entry<String, Ingredient> entry : ingList.entrySet()) {
                out.writeString(entry.getKey());
                Ingredient dat = entry.getValue();
                out.writeString(dat.getName());
                out.writeFloat(dat.getAmount());
                out.writeString(dat.getDate());
                out.writeString(dat.getUnit());
            }
        }

    }

    private Recipe_DB(Parcel in) {
        title = in.readString();
        id = in.readInt();
        imageUrl = in.readString();
        readyInMinutes = in.readInt();
        servings = in.readInt();
        instructions = in.readString();
        final int N = in.readInt();
        for (int i=0; i<N; i++) {
            String key = in.readString();
            Ingredient dat = new Ingredient();

            dat.setName(in.readString());
            dat.setAmount(in.readFloat());
            dat.setDate(in.readString());
            dat.setUnit(in.readString());
            ingList.put(key, dat);
        }

    }

    public static final Parcelable.Creator<Recipe_DB> CREATOR = new Creator<Recipe_DB>() {
        @Override
        public Recipe_DB createFromParcel(Parcel source) {
            return new Recipe_DB(source);
        }

        @Override
        public Recipe_DB[] newArray(int size) {
            return new Recipe_DB[size];
        }
    };


}
