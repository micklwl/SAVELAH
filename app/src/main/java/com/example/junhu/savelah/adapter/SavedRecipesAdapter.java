package com.example.junhu.savelah.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junhu.savelah.R;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedRecipesAdapter extends ArrayAdapter<Recipe_DB> {
    ArrayList<Recipe_DB> recipes;
    Context context;
    int resource;

    public SavedRecipesAdapter(Context context, int resource, ArrayList<Recipe_DB> recipes){
        super(context, resource, recipes);
        this.context = context;
        this.resource = resource;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_search_list, null, true);
        }
        Recipe_DB recipe = this.getItem(position);
        ImageView image = convertView.findViewById(R.id.imageView);
        if (!recipe.getImageUrl().equals("")) {
            Picasso.get().load(recipe.getImageUrl()).into(image);
        }
        TextView title = convertView.findViewById(R.id.recipeTitle);
        title.setText(recipe.getTitle());

        return convertView;
    }
}
