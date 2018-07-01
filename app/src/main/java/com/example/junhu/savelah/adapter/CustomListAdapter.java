package com.example.junhu.savelah.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junhu.savelah.R;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Recipe> {

    ArrayList<Recipe> recipes;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int resource, ArrayList<Recipe> recipes){
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
        Recipe recipe = this.getItem(position);
        ImageView image = convertView.findViewById(R.id.imageView);
        if (!recipe.getImage().equals("")) {
            Picasso.get().load(recipe.getImage()).into(image);
        }
        TextView title = convertView.findViewById(R.id.recipeTitle);
     //   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
     //       title.setText(Html.fromHtml(recipe.getTitle(), Html.FROM_HTML_MODE_LEGACY));
     //   } else {
     //       title.setText(Html.fromHtml(recipe.getTitle()));
     //   }

        title.setText(recipe.getTitle());
        return convertView;
    }
}
